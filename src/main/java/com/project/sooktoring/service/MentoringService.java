package com.project.sooktoring.service;

import com.project.sooktoring.domain.Mentoring;
import com.project.sooktoring.domain.UserProfile;
import com.project.sooktoring.dto.request.MtrRequest;
import com.project.sooktoring.dto.request.MtrUpdateRequest;
import com.project.sooktoring.dto.response.*;
import com.project.sooktoring.exception.MtrDuplicateException;
import com.project.sooktoring.exception.MtrTargetException;
import com.project.sooktoring.repository.MentoringRepository;
import com.project.sooktoring.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.project.sooktoring.enumerate.MentoringState.*;

@Service
@RequiredArgsConstructor
public class MentoringService {

    private final UserProfileRepository userProfileRepository;
    private final MentoringRepository mentoringRepository;
    //private final ChatRoomRepository chatRoomRepository;

    public List<MentorResponse> getMentorList() {
        return userProfileRepository.findMentors();
    }

    public MentorResponse getMentor(Long mentorId) {
        return userProfileRepository.findMentor(mentorId);
    }

    public List<MtrFromListResponse> getMyMentoringList(Long menteeId) {
        return mentoringRepository.findAllFromDto(menteeId);
    }

    public MtrFromResponse getMyMentoring(Long mtrId) {
        return mentoringRepository.findFromDtoById(mtrId);
    }

    public List<MtrToListResponse> getMentoringListToMe(Long mentorId) {
        return mentoringRepository.findAllToDto(mentorId);
    }

    public MtrToResponse getMentoringToMe(Long mtrId) {
        return mentoringRepository.findToDtoById(mtrId);
    }

    @Transactional
    public void save(MtrRequest mtrRequest, Long menteeId) {
        //같은 멘토링 신청내역 존재하는 경우
        Optional<Mentoring> mentoringOptional = mentoringRepository.findByMentorIdAndCat(mtrRequest.getMentorId(), mtrRequest.getCat());
        if (mentoringOptional.isPresent() &&
                (mentoringOptional.get().getState() != REJECT &&
                mentoringOptional.get().getState() != END)) {
            throw new MtrDuplicateException("같은 신청내역이 이미 존재합니다.", mtrRequest);
        }

        Optional<UserProfile> mentorOptional = userProfileRepository.findById(mtrRequest.getMentorId());
        Optional<UserProfile> menteeOptional = userProfileRepository.findById(menteeId);

        if(mentorOptional.isPresent() && menteeOptional.isPresent()) {
            UserProfile mentor = mentorOptional.get();
            UserProfile mentee = menteeOptional.get();

            //멘토와 멘티가 같거나, 멘티에게 멘토링 신청한 경우
            if (mentor == mentee) {
                throw new MtrTargetException("자신에게 멘토링 신청은 불가능합니다.", mtrRequest);
            }
            if (!mentor.getIsMentor()) {
                throw new MtrTargetException("멘티에게 멘토링 신청은 불가능합니다.", mtrRequest);
            }

            Mentoring mentoring = Mentoring.create(mtrRequest.getCat(), mtrRequest.getReason(), mtrRequest.getTalk());
            mentoring.setMentorMentee(mentor, mentee);
            mentoringRepository.save(mentoring);
        }
        //나머지 경우 에러 throw
    }

    @Transactional
    public boolean update(MtrUpdateRequest mtrUpdateRequest, Long mtrId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrId);
        if (mentoringOptional.isPresent() &&
                (mentoringOptional.get().getState() == APPLY ||
                mentoringOptional.get().getState() == INVALID)) {
            Mentoring mentoring = mentoringOptional.get();
            Long mentorId = mentoring.getMentorUserProfile().getId();

            Optional<Mentoring> reMentoring = mentoringRepository.findByMentorIdAndCat(mentorId, mtrUpdateRequest.getCat());
            if (reMentoring.isPresent() &&
                    (reMentoring.get().getState() != REJECT &&
                    reMentoring.get().getState() != END)) {
                return false;
            }
            mentoring.update(mtrUpdateRequest.getCat(), mtrUpdateRequest.getReason(), mtrUpdateRequest.getTalk());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean cancel(Long mtrId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrId);
        if (mentoringOptional.isEmpty() ||
                mentoringOptional.get().getState() == ACCEPT ||
                mentoringOptional.get().getState() == END) {
            return false;
        }
        mentoringRepository.deleteById(mtrId);
        return true;
    }

    @Transactional
    public boolean accept(Long mtrId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrId);
        if (mentoringOptional.isEmpty() ||
                mentoringOptional.get().getState() != APPLY) {
            //ChatRoom chatRoom = ChatRoom.create(mentoring);
            //chatRoomRepository.save(chatRoom);
            return false;
        }
        Mentoring mentoring = mentoringOptional.get();
        mentoring.accept();
        return true;
    }

    @Transactional
    public boolean reject(Long mtrId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrId);
        if (mentoringOptional.isEmpty() ||
                mentoringOptional.get().getState() != APPLY) {
            //chatRoomRepository.deleteById(mtrId); //일단 삭제! 나중에 삭제 여부 필드 추가
            return false;
        }
        Mentoring mentoring = mentoringOptional.get();
        mentoring.reject();
        return true;
    }

    @Transactional
    public boolean end(Long mtrId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrId);
        if (mentoringOptional.isEmpty() ||
                mentoringOptional.get().getState() != ACCEPT) {
            return false;
        }
        Mentoring mentoring = mentoringOptional.get();
        mentoring.end();
        return true;
    }
}