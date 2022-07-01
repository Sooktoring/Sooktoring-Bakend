package com.project.sooktoring.service;

import com.project.sooktoring.domain.ChatRoom;
import com.project.sooktoring.domain.Mentoring;
import com.project.sooktoring.domain.UserProfile;
import com.project.sooktoring.dto.request.MtrRequest;
import com.project.sooktoring.dto.request.MtrUpdateRequest;
import com.project.sooktoring.dto.response.MtrFromResponse;
import com.project.sooktoring.dto.response.MtrToResponse;
import com.project.sooktoring.exception.MtrDuplicateException;
import com.project.sooktoring.exception.MtrTargetException;
import com.project.sooktoring.repository.ChatRoomRepository;
import com.project.sooktoring.repository.MentoringRepository;
import com.project.sooktoring.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MentoringService {

    private final UserProfileRepository userProfileRepository;
    private final MentoringRepository mentoringRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void save(MtrRequest mtrRequest, Long menteeId) {
        //같은 멘토링 신청내역 존재하는 경우
        Optional<Mentoring> mentoringOptional = mentoringRepository.findByMentorIdAndCat(mtrRequest.getMentorId(), mtrRequest.getCat());
        if (mentoringOptional.isPresent()) {
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

    public List<MtrFromResponse> getMyMentoringList(Long menteeId) {
        return mentoringRepository.findAllFromDto(menteeId);
    }

    public MtrFromResponse getMyMentoring(Long mtrId) {
        return mentoringRepository.findFromDtoById(mtrId);
    }

    public List<MtrToResponse> getMentoringListToMe(Long mentorId) {
        return mentoringRepository.findAllToDto(mentorId);
    }

    public MtrToResponse getMentoringToMe(Long mtrId) {
        return mentoringRepository.findToDtoById(mtrId);
    }

    @Transactional
    public void update(MtrUpdateRequest mtrUpdateRequest, Long mtrId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrId);
        if (mentoringOptional.isPresent()) {
            Mentoring mentoring = mentoringOptional.get();
            mentoring.update(mtrUpdateRequest.getCat(), mtrUpdateRequest.getReason(), mtrUpdateRequest.getTalk());
        }
    }

    @Transactional
    public boolean cancel(Long mtrId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrId);
        if (mentoringOptional.isEmpty() || mentoringOptional.get().getIsAccept()) {
            return false;
        }
        mentoringRepository.deleteById(mtrId);
        return true;
    }

    @Transactional
    public void accept(Long mtrId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrId);
        if (mentoringOptional.isPresent()) {
            Mentoring mentoring = mentoringOptional.get();
            mentoring.accept();

            ChatRoom chatRoom = ChatRoom.create(mentoring);
            chatRoomRepository.save(chatRoom);
        }
    }

    @Transactional
    public void reject(Long mtrId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrId);
        if (mentoringOptional.isPresent()) {
            Mentoring mentoring = mentoringOptional.get();
            mentoring.reject();

            chatRoomRepository.deleteById(mtrId); //일단 삭제! 나중에 삭제 여부 필드 추가
        }
    }
}
