package com.handsup.squick.Service;

import com.handsup.squick.Dto.AttendanceDto.AttendanceDto;
import com.handsup.squick.Dto.AttendanceDto.AttendanceUpdateDto;
import com.handsup.squick.Dto.GroupDto.Attend.AttendCountDto;
import com.handsup.squick.Dto.GroupDto.Attend.AttendanceCreateDto;
import com.handsup.squick.Entity.JoinEntity.MasterSubAttendance;
import com.handsup.squick.Entity.MasterAttendance;
import com.handsup.squick.Entity.SubAttendance;
import com.handsup.squick.Entity.Group;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Repository.JoinRepo.MasterSubAttendanceJpaRepository;
import com.handsup.squick.Repository.MasterAttendanceJpaRepository;
import com.handsup.squick.Repository.SubAttendanceJpaRepository;
import com.handsup.squick.Repository.GroupJpaRepository;
import com.handsup.squick.Repository.JoinRepo.MemberGroupJpaRepository;
import com.handsup.squick.Repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final MemberJpaRepository memberJpaRepository;

    private final GroupJpaRepository groupJpaRepository;

    private final SubAttendanceJpaRepository subAttendanceJpaRepository;

    private final MasterAttendanceJpaRepository masterAttendanceJpaRepository;

    private final MasterSubAttendanceJpaRepository masterSubAttendanceJpaRepository;

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
        List<SubAttendance> attendList = subAttendanceJpaRepository.findAllAttendanceByGroupIdAndMemberId(groupId, memberId);

        int attend = 0;
        int absent = 0;
        int late = 0;

        while(!attendList.isEmpty()){
            SubAttendance attendStatus = attendList.remove(0);

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

        List<SubAttendance> subAttendances = subAttendanceJpaRepository.findMonthAttendanceByGroupIdAndMemberIdAndDate(groupId, memberId, year, month);

        return subAttendances;
    }

    //테스트 완
    public boolean updateMemberAttendance(AttendanceUpdateDto dto){
        String groupName = dto.getGroupName();
        String memberName = dto.getMemberName();
        LocalDate date = dto.getDate();
        String status = dto.getStatus();

        SubAttendance subAttendance = subAttendanceJpaRepository.findAttandanceByGroupNameAndMemberNameAndDate(
                groupName, memberName, date
        );

        switch(status){
            case "ATTEND":
                subAttendance.setAttendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND);
                break;
            case "ABSENT":
                subAttendance.setAttendanceStatus(SubAttendance.AttendanceStatus.STATUS_ABSENT);
                break;
            case "LATE":
                subAttendance.setAttendanceStatus(SubAttendance.AttendanceStatus.STATUS_LATE);
                break;
        }

        subAttendanceJpaRepository.save(subAttendance);
        return true;
    }

    public long createAttendance(AttendanceCreateDto dto){
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();

        MasterAttendance masterAttendance = MasterAttendance.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .memberName(dto.getMemberName())
                .groupName(dto.getGroupName())
                .day(1)
                .activation(true)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        MasterAttendance saveMasterAttendance = masterAttendanceJpaRepository.save(masterAttendance);

        return saveMasterAttendance.getMasterAttandanceId();
    }

    public long bePresent(AttendanceCreateDto dto){
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();
        int timeLeft = dto.getTimeLeft();

        Group group = groupJpaRepository.findGroupByGroupName(dto.getGroupName());
        Member master = memberJpaRepository.findMemberByGroupIdAndMemberName(group.getGroupId(), group.getMasterName());

        long groupId = group.getGroupId();
        long masterId = master.getMemberId();

        List<MasterAttendance> masterAttendances = masterAttendanceJpaRepository.findMasterAttendanceByGroupIdAndMemberId(groupId, masterId);
        MasterAttendance masterAttendance = masterAttendances.get(masterAttendances.size() - 1);

        if(!masterAttendance.isActivation()) return -1;

        double masterLatitude = masterAttendance.getLatitude();
        double masterLongitude = masterAttendance.getLongitude();
        int day = masterAttendance.getDay();

        if(timeLeft <= 0 && !isRange(masterLatitude, masterLongitude, latitude, longitude)) return -1;

        SubAttendance subAttendance = SubAttendance.builder()
                .date(LocalDate.now())
                .memberName(dto.getMemberName())
                .groupName(dto.getGroupName())
                .day(day + 1)
                .attendanceStatus(SubAttendance.AttendanceStatus.STATUS_ATTEND)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        MasterSubAttendance masterSubAttendance = MasterSubAttendance.builder()
                .masterAttendance(masterAttendance)
                .subAttendance(subAttendance)
                .build();

        SubAttendance saveSubAttendance = subAttendanceJpaRepository.save(subAttendance);
        masterSubAttendanceJpaRepository.save(masterSubAttendance);

        return saveSubAttendance.getSubAttandanceId();
    }

    public long getRemainingTime(long groupId){
        LocalDate date = LocalDate.now();
        String groupName = groupJpaRepository.findByGroupId(groupId).getGroupName();

        MasterAttendance attendance = masterAttendanceJpaRepository.findMasterAttendanceByGroupNameAndDate(groupName, date);
        LocalTime limit = attendance.getTime().plusMinutes(5);
        long remainingTime = ChronoUnit.SECONDS.between(limit, LocalTime.now());

        return remainingTime;
    }

    public long finishAttendance(long attendanceId){
        MasterAttendance masterAttendance = masterAttendanceJpaRepository.findRecentMasterAttendanceByMasterAttandanceId(attendanceId);

        if(masterAttendance == null) return -1;

        masterAttendance.setActivation(false);
        masterAttendanceJpaRepository.save(masterAttendance);

        return attendanceId;
    }

    public long checkAttendance(long attendanceId, AttendanceDto dto){
        MasterAttendance masterAttendance = masterAttendanceJpaRepository.findRecentMasterAttendanceByMasterAttandanceId(attendanceId);
        long masterAttendanceId = masterAttendance.getMasterAttandanceId();

        SubAttendance subAttendance = subAttendanceJpaRepository.findById(attendanceId);

        if(subAttendance == null) return -1;

        return subAttendance.getSubAttandanceId();
    }
}
