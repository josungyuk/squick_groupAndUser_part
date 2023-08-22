package com.handsup.squick.attendance.service;

import com.handsup.squick.attendance.dto.AttendanceDto;
import com.handsup.squick.attendance.dto.AttendanceUpdateDto;
import com.handsup.squick.group.dto.AttendCountDto;
import com.handsup.squick.group.dto.AttendanceCreateDto;
import com.handsup.squick.attendance.entity.MasterSubAttendance;
import com.handsup.squick.attendance.entity.MasterAttendance;
import com.handsup.squick.attendance.entity.SubAttendance;
import com.handsup.squick.group.entity.Group;
import com.handsup.squick.member.entity.Member;
import com.handsup.squick.attendance.repository.MasterSubAttendanceJpaRepository;
import com.handsup.squick.attendance.repository.MasterAttendanceJpaRepository;
import com.handsup.squick.attendance.repository.SubAttendanceJpaRepository;
import com.handsup.squick.group.repository.GroupJpaRepository;
import com.handsup.squick.group.repository.MemberGroupJpaRepository;
import com.handsup.squick.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public List getMonthMemberAttendance(long groupId, long memberId, LocalDate date){
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

        long groupId = groupJpaRepository.findGroupByGroupName(groupName).getGroupId();
        long memberId = memberJpaRepository.findMemberByMemberName(memberName).getMemberId();

        SubAttendance subAttendance = subAttendanceJpaRepository.findSubAttandanceByGroupIdAndMemberIdAndDate(
                groupId, memberId, date
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

        String groupName = dto.getGroupName();
        String memberName = dto.getMemberName();

        long groupId = groupJpaRepository.findGroupByGroupName(groupName).getGroupId();
        long memberId = memberJpaRepository.findMemberByMemberName(memberName).getMemberId();

        MasterAttendance masterAttendance = MasterAttendance.builder()
                .date(LocalDate.now())
                .time(LocalTime.now())
                .memberId(memberId)
                .groupId(groupId)
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

        if(timeLeft <= 0 || !isRange(masterLatitude, masterLongitude, latitude, longitude)) return -1;

        String memberName = dto.getMemberName();

        long subMemberId = memberJpaRepository.findMemberByMemberName(memberName).getMemberId();

        SubAttendance subAttendance = SubAttendance.builder()
                .date(LocalDate.now())
                .memberId(subMemberId)
                .groupId(groupId)
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

        MasterAttendance attendance = masterAttendanceJpaRepository.findMasterAttendanceByGroupIdAndDate(groupId, date);
        LocalTime limit = attendance.getTime().plusMinutes(5);
        long remainingTime = ChronoUnit.SECONDS.between(LocalTime.now(),limit);

        if(remainingTime < 0) remainingTime = -1;

        return remainingTime;
    }

    public long finishAttendance(long groupId){
        Pageable pageable = PageRequest.of(0,1);
        Page<MasterAttendance> page = masterAttendanceJpaRepository.findRecentMasterAttendanceByMasterGroupId(groupId, pageable);
        MasterAttendance masterAttendance = page.getContent().get(0);

        if(masterAttendance == null) return -1;

        masterAttendance.setActivation(false);
        MasterAttendance result = masterAttendanceJpaRepository.save(masterAttendance);

        return result.getMasterAttandanceId();
    }

    //더 구현 필요.
    public long checkAttendance(long attendanceId, long groupId, AttendanceDto dto){
//        Pageable pageable =  PageRequest.of(0,1);
//        Page<MasterAttendance> page = masterAttendanceJpaRepository.findRecentMasterAttendanceByMasterGroupId(groupId, pageable);
//        long masterAttendanceId = page.getContent().get(0).getMasterAttandanceId();

        //이 부분부터 구현 필요
        SubAttendance subAttendance = subAttendanceJpaRepository.findById(attendanceId);

        if(subAttendance == null) return -1;

        return subAttendance.getSubAttandanceId();
    }
}
