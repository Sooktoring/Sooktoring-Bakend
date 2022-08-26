package com.project.sooktoring.club.service;

import com.project.sooktoring.club.domain.Club;
import com.project.sooktoring.club.domain.ClubUrl;
import com.project.sooktoring.club.dto.request.ClubRequest;
import com.project.sooktoring.club.dto.request.ClubUrlRequest;
import com.project.sooktoring.club.dto.response.ClubListResponse;
import com.project.sooktoring.club.dto.response.ClubRecruitListResponse;
import com.project.sooktoring.club.dto.response.ClubResponse;
import com.project.sooktoring.club.dto.response.ClubUrlResponse;
import com.project.sooktoring.club.enumerate.ClubKind;
import com.project.sooktoring.club.repository.ClubRepository;
import com.project.sooktoring.club.repository.ClubUrlRepository;
import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.common.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.project.sooktoring.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class ClubService {

    private final S3Uploader s3Uploader;
    private final ClubRepository clubRepository;
    private final ClubUrlRepository clubUrlRepository;

    public List<ClubListResponse> getClubListByNewOrder(ClubKind kind) {
        return clubRepository.findAllByNewOrder(kind);
    }

    public List<ClubRecruitListResponse> getClubRecruitListByNewOrder() {
        List<ClubRecruitListResponse> clubRecruitListResponseList = clubRepository.findAllRecruitByNewOrder();

        for (ClubRecruitListResponse clubRecruitListResponse : clubRecruitListResponseList) {
            Long clubId = clubRecruitListResponse.getClubId();
            List<String> recruitUrlList = clubUrlRepository.findAllRecruitUrlByClubId(clubId);
            String recruitUrl = (recruitUrlList.size() == 0) ? "" : recruitUrlList.get(0);
            clubRecruitListResponse.changeRecruitUrl(recruitUrl); //차라리 모집링크를 직접 설정할 수 있도록 하는게 낫지 않나?
        }
        return clubRecruitListResponseList;
    }

    public ClubResponse getClub(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new CustomException(NOT_FOUND_CLUB));
        List<ClubUrlResponse> clubUrlList = clubUrlRepository.findAllByClubId(clubId);
        return ClubResponse.create(club, clubUrlList);
    }

    @Transactional
    public void save(ClubRequest clubRequest, MultipartFile file) {
        //Club 저장
        String logoUrl = s3Uploader.getImageUrl(file, "");
        Club club = Club.builder()
                .logoUrl(logoUrl)
                .kind(clubRequest.getKind())
                .name(clubRequest.getName())
                .desc(clubRequest.getDesc())
                .recruitField(clubRequest.getRecruitField())
                .recruitTime(clubRequest.getRecruitTime())
                .isRecruit(clubRequest.getIsRecruit())
                .build();
        club = clubRepository.save(club);

        //ClubUrl 저장
        List<ClubUrlRequest> clubUrlRequestList = clubRequest.getClubUrlList();
        for (ClubUrlRequest clubUrlRequest : clubUrlRequestList) {
            ClubUrl clubUrl = ClubUrl.builder()
                    .club(club)
                    .cat(clubUrlRequest.getCat())
                    .url(clubUrlRequest.getUrl())
                    .build();
            clubUrlRepository.save(clubUrl);
        }
    }

    @Transactional
    public void update(Long clubId, ClubRequest clubRequest, MultipartFile file) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new CustomException(NOT_FOUND_CLUB));
        String logoUrl = s3Uploader.getImageUrl(file, club.getLogoUrl());

        club.update(clubRequest);
        club.changeLogoUrl(logoUrl);
        _changeClubUrl(clubRequest, club);
    }

    @Transactional
    public void delete(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(() -> new CustomException(NOT_FOUND_CLUB));
        if (StringUtils.hasText(club.getLogoUrl())) {
            s3Uploader.deleteImg(club.getLogoUrl());
        }
        clubUrlRepository.deleteByClubId(clubId);
        clubRepository.deleteById(clubId);
    }

    //=== private 메소드 ===

    private void _changeClubUrl(ClubRequest clubRequest, Club club) {
        List<ClubUrlRequest> clubUrlRequestList = clubRequest.getClubUrlList();
        List<Long> clubUrlIds = new ArrayList<>();

        for (ClubUrlRequest clubUrlRequest : clubUrlRequestList) {
            Long clubUrlId = clubUrlRequest.getClubUrlId();
            if (clubUrlId == null || clubUrlRepository.findById(clubUrlId).isEmpty()) {
                ClubUrl clubUrl = clubUrlRepository.save(
                        ClubUrl.builder()
                                .club(club)
                                .cat(clubUrlRequest.getCat())
                                .url(clubUrlRequest.getUrl())
                                .build()
                );
                clubUrlIds.add(clubUrl.getId());
            } else {
                ClubUrl clubUrl = clubUrlRepository.findById(clubUrlId).get();
                if (!Objects.equals(clubUrl.getClub().getId(), club.getId())) {
                    throw new CustomException(NOT_FOUND_CLUB_URL);
                }

                clubUrl.update(clubUrlRequest.getCat(), clubUrlRequest.getUrl());
                clubUrlIds.add(clubUrl.getId());
            }
        }
        //DTO에 포함되지 않은 clubUrl 삭제
        clubUrlRepository.deleteByIdsNotInBatch(club.getId(), clubUrlIds);
    }
}
