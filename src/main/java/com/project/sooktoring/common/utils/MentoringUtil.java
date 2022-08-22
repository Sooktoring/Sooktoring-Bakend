package com.project.sooktoring.common.utils;

import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.repository.MentoringRepository;
import com.project.sooktoring.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.project.sooktoring.mentoring.enumerate.MentoringState.*;

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

    public void changeStateByRole(Boolean requestedIsMentor, Profile profile) {
        Long profileId = profile.getId();

        //멘토 -> 멘티 : APPLY -> INVALID, ACCEPT -> END
        if (profile.getIsMentor() && !requestedIsMentor) {
            //나에게 온 멘토링 신청내역 APPLY -> INVALID, ACCEPT -> END 로 변경
            List<Mentoring> appliedMentoringListToMe = mentoringRepository.findByMentorProfileIdAndState(profileId, APPLY);
            for (Mentoring mentoring : appliedMentoringListToMe) {
                mentoring.invalid();
            }
            List<Mentoring> acceptedMentoringListToMe = mentoringRepository.findByMentorProfileIdAndState(profileId, ACCEPT);
            for (Mentoring mentoring : acceptedMentoringListToMe) {
                mentoring.end();
            }
        }
        //멘티 -> 멘토 : INVALID -> APPLY
        if (!profile.getIsMentor() && requestedIsMentor) {
            //이전에 멘토 -> 멘티 -> 멘토로 변경하는 경우 INVALID 상태의 나에게 온 멘토링 신청내역 APPLY 로 변경
            List<Mentoring> invalidMentoringListToMe = mentoringRepository.findByMentorProfileIdAndState(profileId, INVALID);
            for (Mentoring mentoring : invalidMentoringListToMe) {
                mentoring.apply();
            }
        }
    }
}
