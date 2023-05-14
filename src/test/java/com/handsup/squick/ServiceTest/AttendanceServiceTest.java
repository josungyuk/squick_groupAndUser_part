package com.handsup.squick.ServiceTest;

import com.handsup.squick.Dto.AttendanceDto.AttendanceUpdate;
import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Repository.AttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import com.handsup.squick.Service.AttendanceService;
import com.handsup.squick.Service.GroupService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.transaction.Transactional;
import java.time.LocalDate;

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
