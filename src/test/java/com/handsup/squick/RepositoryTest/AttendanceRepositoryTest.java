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