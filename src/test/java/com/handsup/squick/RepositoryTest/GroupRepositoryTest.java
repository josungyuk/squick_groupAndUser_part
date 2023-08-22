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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Transactional
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GroupRepositoryTest {

    @Autowired
    GroupJpaRepository groupJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    MemberGroupJpaRepository memberGroupJpaRepository;

    @Test
    void findByGroupIdTest(){
        //given
        Group group1 = Group.builder()
                .groupId(1L)
                .groupName("test1")
                .masterName("member1")
                .img("img1")
                .description("desc1")
                .invitationCode("aaaaaa")
                .isPin(true)
                .isAlarm(true)
                .build();

        Group group2 = Group.builder()
                .groupId(2L)
                .groupName("test2")
                .masterName("member2")
                .img("img2")
                .description("desc2")
                .invitationCode("bbbbbb")
                .isPin(true)
                .isAlarm(true)
                .build();

        Group group3 = Group.builder()
                .groupId(3L)
                .groupName("test3")
                .masterName("member3")
                .img("img3")
                .description("desc3")
                .invitationCode("cccccc")
                .isPin(true)
                .isAlarm(true)
                .build();

        groupJpaRepository.save(group1);
        groupJpaRepository.save(group2);
        groupJpaRepository.save(group3);

        //when
        Group testGroup = groupJpaRepository.findByGroupId(group1.getGroupId());


        assertThat(group1.getGroupId(), is(equalTo(testGroup.getGroupId())));
        assertThat(group1.getGroupName(), is(equalTo(testGroup.getGroupName())));
        assertThat(group1.getImg(), is(equalTo(testGroup.getImg())));
    }

    @Test
    void findGroupTest(){
        //given
        Group group1 = Group.builder()
                .groupId(1L)
                .groupName("test1")
                .masterName("member1")
                .img("img1")
                .description("desc1")
                .invitationCode("aaaaaa")
                .isPin(true)
                .isAlarm(true)
                .build();

        Group group2 = Group.builder()
                .groupId(2L)
                .groupName("test2")
                .masterName("member2")
                .img("img2")
                .description("desc2")
                .invitationCode("bbbbbb")
                .isPin(true)
                .isAlarm(true)
                .build();

        Group group3 = Group.builder()
                .groupId(3L)
                .groupName("test3")
                .masterName("member3")
                .img("img3")
                .description("desc3")
                .invitationCode("cccccc")
                .isPin(true)
                .isAlarm(true)
                .build();

        groupJpaRepository.save(group1);
        groupJpaRepository.save(group2);
        groupJpaRepository.save(group3);

        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);
        groups.add(group3);

        //when
        List<Group> testGroups = groupJpaRepository.findGroup();

        assertThat(groups.size(), is(equalTo(testGroups.size())));
        assertThat(groups.get(0).getGroupName(), is(equalTo(testGroups.get(0).getGroupName())));
        assertThat(groups.get(1).getGroupName(), is(equalTo(testGroups.get(1).getGroupName())));
        assertThat(groups.get(2).getGroupName(), is(equalTo(testGroups.get(2).getGroupName())));
    }
    @Test
    void createGroupTest(){
        Group group = Group.builder()
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Group test = groupJpaRepository.save(group);

        assertThat(group, is(equalTo(test)));
    }

    @Test
    void findMemberIdByGroupId(){
        Group group = Group.builder()
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member member1 = Member.builder()
                .memberName("Member1")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        Member member2 = Member.builder()
                .memberName("Member2")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_WAIT)
                .email("test@testdsa.com")
                .img("TestImgd")
                .build();

        MemberGroup memberGroup1 = MemberGroup.builder()
                .member(member1)
                .group(group)
                .build();

        MemberGroup memberGroup2 = MemberGroup.builder()
                .member(member2)
                .group(group)
                .build();

        Group testGroup = groupJpaRepository.save(group);
        Member testMember1 = memberJpaRepository.save(member1);
        Member testMember2 = memberJpaRepository.save(member2);
        MemberGroup testMg1 = memberGroupJpaRepository.save(memberGroup1);
        MemberGroup testMg2 = memberGroupJpaRepository.save(memberGroup2);

        List<Long> membersId = groupJpaRepository.findMemberIdByGroupId(testGroup.getGroupId());

        assertThat(membersId.get(0), is(equalTo(testMember1.getMemberId())));
        assertThat(membersId.get(1), is(equalTo(testMember2.getMemberId())));
    }
}
