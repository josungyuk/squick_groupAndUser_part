package com.handsup.squick.member.service;

import com.handsup.squick.group.repositoryImpl.GroupJpaRepositoryImpl;
import com.handsup.squick.member.dto.MemberExpelDto;
import com.handsup.squick.member.dto.ParticipationDto;
import com.handsup.squick.group.entity.Group;
import com.handsup.squick.group.entity.MemberGroup;
import com.handsup.squick.member.entity.Member;
import com.handsup.squick.attendance.repository.SubAttendanceJpaRepository;
import com.handsup.squick.group.repository.GroupJpaRepository;
import com.handsup.squick.group.repository.MemberGroupJpaRepository;
import com.handsup.squick.member.repository.MemberJpaRepository;
import com.handsup.squick.member.repositoryImpl.MemberJpaRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final MemberJpaRepositoryImpl memberJpaRepositoryImpl;

    private final GroupJpaRepository groupJpaRepository;
    private final GroupJpaRepositoryImpl groupJpaRepositoryImpl;

    private final SubAttendanceJpaRepository subAttendanceJpaRepository;

    private final MemberGroupJpaRepository memberGroupJpaRepository;

    public void setPin(long memberId){
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new NullPointerException());

        member.setIsPin(!member.getIsPin());
        memberJpaRepository.save(member);
    }

    public void expelMember(MemberExpelDto dto){
        Member member = memberJpaRepositoryImpl.findByName(dto.getMemberName())
                .orElseThrow(() -> new NullPointerException());

        long memberId = member.getId();

        MemberGroup memberGroup = memberGroupJpaRepository.findByMemberId(memberId);

        memberGroupJpaRepository.delete(memberGroup);
        memberJpaRepository.delete(member);
    }

    //테스트 완
    public List getMember(long groupId){
        List<Member> members = memberJpaRepositoryImpl.findByGroupId(groupId);

        return members;
    }

    //테스트 완
    public void participate(ParticipationDto dto){
        Member member = memberJpaRepositoryImpl.findById(dto.getMemberId())
                .orElseThrow(() -> new NullPointerException());;
        Group group = groupJpaRepositoryImpl.findById(dto.getGroupId())
                .orElseThrow( () -> new NullPointerException());

        member.setInvitationStatus(Member.InvitationStatus.INVITATION_ACCEPT);
        member.setGroupName(group.getGroupName());
        memberJpaRepository.save(member);

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        memberGroupJpaRepository.save(memberGroup);
    }

    //테스트 완
    public List getWaitMember(long groupId){
        List<Member> members = memberJpaRepositoryImpl.findByGroupId(groupId);

        List<Member> acceptMembers = new ArrayList<>();

        for(Member member : members){
            if(member.getInvitationStatus() == Member.InvitationStatus.INVITATION_ACCEPT)
                acceptMembers.add(member);
        }

        return acceptMembers;
    }
}
