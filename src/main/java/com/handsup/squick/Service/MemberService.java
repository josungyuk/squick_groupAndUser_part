package com.handsup.squick.Service;

import com.handsup.squick.Dto.GroupDto.Attend.AttendCountDto;
import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Dto.MemberDto.MemberAddDto;
import com.handsup.squick.Dto.MemberDto.MemberDeleteDto;
import com.handsup.squick.Dto.MemberDto.MemberExpulsionDto;
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
        List<Attendance> attendList = memberJpaRepository.findMemberDetail(groupId, memberId);

        int attend = 0;
        int absent = 0;
        int late = 0;

        while(!attendList.isEmpty()){
            Attendance attendStatus = attendList.remove(0);

            if(attendStatus.getStatus().equals("attend"))
                attend++;
            else if(attendStatus.getStatus().equals("absent"))
                absent++;
            else if(attendStatus.getStatus().equals("late"))
                late++;
        }

        return AttendCountDto.builder()
                .attend(attend)
                .absent(absent)
                .late(late)
                .build();
    }

    public List getMemberAttendance(long groupId, long memberId, LocalDate date){
        List<Attendance> attendances = memberJpaRepository.findMemberAttendance(groupId, memberId, date);

        return attendances;
    }

    public void updateMemberAttendance(long attnedanceId, String status){
        Attendance attendance = attendanceJpaRepository.findByAttandanceId(attnedanceId);

        attendance.setStatus(status);
        attendanceJpaRepository.save(attendance);
    }
}
