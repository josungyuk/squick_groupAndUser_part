package com.handsup.squick.RepositoryTest;


import com.handsup.squick.group.entity.Group;
import com.handsup.squick.group.entity.MemberGroup;
import com.handsup.squick.member.entity.Member;
import com.handsup.squick.group.repository.GroupJpaRepository;
import com.handsup.squick.group.repository.MemberGroupJpaRepository;
import com.handsup.squick.member.repository.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
    @Autowired
    GroupJpaRepository groupJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    MemberGroupJpaRepository memberGroupJpaRepository;

    @Test
    void findMemberByMemberNameTest(){
        //given
        String memberName = "Member";

        Member member = Member.builder()
                .memberName(memberName)
                .img("img")
                .email("test@test.com")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .build();

        //when
        Member testMember = memberJpaRepository.save(member);
        String testMemberName = testMember.getMemberName();

        //then
        assertThat(memberName, is(equalTo(testMemberName)));
    }

    @Test
    void findMemberByMemberIdTest(){
        long memberId = 1L;

        Member member = Member.builder()
                .memberId(memberId)
                .memberName("Member")
                .img("img")
                .email("test@test.com")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .build();

        //when
        memberJpaRepository.save(member);
        Member testMember = memberJpaRepository.findMemberByMemberId(memberId);
        long testMemberId = testMember.getMemberId();

        assertThat(memberId, is(equalTo(testMemberId)));
    }

    @Test
    void findMemberByGroupIdTest(){
        long groupId = 1L;

        Member member = Member.builder()
                .memberName("Member")
                .img("img")
                .email("test@test.com")
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .isPin(true)
                .build();

        Group group = Group.builder()
                .groupId(groupId)
                .groupName("Group")
                .img("groupImg")
                .description("desc")
                .masterName("Member")
                .isAlarm(true)
                .isPin(true)
                .invitationCode("aaaaaa")
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        Group testGroup = groupJpaRepository.save(group);
        Member testMember = memberJpaRepository.save(member);
        MemberGroup testMemberGroup = memberGroupJpaRepository.save(memberGroup);

        List<Member> testMemberList = memberJpaRepository.findMemberByGroupId(group.getGroupId());
        Member resultMember = testMemberList.get(0);

        assertThat(1, is(equalTo(testMemberList.size())));
        assertThat(member, is(equalTo(resultMember)));
        assertThat(member.getMemberName(), is(equalTo(resultMember.getMemberName())));
    }

    @Test
    void findMemberByMemberNameAndGroupIdTest(){
        long groupId = 1L;
        String memberName = "MemberName";

        Member member = Member.builder()
                .memberName(memberName)
                .img("img")
                .email("test@test.com")
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .isPin(true)
                .build();

        Group group = Group.builder()
                .groupId(groupId)
                .groupName("Group")
                .img("groupImg")
                .description("desc")
                .masterName(memberName)
                .isAlarm(true)
                .isPin(true)
                .invitationCode("aaaaaa")
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember = memberJpaRepository.save(member);
        MemberGroup saveMemberGroup = memberGroupJpaRepository.save(memberGroup);

        Member testMember = memberJpaRepository.findMemberByMemberNameAndGroupId(memberName, groupId);

        assertThat(member, is(equalTo(testMember)));
        assertThat(member.getMemberName(), is(equalTo(testMember.getMemberName())));
        assertThat(saveGroup.getMasterName(), is(equalTo(testMember.getMemberName())));
    }


}
