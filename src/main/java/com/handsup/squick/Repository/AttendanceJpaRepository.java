package com.handsup.squick.Repository;

import com.handsup.squick.Dto.GroupDto.Attend.AttendStatus;
import com.handsup.squick.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceJpaRepository extends JpaRepository<Attendance, Long> {
    Attendance findByAttandanceId(long attendanceId);

    @Query(value = "select a from Attendance a " +
            "join GroupAttendence ga on ga.attendance.attandanceId = a.attandanceId " +
            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId")
    List<Attendance> findMemberDetail(long groupId, long memberId);

    @Query(value = "select m from Member m " +
            "join MemberGroup mg on m.memberId = mg.member.memberId " +
            "join GroupAttendence ga on mg.group.groupId = ga.group.groupId " +
            "where mg.group.groupId = :groupId and m.memberId = :memberId and ga.attendance.time = :date")
    List<Attendance> findMemberAttendance(long groupId, long memberId, LocalDate date);

    @Query(value = "select mg.member.memberName, a.time, a.attendanceStatus from Attendance a " +
            "join GroupAttendence ga on a.attandanceId = ga.attendance.attandanceId " +
            "join MemberGroup mg on ga.group.groupId = mg.member.memberId " +
            "where a.time = :date and ga.group.groupId = :groupId and mg.member.memberId = :memberId")
    List<AttendStatus> findGroupCurAttendStatus(LocalDate date, long groupId, long memberId);

}
