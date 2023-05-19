package com.handsup.squick.ServiceTest;

import com.handsup.squick.Dto.GroupDto.Attend.AttendStatus;
import com.handsup.squick.Dto.MemberDto.MemberAddDto;
import com.handsup.squick.Entity.SubAttendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.JoinEntity.MemberGroup;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.SubAttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import com.handsup.squick.Service.GroupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@Transactional
@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {
    @InjectMocks
    private GroupService groupService;

    @Mock
    private GroupJpaRepository groupJpaRepository;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private MemberGroupJpaRepository memberGroupJpaRepository;

    @Mock
    private SubAttendanceJpaRepository subAttendanceJpaRepository;


    @Test
    public void groupReadTest(){
        String code = "000000";
        long groupId1 = 1L;
        long groupId2 = 2L;
        String memberName = "Member";

        Group group1= Group.builder()
                .groupId(groupId1)
                .groupName("group1")
                .img("img")
                .masterName(memberName)
                .description("desc")
                .isPin(true)
                .isAlarm(true)
                .invitationCode(code)
                .build();

        Group group2 = Group.builder()
                .groupId(groupId2)
                .groupName("group2")
                .img("img")
                .masterName(memberName)
                .description("desc")
                .isPin(true)
                .isAlarm(true)
                .invitationCode(code)
                .build();

        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        given(groupJpaRepository.findGroup()).willReturn(groups);

        List<Group> testGroups = groupService.groupRead();
        Group testGroup1 = testGroups.get(0);
        Group testGroup2 = testGroups.get(1);

        assertThat(group1.getGroupName(), is(equalTo(testGroup1.getGroupName())));
        assertThat(group1.getGroupId(), is(equalTo(testGroup1.getGroupId())));
        assertThat(group1.getImg(), is(equalTo(testGroup1.getImg())));

        assertThat(group2.getGroupName(), is(equalTo(testGroup2.getGroupName())));
        assertThat(group2.getGroupId(), is(equalTo(testGroup2.getGroupId())));
        assertThat(group2.getImg(), is(equalTo(testGroup2.getImg())));

    }
    @Test
    public void getCodeTest(){
        String code = groupService.getCode();

        System.out.println(code);
    }
    @Test
    public void isVaildCodeTest(){
        String code = "000000";
        long groupId = 1L;
        long memberId = 1L;
        String memberName = "Member";

        Group group = Group.builder()
                .groupId(groupId)
                .groupName("group")
                .img("img")
                .masterName(memberName)
                .description("desc")
                .isPin(true)
                .isAlarm(true)
                .invitationCode(code)
                .build();

        Member member = Member.builder()
                .memberId(memberId)
                .memberName(memberName)
                .email("test@test.com")
                .img("image")
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        MemberAddDto dto = MemberAddDto.builder()
                .memberName(memberName)
                .code(code)
                .build();

        given(groupJpaRepository.findByGroupId(groupId)).willReturn(group);
        given(memberJpaRepository.findMemberByMemberNameAndGroupId(memberName, groupId)).willReturn(member);

        Group testGroup = groupJpaRepository.save(group);
        Member testMember = memberJpaRepository.save(member);
        MemberGroup testMemberGroup = memberGroupJpaRepository.save(memberGroup);

        boolean isVaild = groupService.isVaildCode(groupId, dto);

        assertThat(true, is(equalTo(isVaild)));
    }

    @Test
    public void groupCreateTest(){
        //given
        Group group = Group.builder()
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member member = Member.builder()
                .memberName("TestMember")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        given(memberGroupJpaRepository.save(memberGroup)).willReturn(memberGroup);
        given(memberJpaRepository.save(member)).willReturn(member);
        given(groupJpaRepository.save(group)).willReturn(group);

        //when
        Member testMember = memberJpaRepository.save(member);
        Group testGroup = groupJpaRepository.save(group);
        MemberGroup testMemberGroup = memberGroupJpaRepository.save(memberGroup);

        //then
        assertThat(testMember, is(equalTo(member)));
        assertThat(testGroup, is(equalTo(group)));
        assertThat(testMemberGroup, is(equalTo(testMemberGroup)));
    }

    @Test
    public void groupUpdateTest(){
        //given
        Group group = Group.builder()
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        groupJpaRepository.save(group);

        group.setGroupName("TestUpdateGroup");

        given(groupJpaRepository.save(group)).willReturn(group);

        //when
        Group testGroup = groupJpaRepository.save(group);

        //then
        assertThat(testGroup, is(equalTo(group)));
    }

    @Test
    public void getAttendanceStatusTest(){
        //given
        LocalDate date = LocalDate.of(2023, 5, 5);

        Group group = Group.builder()
                .groupId(1L)
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member member1 = Member.builder()
                .memberId(1L)
                .memberName("Member1")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        Member member2 = Member.builder()
                .memberId(2L)
                .memberName("Member2")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_WAIT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        SubAttendance subAttendance1 = SubAttendance.builder()
                .day(0)
                .date(date)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member1.getMemberName())
                .build();

        SubAttendance subAttendance2 = SubAttendance.builder()
                .day(0)
                .date(date)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ABSENT)
                .groupName(group.getGroupName())
                .memberName(member2.getMemberName())
                .build();

        AttendStatus attend1 = AttendStatus.builder()
                .memberName(member1.getMemberName())
                .date(date)
                .state("ATTEND")
                .build();

        AttendStatus attend2 = AttendStatus.builder()
                .memberName(member2.getMemberName())
                .date(date)
                .state("ABSENT")
                .build();

        //groupJpaRepository.findMemberIdByGroupId의 반환된 결과
        List<Long> membersId = new ArrayList<>();
        membersId.add(member1.getMemberId());
        membersId.add(member2.getMemberId());

        //attendanceJpaRepository.findGroupCurAttendStatus의 member1 Id 를 검색하여 반환된 결과
        List<SubAttendance> member1Attend = new ArrayList<>();
        member1Attend.add(subAttendance1);

        //attendanceJpaRepository.findGroupCurAttendStatus의 member2 Id 를 검색하여 반환된 결과
        List<SubAttendance> member2Attend = new ArrayList<>();
        member2Attend.add(subAttendance2);

        //groupService.getAttendanceStatus의 반환된 결과
        List<AttendStatus> list1 = new ArrayList<>();
        list1.add(attend1);

        List<AttendStatus> list2 = new ArrayList<>();
        list2.add(attend2);

        HashMap<Integer, List<AttendStatus>> map = new HashMap<>();

        map.put(1, list1);
        map.put(2, list2);

        given(groupJpaRepository.findMemberIdByGroupId(group.getGroupId())).willReturn(membersId);
        given(subAttendanceJpaRepository.findGroupCurAttendStatus(date, group.getGroupId(), membersId.get(0))).willReturn(member1Attend);
        given(subAttendanceJpaRepository.findGroupCurAttendStatus(date, group.getGroupId(), membersId.get(1))).willReturn(member2Attend);

        //when
        HashMap<Integer, List<AttendStatus>> testMap = groupService.getAttendanceStatus(date, group.getGroupId());

        //then
        assertThat(testMap.size(), is(map.size()));
        assertThat(testMap.get(0).size(), is(equalTo(map.get(1).size())));

        assertThat(testMap.get(0).get(0).getMemberName(), is(equalTo(map.get(1).get(0).getMemberName())));
        assertThat(testMap.get(0).get(0).getDate(), is(equalTo(map.get(1).get(0).getDate())));
        assertThat(testMap.get(0).get(0).getState(), is(equalTo(map.get(1).get(0).getState())));

        assertThat(testMap.get(1).get(0).getMemberName(), is(equalTo(map.get(2).get(0).getMemberName())));
        assertThat(testMap.get(1).get(0).getDate(), is(equalTo(map.get(2).get(0).getDate())));
        assertThat(testMap.get(1).get(0).getState(), is(equalTo(map.get(2).get(0).getState())));
    }
}
