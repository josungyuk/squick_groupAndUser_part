package com.handsup.squick.Service;

import com.handsup.squick.Dto.GroupDto.Attend.AttendCountDto;
import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.AttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberJpaRepository memberJpaRepository;
    private final GroupJpaRepository groupJpaRepository;
    private final AttendanceJpaRepository attendanceJpaRepository;

    // 그룹 내 한 회원의 출결상태별 수
    public AttendCountDto getMemberDetail(long groupId, long memberId){
        List<Attendance> attendList = attendanceJpaRepository.findMemberDetail(groupId, memberId);

        int attend = 0;
        int absent = 0;
        int late = 0;

        while(!attendList.isEmpty()){
            Attendance attendStatus = attendList.remove(0);

            switch(attendStatus.getAttendanceStatus()){
                case STATUS_ATTEND:
                    attend++;
                    break;
                case STATUS_ABSENT:
                    absent++;
                    break;
                case STATUS_LATE:
                    late++;
                    break;
            }
        }

        return AttendCountDto.builder()
                .attend(attend)
                .absent(absent)
                .late(late)
                .build();
    }

    public List getMemberAttendance(long groupId, long memberId, LocalDate date){
        List<Attendance> attendances = attendanceJpaRepository.findMemberAttendance(groupId, memberId, date);

        return attendances;
    }

    public void updateMemberAttendance(long attnedanceId, String status){
        Attendance attendance = attendanceJpaRepository.findByAttandanceId(attnedanceId);

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

        attendanceJpaRepository.save(attendance);
    }

    public void setPin(long memberId){
        Member member = memberJpaRepository.findMemberByMemberId(memberId);

        member.setPin(!member.isPin());
    }
}
