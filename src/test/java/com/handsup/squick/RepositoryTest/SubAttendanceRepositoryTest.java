package com.handsup.squick.RepositoryTest;

import com.handsup.squick.Dto.GroupDto.Attend.AttendStatus;
import com.handsup.squick.Entity.JoinEntity.MasterSubAttendance;
import com.handsup.squick.Entity.MasterAttendance;
import com.handsup.squick.Entity.SubAttendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.JoinEntity.GroupAttendance;
import com.handsup.squick.Entity.JoinEntity.MemberGroup;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.JoinRepo.MasterSubAttendanceJpaRepository;
import com.handsup.squick.Repository.SubAttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.GroupAttendanceJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SubAttendanceRepositoryTest {
    @Autowired
    GroupJpaRepository groupJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    MemberGroupJpaRepository memberGroupJpaRepository;

    @Autowired
    SubAttendanceJpaRepository subAttendanceJpaRepository;

    @Autowired
    GroupAttendanceJpaRepository groupAttendanceJpaRepository;

    @Autowired
    MasterSubAttendanceJpaRepository masterSubAttendanceJpaRepository;

    @Test
    void findByAttandanceIdTest(){
        LocalDate date = LocalDate.of(2023, 5, 5);
        long id = 1L;

        Group group = Group.builder()
                .groupId(id)
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member member = Member.builder()
                .memberId(id)
                .memberName("Member")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        SubAttendance subAttendance = SubAttendance.builder()
                .subAttandanceId(id)
                .day(0)
                .date(date)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember = memberJpaRepository.save(member);
        SubAttendance saveSubAttendance = subAttendanceJpaRepository.save(subAttendance);

        SubAttendance testSubAttendance = subAttendanceJpaRepository.findAttandanceByGroupNameAndMemberNameAndDate(
                group.getGroupName(), member.getMemberName(), date);

        assertThat(subAttendance.getSubAttandanceId(), is(equalTo(testSubAttendance.getSubAttandanceId())));
    }

    @Test
    void findAllAttendanceByGroupIdAndMemberIdTest(){
        LocalDate date1 = LocalDate.of(2023, 5, 5);
        LocalDate date2 = LocalDate.of(2023, 5, 6);
        long id = 1L;

        Group group = Group.builder()
                .groupId(id)
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member member = Member.builder()
                .memberId(id)
                .memberName("Member")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        MasterAttendance masterAttendance = MasterAttendance.builder()
                .masterAttandanceId(1L)
                .day(0)
                .date(date1)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName("master")
                .build();

        SubAttendance subAttendance1 = SubAttendance.builder()
                .subAttandanceId(1L)
                .day(0)
                .date(date1)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        SubAttendance subAttendance2 = SubAttendance.builder()
                .subAttandanceId(2L)
                .day(0)
                .date(date2)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        MasterSubAttendance masterSubAttendance1 = MasterSubAttendance.builder()
                .subAttendance(subAttendance1)
                .masterAttendance(masterAttendance)
                .build();

        MasterSubAttendance masterSubAttendance2 = MasterSubAttendance.builder()
                .subAttendance(subAttendance2)
                .masterAttendance(masterAttendance)
                .build();

        GroupAttendance groupAttendance1 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance)
                .build();

        GroupAttendance groupAttendance2 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance)
                .build();

        //when
        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember = memberJpaRepository.save(member);
        MemberGroup saveMg = memberGroupJpaRepository.save(memberGroup);
        SubAttendance saveSubAttendance1 = subAttendanceJpaRepository.save(subAttendance1);
        SubAttendance saveSubAttendance2 = subAttendanceJpaRepository.save(subAttendance2);
        MasterSubAttendance saveMSAttendance1 = masterSubAttendanceJpaRepository.save(masterSubAttendance1);
        MasterSubAttendance saveMSAttendance2 = masterSubAttendanceJpaRepository.save(masterSubAttendance2);
        GroupAttendance saveGroupAttendance1 = groupAttendanceJpaRepository.save(groupAttendance1);
        GroupAttendance saveGroupAttendance2 = groupAttendanceJpaRepository.save(groupAttendance2);

        List<SubAttendance> testSubAttendanceList = subAttendanceJpaRepository.findAllAttendanceByGroupIdAndMemberId(id, id);
        SubAttendance testSubAttendance1 = testSubAttendanceList.get(0);
        SubAttendance testSubAttendance2 = testSubAttendanceList.get(1);

        assertThat(testSubAttendance1.getMemberName(), is(equalTo(saveSubAttendance1.getMemberName())));
        assertThat(testSubAttendance1.getGroupName(), is(equalTo(saveSubAttendance1.getGroupName())));
        assertThat(testSubAttendance1.getDate(), is(equalTo(saveSubAttendance1.getDate())));

        assertThat(testSubAttendance2.getMemberName(), is(equalTo(saveSubAttendance2.getMemberName())));
        assertThat(testSubAttendance2.getGroupName(), is(equalTo(saveSubAttendance2.getGroupName())));
        assertThat(testSubAttendance2.getDate(), is(equalTo(saveSubAttendance2.getDate())));
    }

    @Test
    void findDateAttendanceByGroupIdAndMemberIdAndDateTest(){
        LocalDate date1 = LocalDate.of(2023, 5, 5);
        LocalDate date2 = LocalDate.of(2023, 4, 4);
        LocalDate date3 = LocalDate.of(2023, 7, 7);
        long id = 1L;

        Group group = Group.builder()
                .groupId(id)
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member member = Member.builder()
                .memberId(id)
                .memberName("Member")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        MasterAttendance masterAttendance = MasterAttendance.builder()
                .masterAttandanceId(1L)
                .day(0)
                .date(date1)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName("master")
                .build();

        SubAttendance subAttendance1 = SubAttendance.builder()
                .subAttandanceId(1L)
                .day(0)
                .date(date1)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        SubAttendance subAttendance2 = SubAttendance.builder()
                .subAttandanceId(2L)
                .day(0)
                .date(date2)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        SubAttendance subAttendance3 = SubAttendance.builder()
                .subAttandanceId(3L)
                .day(0)
                .date(date3)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        GroupAttendance groupAttendance1 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance)
                .build();

        GroupAttendance groupAttendance2 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance)
                .build();

        GroupAttendance groupAttendance3 = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance)
                .build();

        //when
        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember = memberJpaRepository.save(member);
        MemberGroup saveMg = memberGroupJpaRepository.save(memberGroup);
        SubAttendance saveSubAttendance1 = subAttendanceJpaRepository.save(subAttendance1);
        SubAttendance saveSubAttendance2 = subAttendanceJpaRepository.save(subAttendance2);
        SubAttendance saveSubAttendance3 = subAttendanceJpaRepository.save(subAttendance3);
        GroupAttendance saveGroupAttendance1 = groupAttendanceJpaRepository.save(groupAttendance1);
        GroupAttendance saveGroupAttendance2 = groupAttendanceJpaRepository.save(groupAttendance2);
        GroupAttendance saveGroupAttendance3 = groupAttendanceJpaRepository.save(groupAttendance3);

        List<SubAttendance> testSubAttendanceList = subAttendanceJpaRepository.findMonthAttendanceByGroupIdAndMemberIdAndDate(id, id, 2023, 5);
        SubAttendance testSubAttendance = testSubAttendanceList.get(0);

        assertThat(1, is(equalTo(testSubAttendanceList.size())));
        assertThat(subAttendance1.getGroupName(), is(equalTo(testSubAttendance.getGroupName())));
        assertThat(subAttendance1.getMemberName(), is(equalTo(testSubAttendance.getMemberName())));
        assertThat(subAttendance1.getDate(), is(equalTo(testSubAttendance.getDate())));
    }

    @Test
    void findGroupcurAttendStatusTest(){
        LocalDate date = LocalDate.of(2023, 5, 5);

        Group group = Group.builder()
                .groupName("TestGroup")
                .description("blabla")
                .masterName("TestMember")
                .img("img")
                .invitationCode("aaaaaa")
                .build();

        Member member = Member.builder()
                .memberName("Member1")
                .isPin(true)
                .invitationStatus(Member.InvitationStatus.INVITATION_ACCEPT)
                .email("test@test.com")
                .img("TestImg")
                .build();

        MemberGroup memberGroup = MemberGroup.builder()
                .member(member)
                .group(group)
                .build();

        MasterAttendance masterAttendance = MasterAttendance.builder()
                .masterAttandanceId(1L)
                .day(0)
                .date(date)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName("master")
                .build();

        SubAttendance subAttendance = SubAttendance.builder()
                .day(0)
                .date(date)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        GroupAttendance groupAttendance = GroupAttendance.builder()
                .group(group)
                .masterAttendance(masterAttendance)
                .build();

        AttendStatus attendStatus = AttendStatus.builder()
                .memberName(member.getMemberName())
                .date(date)
                .state("ATTEND")
                .build();

        //when
        Group testGroup = groupJpaRepository.save(group);
        Member testMember = memberJpaRepository.save(member);
        MemberGroup testMg = memberGroupJpaRepository.save(memberGroup);
        SubAttendance testSubAttendance = subAttendanceJpaRepository.save(subAttendance);
        GroupAttendance testGroupAttendance = groupAttendanceJpaRepository.save(groupAttendance);

        long memberId = groupJpaRepository.findMemberIdByGroupId(testGroup.getGroupId()).get(0);

        SubAttendance testResultSubAttendance = subAttendanceJpaRepository.findGroupCurAttendStatus(date, testGroup.getGroupId(), memberId).get(0);

        LocalDate testTime = testResultSubAttendance.getDate();
        String testMemberName = testResultSubAttendance.getMemberName();
        String status;

        if(testResultSubAttendance.getAttendanceStatus() == SubAttendance.AttendanceStatus.STATUS_ATTEND)
            status = "ATTEND";
        else if(testResultSubAttendance.getAttendanceStatus() == SubAttendance.AttendanceStatus.STATUS_ABSENT)
            status = "ABSENT";
        else
            status = "LATE";

        assertThat(attendStatus.getMemberName(), is(equalTo(testMemberName)));
        assertThat(attendStatus.getDate(), is(equalTo(testTime)));
        assertThat(attendStatus.getState(), is(equalTo(status)));
    }
}
