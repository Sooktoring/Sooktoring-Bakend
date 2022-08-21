package com.project.sooktoring.mentoring.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.common.utils.ProfileUtil;
import com.project.sooktoring.common.utils.SecurityUtil;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.dto.request.MentoringRequest;
import com.project.sooktoring.mentoring.dto.request.MentoringUpdateRequest;
import com.project.sooktoring.mentoring.dto.response.*;
import com.project.sooktoring.mentoring.repository.MentoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.project.sooktoring.common.exception.ErrorCode.*;
import static com.project.sooktoring.mentoring.enumerate.MentoringState.*;

@Service
@RequiredArgsConstructor
public class MentoringService {

    private final ProfileUtil profileUtil;
    private final MentoringRepository mentoringRepository;

    public List<MentoringFromListResponse> getMyMentoringList() {
        return mentoringRepository.findAllFromDto(SecurityUtil.getCurrentUserId());
    }

    public MentoringFromResponse getMyMentoring(Long mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMenteeProfile().getId(), SecurityUtil.getCurrentUserId())) {
            return mentoringRepository.findFromDtoById(mentoringId);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    public List<MentoringToListResponse> getMentoringListToMe() {
        return mentoringRepository.findAllToDto(SecurityUtil.getCurrentUserId());
    }

    public MentoringToResponse getMentoringToMe(Long mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMentorProfile().getId(), SecurityUtil.getCurrentUserId())) {
            return mentoringRepository.findToDtoById(mentoringId);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void save(MentoringRequest mtrRequest) {
        //같은 멘토링 신청내역 존재하는 경우
        Optional<Mentoring> mentoringOptional = mentoringRepository.findByMentorProfileIdAndCat(mtrRequest.getMentorId(), mtrRequest.getCat());
        if (mentoringOptional.isPresent() &&
                (mentoringOptional.get().getState() != REJECT &&
                 mentoringOptional.get().getState() != END)) {
            throw new CustomException(ALREADY_MENTORING_EXISTS);
        }

        Profile mentor = profileUtil.getProfile(mtrRequest.getMentorId());
        Profile mentee = profileUtil.getCurrentProfile();

        //멘토와 멘티가 같거나, 멘티에게 멘토링 신청한 경우
        if (mentor == mentee) {
            throw new CustomException(INVALID_MENTORING_TO_SELF);
        }
        if (!mentor.getIsMentor()) {
            throw new CustomException(INVALID_MENTORING_TO_MENTEE);
        }

        Mentoring mentoring = Mentoring.create(mtrRequest.getCat(), mtrRequest.getReason(), mtrRequest.getTalk());
        mentoring.setMentorMentee(mentor, mentee);
        mentoringRepository.save(mentoring);
    }

    @Transactional
    public void update(Long mentoringId, MentoringUpdateRequest mtrUpdateRequest) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMenteeProfile().getId(), SecurityUtil.getCurrentUserId())) {
            if (mentoring.getState() == APPLY || mentoring.getState() == INVALID) {
                Long mentorId = mentoring.getMentorProfile().getId();
                Optional<Mentoring> mentoringOptional = mentoringRepository.findByMentorProfileIdAndCat(mentorId, mtrUpdateRequest.getCat());
                if (mentoringOptional.isPresent() &&
                        (mentoringOptional.get().getState() != REJECT &&
                         mentoringOptional.get().getState() != END)) {
                    throw new CustomException(ALREADY_MENTORING_EXISTS);
                }

                mentoring.update(mtrUpdateRequest.getCat(), mtrUpdateRequest.getReason(), mtrUpdateRequest.getTalk());
            }
            throw new CustomException(FORBIDDEN_MENTORING_UPDATE);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void cancel(Long mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMenteeProfile().getId(), SecurityUtil.getCurrentUserId())) {
            if (mentoring.getState() == ACCEPT || mentoring.getState() == END) {
                throw new CustomException(FORBIDDEN_MENTORING_CANCEL);
            }
            mentoringRepository.deleteById(mentoringId);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void accept(Long mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMentorProfile().getId(), SecurityUtil.getCurrentUserId())) {
            if (mentoring.getState() == APPLY) {
                mentoring.accept();
            }
            throw new CustomException(FORBIDDEN_MENTORING_ACCEPT);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void reject(Long mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMentorProfile().getId(), SecurityUtil.getCurrentUserId())) {
            if (mentoring.getState() == APPLY) {
                //chatRoomRepository.deleteById(mtrId); //일단 삭제! 나중에 삭제 여부 필드 추가
                mentoring.reject();
            }
            throw new CustomException(FORBIDDEN_MENTORING_REJECT);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void end(Long mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMentorProfile().getId(), SecurityUtil.getCurrentUserId())) {
            if (mentoring.getState() == ACCEPT) {
                mentoring.end();
            }
            throw new CustomException(FORBIDDEN_MENTORING_END);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void withdraw(Long profileId) {
        //모든 멘토링 내역(내가 멘토인, 내가 멘티인) WITHDRAW 상태로 변경
        List<Mentoring> mentoringListToMe = mentoringRepository.findByMentorProfileId(profileId);
        List<Mentoring> mentoringListFromMe = mentoringRepository.findByMenteeProfileId(profileId);
        for (Mentoring mentoring : mentoringListToMe) {
            mentoring.withdraw();
        }
        for (Mentoring mentoring : mentoringListFromMe) {
            mentoring.withdraw();
        }
        //탈퇴하는 이용자의 멘토링 FK set null
        mentoringRepository.updateMentorByProfileId(profileId);
        mentoringRepository.updateMenteeByProfileId(profileId);
    }

    public List<Mentoring> getMyChatRoomList() {
        Profile profile = profileUtil.getCurrentProfile();
        if(profile.getIsMentor()) return mentoringRepository.findByMentorProfileIdAndState(profile.getId(), APPLY);
        else return mentoringRepository.findByMenteeProfileIdAndState(profile.getId(), APPLY);
    }
}