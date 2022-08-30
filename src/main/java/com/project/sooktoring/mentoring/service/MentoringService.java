package com.project.sooktoring.mentoring.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.common.utils.ProfileUtil;
import com.project.sooktoring.common.utils.UserUtil;
import com.project.sooktoring.mentoring.enumerate.MentoringState;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.dto.request.MentoringRequest;
import com.project.sooktoring.mentoring.dto.request.MentoringUpdateRequest;
import com.project.sooktoring.mentoring.dto.response.*;
import com.project.sooktoring.mentoring.repository.MentoringRepository;
import com.project.sooktoring.push.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.project.sooktoring.common.exception.ErrorCode.*;
import static com.project.sooktoring.mentoring.enumerate.MentoringState.*;

@RequiredArgsConstructor
@Service
public class MentoringService {

    private final UserUtil userUtil;
    private final ProfileUtil profileUtil;
    private final FcmService fcmService;
    private final MentoringRepository mentoringRepository;

    //From
    public List<MentoringFromListResponse> getMyMentoringList() {
        Long profileId = profileUtil.getCurrentProfile().getId();
        return mentoringRepository.findAllFromDto(profileId);
    }

    public MentoringFromResponse getMyMentoring(Long mentoringId) {
        _getMentoring(mentoringId, false);
        return mentoringRepository.findFromDtoById(mentoringId);
    }

    @Transactional
    public void save(MentoringRequest mentoringRequest) {
        //같은 멘토링 신청내역 존재하는 경우
        Optional<Mentoring> mentoringOptional = mentoringRepository.findByMentorProfileIdAndCat(mentoringRequest.getMentorProfileId(), mentoringRequest.getCat());
        if (mentoringOptional.isPresent() &&
                (mentoringOptional.get().getState() != REJECT &&
                 mentoringOptional.get().getState() != END)) {
            throw new CustomException(ALREADY_MENTORING_EXISTS);
        }

        Profile mentor = profileUtil.getProfile(mentoringRequest.getMentorProfileId());
        Profile mentee = profileUtil.getCurrentProfile();
        //멘토와 멘티가 같거나, 멘티에게 멘토링 신청한 경우
        if (mentor == mentee) {
            throw new CustomException(INVALID_MENTORING_TO_SELF);
        }
        if (!mentor.getIsMentor()) {
            throw new CustomException(INVALID_MENTORING_TO_MENTEE);
        }

        Mentoring mentoring = Mentoring.create(mentoringRequest.getCat(), mentoringRequest.getReason(), mentoringRequest.getTalk());
        mentoring.setMentorAndMentee(mentor, mentee);
        mentoringRepository.save(mentoring);
    }

    @Transactional
    public void update(Long mentoringId, MentoringUpdateRequest mentoringUpdateRequest) {
        Mentoring mentoring = _getMentoring(mentoringId, false);
        if (mentoring.getState() != APPLY) {
            throw new CustomException(FORBIDDEN_MENTORING_UPDATE);
        }

        //멘토링 카테고리 수정할 경우, 같은 멘토 & 같은 카테고리 신청내역 존재 여부 확인
        if (mentoring.getCat() != mentoringUpdateRequest.getCat()) {
            Long mentorId = mentoring.getMentorProfile().getId();
            Optional<Mentoring> mentoringOptional = mentoringRepository.findByMentorProfileIdAndCat(mentorId, mentoringUpdateRequest.getCat());
            if (mentoringOptional.isPresent() &&
                    (mentoringOptional.get().getState() != REJECT &&
                     mentoringOptional.get().getState() != END)) {
                throw new CustomException(ALREADY_MENTORING_EXISTS);
            }
        }

        mentoring.update(mentoringUpdateRequest.getCat(), mentoringUpdateRequest.getReason(), mentoringUpdateRequest.getTalk());
    }

    @Transactional
    public void cancel(Long mentoringId) {
        Mentoring mentoring = _getMentoring(mentoringId, false);
        if (mentoring.getState() == ACCEPT || mentoring.getState() == END) {
            throw new CustomException(FORBIDDEN_MENTORING_CANCEL);
        }
        mentoringRepository.deleteById(mentoringId);
    }

