package com.project.sooktoring.mentoring.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.profile.domain.Profile;
import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.dto.request.MentoringRequest;
import com.project.sooktoring.mentoring.dto.request.MentoringUpdateRequest;
import com.project.sooktoring.mentoring.dto.response.*;
import com.project.sooktoring.mentoring.repository.MentoringRepository;
import com.project.sooktoring.profile.repository.ProfileRepository;
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

    private final ProfileRepository userProfileRepository;
    private final MentoringRepository mentoringRepository;
    //private final ChatRoomRepository chatRoomRepository;

    public List<MentoringFromListResponse> getMyMentoringList(Long menteeId) {
        return mentoringRepository.findAllFromDto(menteeId);
    }

    public MentoringFromResponse getMyMentoring(Long menteeId, Long mtrId) {
        Mentoring mentoring = mentoringRepository.findById(mtrId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMenteeProfile().getId(), menteeId)) {
            return mentoringRepository.findFromDtoById(mtrId);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    public List<MentoringToListResponse> getMentoringListToMe(Long mentorId) {
        return mentoringRepository.findAllToDto(mentorId);
    }

    public MentoringToResponse getMentoringToMe(Long mentorId, Long mtrId) {
        Mentoring mentoring = mentoringRepository.findById(mtrId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMentorProfile().getId(), mentorId)) {
            return mentoringRepository.findToDtoById(mtrId);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void save(Long menteeId, MentoringRequest mtrRequest) {
        //같은 멘토링 신청내역 존재하는 경우
        Optional<Mentoring> mentoringOptional = mentoringRepository.findByMentorIdAndCat(mtrRequest.getMentorId(), mtrRequest.getCat());
        if (mentoringOptional.isPresent() &&
                (mentoringOptional.get().getState() != REJECT &&
                 mentoringOptional.get().getState() != END)) {
            throw new CustomException(ALREADY_MENTORING_EXISTS);
        }

        Profile mentor = userProfileRepository.findById(mtrRequest.getMentorId())
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
        Profile mentee = userProfileRepository.findById(menteeId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

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
    public void update(Long menteeId, Long mtrId, MentoringUpdateRequest mtrUpdateRequest) {
        Mentoring mentoring = mentoringRepository.findById(mtrId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMenteeProfile().getId(), menteeId)) {
            if (mentoring.getState() == APPLY || mentoring.getState() == INVALID) {
                Long mentorId = mentoring.getMentorProfile().getId();
                Optional<Mentoring> mentoringOptional = mentoringRepository.findByMentorIdAndCat(mentorId, mtrUpdateRequest.getCat());
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
    public void cancel(Long menteeId, Long mtrId) {
        Mentoring mentoring = mentoringRepository.findById(mtrId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMenteeProfile().getId(), menteeId)) {
            if (mentoring.getState() == ACCEPT || mentoring.getState() == END) {
                throw new CustomException(FORBIDDEN_MENTORING_CANCEL);
            }
            mentoringRepository.deleteById(mtrId);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void accept(Long mentorId, Long mtrId) {
        Mentoring mentoring = mentoringRepository.findById(mtrId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMentorProfile().getId(), mentorId)) {
            if (mentoring.getState() == APPLY) {
                mentoring.accept();
            }
            throw new CustomException(FORBIDDEN_MENTORING_ACCEPT);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void reject(Long mentorId, Long mtrId) {
        Mentoring mentoring = mentoringRepository.findById(mtrId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMentorProfile().getId(), mentorId)) {
            if (mentoring.getState() == APPLY) {
                //chatRoomRepository.deleteById(mtrId); //일단 삭제! 나중에 삭제 여부 필드 추가
                mentoring.reject();
            }
            throw new CustomException(FORBIDDEN_MENTORING_REJECT);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void end(Long mentorId, Long mtrId) {
        Mentoring mentoring = mentoringRepository.findById(mtrId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMentorProfile().getId(), mentorId)) {
            if (mentoring.getState() == ACCEPT) {
                mentoring.end();
            }
            throw new CustomException(FORBIDDEN_MENTORING_END);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    public List<Mentoring> getMyChatRoomList(Long userId) {
        Profile user = userProfileRepository.getById(userId);
        if(user.getIsMentor()) return mentoringRepository.findByMentorIdAndState(user.getId(), APPLY);
        else return mentoringRepository.findByMenteeIdAndState(user.getId(), APPLY);
    }

}