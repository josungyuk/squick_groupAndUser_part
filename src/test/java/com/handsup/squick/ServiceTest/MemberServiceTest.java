package com.handsup.squick.ServiceTest;

import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.JoinEntity.MemberGroup;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.AttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import com.handsup.squick.Service.GroupService;
import com.handsup.squick.Service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;


@Transactional
@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;

    @Mock
    private GroupJpaRepository groupJpaRepository;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private MemberGroupJpaRepository memberGroupJpaRepository;

    @Mock
    private AttendanceJpaRepository attendanceJpaRepository;

    @Test
    void getWaitMember(){
        long groupId = 1L;

        Group group = Group.builder()
                .groupId(groupId)
                .groupName("Group")
                .img("img")
                .description("desc")
                .masterName("master")
                .invitationCode("000000")
                .isAlarm(true)
                .isPin(true)
                .build();

        Member member1 = Member.builder()
                .memberId(1L)
                .memberName("member1")
                .email("test@test.com")
                .img("img")
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .isPin(true)
                .build();

        Member member2 = Member.builder()
                .memberId(2L)
                .memberName("member2")
                .email("test@test.com")
                .img("img")
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .isPin(true)
                .build();

        Member member3 = Member.builder()
                .memberId(3L)
                .memberName("member3")
                .email("test@test.com")
                .img("img")
                .invitationStatus(Member.InvitationStatus.INVITATION_WAIT)
                .isPin(true)
                .build();

        List<Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);
        members.add(member3);

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member1)
                .group(group)
                .build();

        MemberGroup saveMemberGroup1 = memberGroupJpaRepository.save(memberGroup);


        memberGroup = MemberGroup.builder()
                .member(member2)
                .group(group)
                .build();

        MemberGroup saveMemberGroup2 = memberGroupJpaRepository.save(memberGroup);


        memberGroup = MemberGroup.builder()
                .member(member3)
                .group(group)
                .build();

        MemberGroup saveMemberGroup3 = memberGroupJpaRepository.save(memberGroup);


        Member saveMember1 = memberJpaRepository.save(member1);
        Member saveMember2 = memberJpaRepository.save(member2);
        Member saveMember3 = memberJpaRepository.save(member3);

        given(memberJpaRepository.findMemberByGroupId(groupId)).willReturn(members);

        List<Member> testMembers = memberService.getWaitMember(groupId);

        Member testMember1 = testMembers.get(0);
        Member testMember2 = testMembers.get(1);

        assertThat(testMember1, is(equalTo(member1)));
        assertThat(testMember2, is(equalTo(member2)));
    }
}
