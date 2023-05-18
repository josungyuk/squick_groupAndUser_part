package com.handsup.squick.ServiceTest;

import com.handsup.squick.Dto.GroupDto.Attend.AttendCountDto;
import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.AttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import com.handsup.squick.Service.AttendanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;

@Transactional
@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTest {
    @InjectMocks
    private AttendanceService attendanceService;

    @Mock
    private GroupJpaRepository groupJpaRepository;

    @Mock
    private MemberJpaRepository memberJpaRepository;

    @Mock
    private MemberGroupJpaRepository memberGroupJpaRepository;

    @Mock
    private AttendanceJpaRepository attendanceJpaRepository;

    @Test
    void getMemberDetail(){
        LocalDate date1 = LocalDate.of(2023, 5, 5);
        LocalDate date2 = LocalDate.of(2023, 4, 4);
        LocalDate date3 = LocalDate.of(2023, 7, 7);
        LocalDate date4 = LocalDate.of(2023, 7, 8);
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
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_ABSENT)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        Attendance attendance3 = Attendance.builder()
                .attandanceId(3L)
                .day(0)
                .date(date4)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_LATE)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        Attendance attendance4 = Attendance.builder()
                .attandanceId(4L)
                .day(0)
                .date(date3)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_LATE)
                .groupName(group.getGroupName())
                .memberName(member.getMemberName())
                .build();

        List<Attendance> attendances = new ArrayList<>();
        attendances.add(attendance1);
        attendances.add(attendance2);
        attendances.add(attendance3);
        attendances.add(attendance4);

        AttendCountDto attendCountDto = AttendCountDto.builder()
                .attend(1)
                .absent(1)
                .late(2)
                .build();

        given(attendanceJpaRepository.findAllAttendanceByGroupIdAndMemberId(group.getGroupId(), member.getMemberId())).willReturn(attendances);

        AttendCountDto testDto = attendanceService.getMemberDetail(group.getGroupId(), member.getMemberId());

        assertThat(attendCountDto.getAttend(), is(equalTo(testDto.getAttend())));
        assertThat(attendCountDto.getAbsent(), is(equalTo(testDto.getAbsent())));
        assertThat(attendCountDto.getLate(), is(equalTo(testDto.getLate())));
    }

    @Test
    void updateMemberAttendance(){
        LocalDate date = LocalDate.now();
        String groupName = "groupName";
        String memberName = "memberName";
        String status = "ATTEND";

        Attendance attendance = Attendance.builder()
                .memberName(memberName)
                .groupName(groupName)
                .date(date)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_LATE)
                .day(0)
                .build();


        switch(status){
            case "ATTEND":
                attendance.setAttendanceStatus(Attendance.AttendanceStatus.STATUS_ATTEND);
                break;
            case "ABSENT":
                attendance.setAttendanceStatus(Attendance.AttendanceStatus.STATUS_ABSENT);
                break;
            case "LATE":
                attendance.setAttendanceStatus(Attendance.AttendanceStatus.STATUS_LATE);
                break;
        }

        given(attendanceJpaRepository.save(attendance)).willReturn(attendance);

        Attendance testAttendance = attendanceJpaRepository.save(attendance);

        assertThat(testAttendance.getAttendanceStatus(), is(equalTo(Attendance.AttendanceStatus.STATUS_ATTEND)));
    }
}
