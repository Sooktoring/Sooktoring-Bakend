package com.project.sooktoring.mentoring.service;

import com.project.sooktoring.common.exception.CustomException;
import com.project.sooktoring.common.utils.ProfileUtil;
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

import static com.project.sooktoring.common.exception.ErrorCode.*;

@RequiredArgsConstructor
@Service
public class MentoringCardService {

    private final ProfileUtil profileUtil;
    private final MentoringRepository mentoringRepository;
    private final MentoringCardRepository mentoringCardRepository;

    public List<MentoringCardFromResponse> getMentoringCardListFromMe() {
        Long profileId = profileUtil.getCurrentProfile().getId();
        return mentoringCardRepository.findAllFromDto(profileId);
    }

    //멘토링 ID는 멘토링 카드의 FK이자 PK
    public MentoringCardFromResponse getMentoringCardFromMe(Long mentoringCardId) {
        _getMentoringCard(mentoringCardId, false);
        return mentoringCardRepository.findFromDtoById(mentoringCardId);
    }

    public List<MentoringCardToResponse> getMentoringCardListToMe() {
        Long profileId = profileUtil.getCurrentProfile().getId();
        return mentoringCardRepository.findAllToDto(profileId);
    }

    public MentoringCardToResponse getMentoringCardToMe(Long mentoringCardId) {
        _getMentoringCard(mentoringCardId, true);
        return mentoringCardRepository.findToDtoById(mentoringCardId);
    }

    @Transactional
    public void save(Long mentoringId, MentoringCardRequest mentoringCardRequest) {
        Mentoring mentoring = _getMentoring(mentoringId);
        if (mentoringCardRepository.findById(mentoringId).isPresent()) {
            throw new CustomException(ALREADY_MENTORING_CARD_EXISTS);
        }

        MentoringCard mentoringCard = MentoringCard.builder()
                .mentoring(mentoring)
                .title(mentoringCardRequest.getTitle())
                .content(mentoringCardRequest.getContent())
                .build();
        mentoringCardRepository.save(mentoringCard);
    }

    @Transactional
    public void update(Long mentoringCardId, MentoringCardRequest mentoringCardRequest) {
        MentoringCard mentoringCard = _getMentoringCard(mentoringCardId, false);
        mentoringCard.updateCard(mentoringCardRequest.getTitle(), mentoringCardRequest.getContent());
    }

    @Transactional
    public void delete(Long mentoringCardId) {
        MentoringCard mentoringCard = _getMentoringCard(mentoringCardId, false);
        mentoringCardRepository.delete(mentoringCard);
    }

    //=== private 메소드 ===

    private Mentoring _getMentoring(Long mentoringId) {
        Mentoring mentoring = mentoringRepository.findById(mentoringId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        Long profileId = profileUtil.getCurrentProfile().getId();

        if (!Objects.equals(mentoring.getMenteeProfile().getId(), profileId)) {
            throw new CustomException(FORBIDDEN_MENTORING_ACCESS);
        }
        if (!mentoring.getState().equals(MentoringState.END)) {
            throw new CustomException(FORBIDDEN_MENTORING_CARD_WRITE);
        }
        return mentoring;
    }

    //해당 멘토링의 존재 여부 & 현재 인증된 이용자가 해당 멘토링의 멘티인지 여부 & 해당 멘토링 카드의 존재 여부
    private MentoringCard _getMentoringCard(Long mentoringCardId, Boolean isMentor) {
        Mentoring mentoring = mentoringRepository.findById(mentoringCardId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING));
        Long profileId = profileUtil.getCurrentProfile().getId();

        if (isMentor && !Objects.equals(mentoring.getMentorProfile().getId(), profileId) ||
            !isMentor && !Objects.equals(mentoring.getMenteeProfile().getId(), profileId)) {
            throw new CustomException(FORBIDDEN_MENTORING_ACCESS);
        }
        return mentoringCardRepository.findById(mentoringCardId).orElseThrow(() -> new CustomException(NOT_FOUND_MENTORING_CARD));
    }
}
