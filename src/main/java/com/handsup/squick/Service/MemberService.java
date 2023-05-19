package com.handsup.squick.Service;

import com.handsup.squick.Dto.MemberDto.MemberExpelDto;
import com.handsup.squick.Dto.MemberDto.Participatation.ParticipationDto;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.JoinEntity.MemberGroup;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.SubAttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;

    private final GroupJpaRepository groupJpaRepository;

    private final SubAttendanceJpaRepository subAttendanceJpaRepository;

    private final MemberGroupJpaRepository memberGroupJpaRepository;

    public void setPin(long memberId){
        Member member = memberJpaRepository.findMemberByMemberId(memberId);

        member.setPin(!member.isPin());
        memberJpaRepository.save(member);
    }

    public void expelMember(MemberExpelDto dto){
        Member member = memberJpaRepository.findMemberByMemberName(dto.getMemberName());

        long memberId = member.getMemberId();

        MemberGroup memberGroup = memberGroupJpaRepository.findByMemberId(memberId);

        memberGroupJpaRepository.delete(memberGroup);
        memberJpaRepository.delete(member);
    }

    //테스트 완
    public List getMember(long groupId){
        List<Member> members = memberJpaRepository.findMemberByGroupId(groupId);

        return members;
    }

    //테스트 완
    public void participate(ParticipationDto dto){
        Member member = memberJpaRepository.findMemberByMemberId(dto.getMemberId());
        Group group = groupJpaRepository.findByGroupId(dto.getGroupId());

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
        List<Member> members = memberJpaRepository.findMemberByGroupId(groupId);

        List<Member> acceptMembers = new ArrayList<>();

        for(Member member : members){
            if(member.getInvitationStatus() == Member.InvitationStatus.INVITATION_ACCEPT)
                acceptMembers.add(member);
        }

        return acceptMembers;
    }
}
