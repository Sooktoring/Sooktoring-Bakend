package com.project.sooktoring.common.utils;

import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.repository.MentoringRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MentoringUtil {

    private final MentoringRepository mentoringRepository;

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
}