    @Transactional
    public void endByMentee(Long mentoringId) {
        Mentoring mentoring = _getMentoring(mentoringId, false);
        MentoringState state = mentoring.getState();
        String title, body;

        if (state != ACCEPT && state != END_MENTOR) {
            throw new CustomException(FORBIDDEN_MENTORING_END);
        }
        else if (state == ACCEPT) {
            mentoring.endMentee();
            title = "멘토링 종료 요청 알림"; body = "멘티가 멘토링 종료를 요청하였습니다.";
        }
        else {
            mentoring.end();
            title = "멘토링 종료 알림"; body = "멘티가 멘토링 종료를 수락하였습니다.";
        }

        //푸시 알림 send to 멘토
        Long toProfileId = mentoring.getMentorProfile().getId();
        _sendPushNotification(toProfileId, title, body);
    }

    //To
    public List<MentoringToListResponse> getMentoringListToMe() {
        Long profileId = profileUtil.getCurrentProfile().getId();
        return mentoringRepository.findAllToDto(profileId);
    }

    public MentoringToResponse getMentoringToMe(Long mentoringId) {
        _getMentoring(mentoringId, true);
        return mentoringRepository.findToDtoById(mentoringId);
    }

    @Transactional
    public void accept(Long mentoringId) {
        Mentoring mentoring = _getMentoring(mentoringId, true);
        if (mentoring.getState() != APPLY) {
            throw new CustomException(FORBIDDEN_MENTORING_ACCEPT);
        }
        mentoring.accept();
    }

    @Transactional
    public void reject(Long mentoringId) {
        Mentoring mentoring = _getMentoring(mentoringId, true);
        if (mentoring.getState() != APPLY) {
            throw new CustomException(FORBIDDEN_MENTORING_REJECT);
        }
        mentoring.reject();
    }

    @Transactional
    public void endByMentor(Long mentoringId) {
        Mentoring mentoring = _getMentoring(mentoringId, true);
        MentoringState state = mentoring.getState();
        String title, body;

        if (state != ACCEPT && state != END_MENTEE) {
            throw new CustomException(FORBIDDEN_MENTORING_END);
        }
        else if (state == ACCEPT) {
            mentoring.endMentor();
            title = "멘토링 종료 요청 알림"; body = "멘토가 멘토링 종료를 요청하였습니다.";
        }
        else {
            mentoring.end();
            title = "멘토링 종료 알림"; body = "멘토가 멘토링 종료를 수락하였습니다.";
        }

        //푸시 알림 send to 멘티
        Long toProfileId = mentoring.getMenteeProfile().getId();
        _sendPushNotification(toProfileId, title, body);
    }

    //=== private 메소드 ===

    //해당 멘토링의 존재 여부 & 현재 인증된 이용자가 해당 멘토링의 멘토 or 멘티인지 여부
    private Mentoring _getMentoring(Long mentoringId, Boolean isMentor) {//isMentor : 해당 멘토링에서 멘토인지 여부
        Mentoring mentoring = mentoringRepository.findById(mentoringId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        Long profileId = profileUtil.getCurrentProfile().getId();

        if (isMentor) {
            if (Objects.equals(mentoring.getMentorProfile().getId(), profileId)) return mentoring;
        } else {
            if (Objects.equals(mentoring.getMenteeProfile().getId(), profileId)) return mentoring;
        }
        throw new CustomException(FORBIDDEN_MENTORING_ACCESS);
    }

    private void _sendPushNotification(Long toProfileId, String title, String body) {
        Long userId = profileUtil.getProfile(toProfileId).getUser().getId();
        String targetToken = userUtil.getUser(userId).getFcmToken();

        try {
            fcmService.sendMessageTo(targetToken, title, body);
        } catch (IOException e) {
            throw new CustomException(FAILED_MENTORING_PUSH);
        }
    }
}