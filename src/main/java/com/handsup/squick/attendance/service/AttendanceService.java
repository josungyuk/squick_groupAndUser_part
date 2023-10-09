package com.handsup.squick.attendance.service;

import com.handsup.squick.attendance.dto.AttendanceDto;
import com.handsup.squick.attendance.dto.AttendanceUpdateDto;
import com.handsup.squick.attendance.repositoryImpl.MasterAttendanceJpaRepositoryImpl;
import com.handsup.squick.attendance.repositoryImpl.SubAttendanceJpaRepositoryImpl;
import com.handsup.squick.group.dto.AttendCountDto;
import com.handsup.squick.group.dto.AttendanceCreateDto;
import com.handsup.squick.attendance.entity.MasterSubAttendance;
import com.handsup.squick.attendance.entity.MasterAttendance;
import com.handsup.squick.attendance.entity.SubAttendance;
import com.handsup.squick.group.entity.Group;
import com.handsup.squick.group.repositoryImpl.GroupJpaRepositoryImpl;
import com.handsup.squick.member.entity.Member;
import com.handsup.squick.attendance.repository.MasterSubAttendanceJpaRepository;
import com.handsup.squick.attendance.repository.MasterAttendanceJpaRepository;
import com.handsup.squick.attendance.repository.SubAttendanceJpaRepository;
import com.handsup.squick.group.repository.GroupJpaRepository;
import com.handsup.squick.group.repository.MemberGroupJpaRepository;
import com.handsup.squick.member.repository.MemberJpaRepository;
import com.handsup.squick.member.repositoryImpl.MemberJpaRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final MemberJpaRepositoryImpl memberJpaRepositoryImpl;

    private final GroupJpaRepositoryImpl groupJpaRepositoryImpl;

    private final SubAttendanceJpaRepository subAttendanceJpaRepository;
    private final SubAttendanceJpaRepositoryImpl subAttendanceJpaRepositoryImpl;

    private final MasterAttendanceJpaRepository masterAttendanceJpaRepository;
    private final MasterAttendanceJpaRepositoryImpl masterAttendanceJpaRepositoryImpl;

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
        List<SubAttendance> attendList = subAttendanceJpaRepositoryImpl.findByGroupIdAndMemberId(groupId, memberId);

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

        List<SubAttendance> subAttendances = subAttendanceJpaRepositoryImpl.findMonthByGroupIdAndMemberIdAndDate(groupId, memberId, year, month);

        return subAttendances;
    }

    //테스트 완
    public boolean updateMemberAttendance(AttendanceUpdateDto dto){
        String groupName = dto.getGroupName();
        String memberName = dto.getMemberName();
        LocalDateTime date = dto.getDate();
        String status = dto.getStatus();

        long groupId = groupJpaRepositoryImpl.findByName(groupName)
                .orElseThrow( () -> new NullPointerException())
                .getId();
        long memberId = memberJpaRepositoryImpl.findByName(memberName)
                .orElseThrow( () -> new NullPointerException())
                .getId();

        SubAttendance subAttendance = subAttendanceJpaRepositoryImpl.findMonthByGroupIdAndMemberIdAndDate(
                groupId, memberId, date.getYear(), date.getMonthValue()
        ).get(0);

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

        long groupId = groupJpaRepositoryImpl.findByName(groupName)
                .orElseThrow( () -> new NullPointerException())
                .getId();
        long memberId = memberJpaRepositoryImpl.findByName(memberName)
                .orElseThrow( () -> new NullPointerException())
                .getId();

        MasterAttendance masterAttendance = MasterAttendance.builder()
                .memberId(memberId)
                .groupId(groupId)
                .day(1)
                .activation(true)
                .attendanceStatus(MasterAttendance.AttendanceStatus.STATUS_ATTEND)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        MasterAttendance saveMasterAttendance = masterAttendanceJpaRepository.save(masterAttendance);

        return saveMasterAttendance.getId();
    }

    public long bePresent(AttendanceCreateDto dto){
        double latitude = dto.getLatitude();
        double longitude = dto.getLongitude();
        int timeLeft = dto.getTimeLeft();

        Group group = groupJpaRepositoryImpl.findByName(dto.getGroupName())
                .orElseThrow( () -> new NullPointerException());
        Member master = memberJpaRepositoryImpl.findByNameAndGroupId(group.getMasterName(), group.getId())
                .orElseThrow( () -> new NullPointerException());

        long groupId = group.getId();
        long masterId = master.getId();

        List<MasterAttendance> masterAttendances = masterAttendanceJpaRepositoryImpl.findByGroupIdAndMemberId(groupId, masterId);
        MasterAttendance masterAttendance = masterAttendances.get(masterAttendances.size() - 1);

        if(!masterAttendance.isActivation()) return -1;

        double masterLatitude = masterAttendance.getLatitude();
        double masterLongitude = masterAttendance.getLongitude();
        int day = masterAttendance.getDay();

        if(timeLeft <= 0 || !isRange(masterLatitude, masterLongitude, latitude, longitude)) return -1;

        String memberName = dto.getMemberName();

        long subMemberId = memberJpaRepositoryImpl.findByName(memberName)
                .orElseThrow( () -> new NullPointerException())
                .getId();

        SubAttendance subAttendance = SubAttendance.builder()
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

        return saveSubAttendance.getId();
    }

    public long getRemainingTime(long groupId){
        LocalDateTime date = LocalDateTime.now();

        MasterAttendance attendance = masterAttendanceJpaRepositoryImpl.findByGroupIdAndDate(groupId, date)
                .orElseThrow( () -> new NullPointerException());
        LocalTime limit = attendance.getCreated().toLocalTime().plusMinutes(5);
        long remainingTime = ChronoUnit.SECONDS.between(LocalTime.now(),limit);

        if(remainingTime < 0) remainingTime = -1;

        return remainingTime;
    }

    public long finishAttendance(long groupId){
        Pageable pageable = PageRequest.of(0,1);
        Page<MasterAttendance> page = masterAttendanceJpaRepositoryImpl.findByMasterGroupId(groupId, pageable);
        MasterAttendance masterAttendance = page.getContent().get(0);

        if(masterAttendance == null) return -1;

        masterAttendance.setActivation(false);
        MasterAttendance result = masterAttendanceJpaRepository.save(masterAttendance);

        return result.getId();
    }

    //더 구현 필요.
    public long checkAttendance(long attendanceId, long groupId, AttendanceDto dto){
//        Pageable pageable =  PageRequest.of(0,1);
//        Page<MasterAttendance> page = masterAttendanceJpaRepository.findRecentMasterAttendanceByMasterGroupId(groupId, pageable);
//        long masterAttendanceId = page.getContent().get(0).getMasterAttandanceId();

        //이 부분부터 구현 필요
        SubAttendance subAttendance = subAttendanceJpaRepository.findById(attendanceId)
                .orElseThrow( () -> new NullPointerException());

        if(subAttendance == null) return -1;

        return subAttendance.getId();
    }
}
