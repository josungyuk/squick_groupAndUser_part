package com.handsup.squick.ServiceTest;

import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.JoinEntity.MemberGroup;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.SubAttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
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
import static org.hamcrest.Matchers.*;
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
    private SubAttendanceJpaRepository subAttendanceJpaRepository;


    @Test
    void getMemberTest(){
        long groupId = 1L;

        Member member1 = Member.builder()
                .memberId(1L)
                .memberName("member1")
                .email("member1@test.com")
                .img("member1.img")
                .invitationStatus(Member.InvitationStatus.INVITATION_WAIT)
                .isPin(true)
                .build();

        Member member2 = Member.builder()
                .memberId(2L)
                .memberName("member2")
                .email("member2@test.com")
                .img("member2.img")
                .invitationStatus(Member.InvitationStatus.INVITATION_WAIT)
                .isPin(true)
                .build();

        Member member3 = Member.builder()
                .memberId(3L)
                .memberName("member3")
                .email("member3@test.com")
                .img("member3.img")
                .invitationStatus(Member.InvitationStatus.INVITATION_WAIT)
                .isPin(true)
                .build();

        List<Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);
        members.add(member3);

        given(memberJpaRepository.findMemberByGroupId(groupId)).willReturn(members);

        List<Member> testMembers = memberService.getMember(groupId);

        assertThat(member1, is(equalTo(testMembers.get(0))));
        assertThat(member2, is(equalTo(testMembers.get(1))));
        assertThat(member3, is(equalTo(testMembers.get(2))));
    }

    @Test
    void participateTest(){
        long memberId = 1L;
        long groupId = 3L;

        Group group = Group.builder()
                .groupId(groupId)
                .groupName("groupName")
                .masterName("master")
                .img("group.img")
                .description("desc")
                .invitationCode("000000")
                .isAlarm(true)
                .isPin(true)
                .build();

        Member member1 = Member.builder()
                .memberId(memberId)
                .memberName("member1")
                .email("member1@test.com")
                .img("member1.img")
                .invitationStatus(Member.InvitationStatus.INVITATION_WAIT)
                .isPin(true)
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .group(group)
                .member(member1)
                .build();

        member1.setInvitationStatus(Member.InvitationStatus.INVITATION_ACCEPT);
        member1.setGroupName(group.getGroupName());

        given(memberJpaRepository.save(member1)).willReturn(member1);
        given(memberGroupJpaRepository.save(memberGroup)).willReturn(memberGroup);

        Member testMember = memberJpaRepository.save(member1);
        MemberGroup testMemberGroup = memberGroupJpaRepository.save(memberGroup);

        assertThat(memberGroup, is(equalTo(testMemberGroup)));
        assertThat(Member.InvitationStatus.INVITATION_ACCEPT, is(equalTo(testMember.getInvitationStatus())));
        assertThat(member1.getGroupName(), is(equalTo(testMember.getGroupName())));
    }

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

        given(memberJpaRepository.findMemberByGroupId(groupId)).willReturn(members);

        List<Member> testMembers = memberService.getWaitMember(groupId);

        Member testMember1 = testMembers.get(0);
        Member testMember2 = testMembers.get(1);

        assertThat(testMember1, is(equalTo(member1)));
        assertThat(testMember2, is(equalTo(member2)));
    }
}
