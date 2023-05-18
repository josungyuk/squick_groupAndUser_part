package com.handsup.squick.Service;

import com.handsup.squick.Dto.AttendanceDto.AttendanceUpdateDto;
import com.handsup.squick.Dto.GroupDto.Attend.AttendCountDto;
import com.handsup.squick.Dto.GroupDto.Attend.AttendanceCreateDto;
import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.AttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final MemberJpaRepository memberJpaRepository;

    private final GroupJpaRepository groupJpaRepository;

    private final AttendanceJpaRepository attendanceJpaRepository;

    private final MemberGroupJpaRepository memberGroupJpaRepository;

    static final double RADIUS_OF_EARTH_KM = 6371;

    public boolean isRange(double lat1, double lon1, double lat2, double lon2){
        double distance = calculateDistance(lat1, lon1, lat2, lon2);

        return (distance <= 0.5);
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double deltaLatRad = Math.toRadians(lat2 - lat1);
        double deltaLonRad = Math.toRadians(lon2 - lon1);

        double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIUS_OF_EARTH_KM * c;
    }

    //테스트 완
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

    //테스트 완
    public List getMemberAttendance(long groupId, long memberId, LocalDate date){
        int year = date.getYear();
        int month = date.getMonthValue();

        List<Attendance> attendances = attendanceJpaRepository.findMonthAttendanceByGroupIdAndMemberIdAndDate(groupId, memberId, year, month);

        return attendances;
    }

    //테스트 완
    public boolean updateMemberAttendance(AttendanceUpdateDto dto){
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

    public boolean createAttendance(AttendanceCreateDto dto){
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();

        Attendance attendance = Attendance.builder()
                .date(LocalDate.now())
                .memberName(dto.getMemberName())
                .groupName(dto.getGroupName())
                .day(1)
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_ATTEND)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        attendanceJpaRepository.save(attendance);

        return true;
    }

    public boolean bePresent(AttendanceCreateDto dto){
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();
        int timeLeft = dto.getTimeLeft();

        Group group = groupJpaRepository.findGroupByGroupName(dto.getGroupName());
        Member master = memberJpaRepository.findMemberByGroupIdAndMemberName(group.getGroupId(), group.getMasterName());

        String groupName = group.getGroupName();
        String masterName = group.getMasterName();
        long groupId = group.getGroupId();
        long masterId = master.getMemberId();

        List<Attendance> attendances = attendanceJpaRepository.findAllAttendanceByGroupIdAndMemberId(groupId, masterId);
        Attendance masterAttendance = attendances.get(attendances.size()-1);

        LocalDate firstDay = attendances.get(0).getDate();
        LocalDate lastDay = attendances.get(attendances.size() - 1).getDate();

        Period period = Period.between(firstDay, lastDay);

        double masterLatitude = masterAttendance.getLatitude();
        double masterLongutude = masterAttendance.getLongitude();

        if(timeLeft <= 0 && !isRange(masterLatitude, masterLongutude, latitude, longitude)) return false;

        Attendance attendance = Attendance.builder()
                .date(LocalDate.now())
                .memberName(dto.getMemberName())
                .groupName(dto.getGroupName())
                .day(period.getDays())
                .attendanceStatus(Attendance.AttendanceStatus.STATUS_ATTEND)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        attendanceJpaRepository.save(attendance);

        return true;
    }
}
