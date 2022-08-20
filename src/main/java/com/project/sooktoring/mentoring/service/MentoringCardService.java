package com.project.sooktoring.mentoring.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.domain.MentoringCard;
import com.project.sooktoring.mentoring.dto.request.MentoringCardRequest;
import com.project.sooktoring.mentoring.dto.response.MentoringCardFromResponse;
import com.project.sooktoring.mentoring.dto.response.MentoringCardToResponse;
import com.project.sooktoring.mentoring.enumerate.MentoringState;
import com.project.sooktoring.mentoring.repository.MentoringCardRepository;
import com.project.sooktoring.mentoring.repository.MentoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.project.sooktoring.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class MentoringCardService {

    private final MentoringRepository mentoringRepository;
    private final MentoringCardRepository mentoringCardRepository;

    public List<MentoringCardFromResponse> getMentoringCardListFromMe(Long menteeId) {
        return mentoringCardRepository.findAllFromDto(menteeId);
    }

    public MentoringCardFromResponse getMentoringCardFromMe(Long menteeId, Long mtrCardId) {
        Mentoring mentoring = mentoringRepository.findById(mtrCardId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMenteeProfile().getId(), menteeId)) {
            mentoringCardRepository.findById(mtrCardId)
                    .orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING_CARD));
            return mentoringCardRepository.findFromDtoById(mtrCardId);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    public List<MentoringCardToResponse> getMentoringCardListToMe(Long mentorId) {
        return mentoringCardRepository.findAllToDto(mentorId);
    }

    @Transactional
    public void save(Long menteeId, Long mtrId, MentoringCardRequest mtrCardRequest) {
        Mentoring mentoring = mentoringRepository.findById(mtrId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMenteeProfile().getId(), menteeId)) {
            if (mentoring.getState().equals(MentoringState.END)) {
                Optional<MentoringCard> mentoringCardOptional = mentoringCardRepository.findById(mtrId);
                if (mentoringCardOptional.isEmpty()) {
                    MentoringCard mentoringCard = MentoringCard.builder()
                            .mentoring(mentoring)
                            .title(mtrCardRequest.getTitle())
                            .content(mtrCardRequest.getContent())
                            .build();

                    mentoringCardRepository.save(mentoringCard);
                }
                throw new CustomException(ALREADY_MENTORING_CARD_EXISTS);
            }
            throw new CustomException(FORBIDDEN_MENTORING_CARD_WRITE);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void update(Long menteeId, Long mtrCardId, MentoringCardRequest mtrCardRequest) {
        Mentoring mentoring = mentoringRepository.findById(mtrCardId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMenteeProfile().getId(), menteeId)) {
            MentoringCard mentoringCard = mentoringCardRepository.findById(mtrCardId)
                    .orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING_CARD));
                mentoringCard.updateCard(mtrCardRequest.getTitle(), mtrCardRequest.getContent());
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }

    @Transactional
    public void delete(Long menteeId, Long mtrCardId) {
        Mentoring mentoring = mentoringRepository.findById(mtrCardId)
                .orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        if (Objects.equals(mentoring.getMenteeProfile().getId(), menteeId)) {
            MentoringCard mentoringCard = mentoringCardRepository.findById(mtrCardId)
                    .orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING_CARD));
            mentoringCardRepository.delete(mentoringCard);
        }
        throw new CustomException(UNAUTHORIZED_MENTORING_ACCESS);
    }
}
