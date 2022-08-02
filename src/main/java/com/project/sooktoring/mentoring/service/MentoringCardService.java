package com.project.sooktoring.mentoring.service;

import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.domain.MentoringCard;
import com.project.sooktoring.mentoring.dto.request.MtrCardRequest;
import com.project.sooktoring.mentoring.dto.response.MtrCardFromResponse;
import com.project.sooktoring.mentoring.dto.response.MtrCardToResponse;
import com.project.sooktoring.mentoring.enumerate.MentoringState;
import com.project.sooktoring.mentoring.repository.MentoringCardRepository;
import com.project.sooktoring.mentoring.repository.MentoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MentoringCardService {

    private final MentoringRepository mentoringRepository;
    private final MentoringCardRepository mentoringCardRepository;

    public List<MtrCardFromResponse> getMentoringCardListFromMe(Long menteeId) {
        return mentoringCardRepository.findAllFromDto(menteeId);
    }

    public MtrCardFromResponse getMentoringCardFromMe(Long menteeId, Long mtrCardId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrCardId);
        if (mentoringOptional.isPresent() &&
                Objects.equals(mentoringOptional.get().getMenteeUserProfile().getId(), menteeId)) {

            Optional<MentoringCard> mentoringCardOptional = mentoringCardRepository.findById(mtrCardId);
            if (mentoringCardOptional.isPresent()) {
                return mentoringCardRepository.findFromDtoById(mtrCardId);
            }
            //나중에 예외처리
        }
        //나중에 예외처리
        return null;
    }

    public List<MtrCardToResponse> getMentoringCardListToMe(Long mentorId) {
        return mentoringCardRepository.findAllToDto(mentorId);
    }

    @Transactional
    public void save(Long menteeId, Long mtrId, MtrCardRequest mtrCardRequest) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrId);
        if (mentoringOptional.isPresent() &&
                Objects.equals(mentoringOptional.get().getMenteeUserProfile().getId(), menteeId) &&
                mentoringOptional.get().getState().equals(MentoringState.END)) {

            Optional<MentoringCard> mentoringCardOptional = mentoringCardRepository.findById(mtrId);
            if (mentoringCardOptional.isEmpty()) {
                Mentoring mentoring = mentoringOptional.get();
                MentoringCard mentoringCard = MentoringCard.builder()
                        .mentoring(mentoring)
                        .title(mtrCardRequest.getTitle())
                        .content(mtrCardRequest.getContent())
                        .build();

                mentoringCardRepository.save(mentoringCard);
            }
            //나중에 예외처리
        }
        //나중에 예외처리
    }

    @Transactional
    public void update(Long menteeId, Long mtrCardId, MtrCardRequest mtrCardRequest) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrCardId);
        if (mentoringOptional.isPresent() &&
                Objects.equals(mentoringOptional.get().getMenteeUserProfile().getId(), menteeId)) {

            Optional<MentoringCard> mentoringCardOptional = mentoringCardRepository.findById(mtrCardId);
            if (mentoringCardOptional.isPresent()) {
                MentoringCard mentoringCard = mentoringCardOptional.get();
                mentoringCard.updateCard(mtrCardRequest.getTitle(), mtrCardRequest.getContent());
            }
            //나중에 예외처리
        }
        //나중에 예외처리
    }

    @Transactional
    public void delete(Long menteeId, Long mtrCardId) {
        Optional<Mentoring> mentoringOptional = mentoringRepository.findById(mtrCardId);
        if (mentoringOptional.isPresent() &&
                Objects.equals(mentoringOptional.get().getMenteeUserProfile().getId(), menteeId)) {

            Optional<MentoringCard> mentoringCardOptional = mentoringCardRepository.findById(mtrCardId);
            if (mentoringCardOptional.isPresent()) {
                MentoringCard mentoringCard = mentoringCardOptional.get();
                mentoringCardRepository.delete(mentoringCard);
            }
            //나중에 예외처리
        }
        //나중에 예외처리
    }
}
