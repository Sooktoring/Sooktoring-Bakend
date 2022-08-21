package com.project.sooktoring.mentoring.service;

import com.project.sooktoring.common.utils.ProfileUtil;
import com.project.sooktoring.mentoring.domain.Mentoring;
import com.project.sooktoring.mentoring.repository.MentoringRepository;
import com.project.sooktoring.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.sooktoring.mentoring.enumerate.MentoringState.APPLY;

@RequiredArgsConstructor
@Service
public class MentoringChatService {

    private final ProfileUtil profileUtil;
    private final MentoringRepository mentoringRepository;

    //수정할 예정
    public List<Mentoring> getMyChatRoomList() {
        Profile profile = profileUtil.getCurrentProfile();
        if(profile.getIsMentor()) return mentoringRepository.findByMentorProfileIdAndState(profile.getId(), APPLY);
        else return mentoringRepository.findByMenteeProfileIdAndState(profile.getId(), APPLY);
    }
}
