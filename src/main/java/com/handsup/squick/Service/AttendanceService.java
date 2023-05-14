package com.handsup.squick.Service;

import com.handsup.squick.Dto.AttendanceDto.AttendanceUpdate;
import com.handsup.squick.Dto.GroupDto.Attend.AttendCountDto;
import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Repository.AttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final MemberJpaRepository memberJpaRepository;

    private final GroupJpaRepository groupJpaRepository;

    private final AttendanceJpaRepository attendanceJpaRepository;

    private final MemberGroupJpaRepository memberGroupJpaRepository;
    public AttendCountDto getMemberDetail(long groupId, long memberId){
        List<Attendance> attendList = attendanceJpaRepository.findAllAttendanceByGroupIdAndMemberId(groupId, memberId);

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
        List<Attendance> attendances = attendanceJpaRepository.findDateAttendanceByGroupIdAndMemberIdAndDate(groupId, memberId, date);

        return attendances;
    }

    //테스트 완
    public boolean updateMemberAttendance(AttendanceUpdate dto){
        String groupName = dto.getGroupName();
        String memberName = dto.getMemberName();
        LocalDate date = dto.getDate();
        String status = dto.getStatus();

        Attendance attendance = attendanceJpaRepository.findAttandanceByGroupNameAndMemberNameAndDate(
                groupName, memberName, date
        );

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
        return true;
    }
}
