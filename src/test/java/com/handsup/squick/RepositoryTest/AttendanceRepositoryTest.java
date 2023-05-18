package com.handsup.squick.RepositoryTest;

import com.handsup.squick.Dto.GroupDto.Attend.AttendStatus;
import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.JoinEntity.GroupAttendence;
import com.handsup.squick.Entity.JoinEntity.MemberGroup;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.AttendanceJpaRepository;
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
import static org.mockito.BDDMockito.given;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AttendanceRepositoryTest {
    @Autowired
    GroupJpaRepository groupJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    MemberGroupJpaRepository memberGroupJpaRepository;

    @Autowired
    AttendanceJpaRepository attendanceJpaRepository;

    @Autowired
    GroupAttendanceJpaRepository groupAttendanceJpaRepository;

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

        Attendance attendance = Attendance.builder()
                .attandanceId(id)
                .day(0)
                .date(date)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember = memberJpaRepository.save(member);
        Attendance saveAttendance = attendanceJpaRepository.save(attendance);

        Attendance testAttendance = attendanceJpaRepository.findAttandanceByGroupNameAndMemberNameAndDate(
                group.getGroupName(), member.getMemberName(), date);

        assertThat(attendance.getAttandanceId(), is(equalTo(testAttendance.getAttandanceId())));
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

        Attendance attendance1 = Attendance.builder()
                .attandanceId(1L)
                .day(0)
                .date(date1)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        Attendance attendance2 = Attendance.builder()
                .attandanceId(2L)
                .day(0)
                .date(date2)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        GroupAttendence groupAttendence1 = GroupAttendence.builder()
                .group(group)
                .attendance(attendance1)
                .build();

        GroupAttendence groupAttendence2 = GroupAttendence.builder()
                .group(group)
                .attendance(attendance2)
                .build();

        //when
        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember = memberJpaRepository.save(member);
        MemberGroup saveMg = memberGroupJpaRepository.save(memberGroup);
        Attendance saveAttendance1 = attendanceJpaRepository.save(attendance1);
        Attendance saveAttendance2 = attendanceJpaRepository.save(attendance2);
        GroupAttendence saveGroupAttendance1 = groupAttendanceJpaRepository.save(groupAttendence1);
        GroupAttendence saveGroupAttendance2 = groupAttendanceJpaRepository.save(groupAttendence2);

        List<Attendance> testAttendanceList = attendanceJpaRepository.findAllAttendanceByGroupIdAndMemberId(id, id);
        Attendance testAttendance1 = testAttendanceList.get(0);
        Attendance testAttendance2 = testAttendanceList.get(1);

        assertThat(testAttendance1.getMemberName(), is(equalTo(saveAttendance1.getMemberName())));
        assertThat(testAttendance1.getGroupName(), is(equalTo(saveAttendance1.getGroupName())));
        assertThat(testAttendance1.getDate(), is(equalTo(saveAttendance1.getDate())));

        assertThat(testAttendance2.getMemberName(), is(equalTo(saveAttendance2.getMemberName())));
        assertThat(testAttendance2.getGroupName(), is(equalTo(saveAttendance2.getGroupName())));
        assertThat(testAttendance2.getDate(), is(equalTo(saveAttendance2.getDate())));
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

        Attendance attendance1 = Attendance.builder()
                .attandanceId(1L)
                .day(0)
                .date(date1)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        Attendance attendance2 = Attendance.builder()
                .attandanceId(2L)
                .day(0)
                .date(date2)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        Attendance attendance3 = Attendance.builder()
                .attandanceId(3L)
                .day(0)
                .date(date3)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        GroupAttendence groupAttendence1 = GroupAttendence.builder()
                .group(group)
                .attendance(attendance1)
                .build();

        GroupAttendence groupAttendence2 = GroupAttendence.builder()
                .group(group)
                .attendance(attendance2)
                .build();

        GroupAttendence groupAttendence3 = GroupAttendence.builder()
                .group(group)
                .attendance(attendance3)
                .build();

        //when
        Group saveGroup = groupJpaRepository.save(group);
        Member saveMember = memberJpaRepository.save(member);
        MemberGroup saveMg = memberGroupJpaRepository.save(memberGroup);
        Attendance saveAttendance1 = attendanceJpaRepository.save(attendance1);
        Attendance saveAttendance2 = attendanceJpaRepository.save(attendance2);
        Attendance saveAttendance3 = attendanceJpaRepository.save(attendance3);
        GroupAttendence saveGroupAttendance1 = groupAttendanceJpaRepository.save(groupAttendence1);
        GroupAttendence saveGroupAttendance2 = groupAttendanceJpaRepository.save(groupAttendence2);
        GroupAttendence saveGroupAttendance3 = groupAttendanceJpaRepository.save(groupAttendence3);

        List<Attendance> testAttendanceList = attendanceJpaRepository.findMonthAttendanceByGroupIdAndMemberIdAndDate(id, id, 2023, 5);
        Attendance testAttendance = testAttendanceList.get(0);

        assertThat(1, is(equalTo(testAttendanceList.size())));
        assertThat(attendance1.getGroupName(), is(equalTo(testAttendance.getGroupName())));
        assertThat(attendance1.getMemberName(), is(equalTo(testAttendance.getMemberName())));
        assertThat(attendance1.getDate(), is(equalTo(testAttendance.getDate())));
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

        Attendance attendance = Attendance.builder()
                .day(0)
                .date(date)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_ATTEND)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        GroupAttendence groupAttendence = GroupAttendence.builder()
                .group(group)
                .attendance(attendance)
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
        Attendance testAttendance = attendanceJpaRepository.save(attendance);
        GroupAttendence testGroupAttendance = groupAttendanceJpaRepository.save(groupAttendence);

        long memberId = groupJpaRepository.findMemberIdByGroupId(testGroup.getGroupId()).get(0);

        Attendance testResultAttendance = attendanceJpaRepository.findGroupCurAttendStatus(date, testGroup.getGroupId(), memberId).get(0);

        LocalDate testTime = testResultAttendance.getDate();
        String testMemberName = testResultAttendance.getMemberName();
        String status;

        if(testResultAttendance.getAttendanceStatus() == Attendance.AttendanceStatus.STATUS_ATTEND)
            status = "ATTEND";
        else if(testResultAttendance.getAttendanceStatus() == Attendance.AttendanceStatus.STATUS_ABSENT)
            status = "ABSENT";
        else
            status = "LATE";

        assertThat(attendStatus.getMemberName(), is(equalTo(testMemberName)));
        assertThat(attendStatus.getDate(), is(equalTo(testTime)));
        assertThat(attendStatus.getState(), is(equalTo(status)));
    }
}
