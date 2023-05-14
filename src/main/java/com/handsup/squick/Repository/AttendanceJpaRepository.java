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
    //테스트 완
    @Query(value = "select a from Attendance a " +
            "join GroupAttendence ga on a.attandanceId = ga.attendance.attandanceId " +
            "join MemberAttendance ma on a.attandanceId = ma.attendance.attandanceId " +
            "where ga.group.groupName = :groupName and ma.member.memberName = :memberName and " +
            "a.date = :date")
    Attendance findAttandanceByGroupNameAndMemberNameAndDate(String groupName, String memberName, LocalDate date);

    //테스트 완
    @Query(value = "select a from Attendance a " +
            "join GroupAttendence ga on ga.attendance.attandanceId = a.attandanceId " +
            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId")
    List<Attendance> findAllAttendanceByGroupIdAndMemberId(long groupId, long memberId);

    //테스트 완
    @Query(value = "select a from Attendance a " +
            "join GroupAttendence ga on a.attandanceId = ga.attendance.attandanceId " +
            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId and a.date = :date")
    List<Attendance> findDateAttendanceByGroupIdAndMemberIdAndDate(long groupId, long memberId, LocalDate date);

    //테스트 완
    @Query(value = "select a from Attendance a " +
            "join GroupAttendence ga on a.attandanceId = ga.attendance.attandanceId " +
            "join MemberGroup mg on ga.group.groupId = mg.member.memberId " +
            "where a.date = :date and ga.group.groupId = :groupId and mg.member.memberId = :memberId")
    List<Attendance> findGroupCurAttendStatus(LocalDate date, long groupId, long memberId);

}
