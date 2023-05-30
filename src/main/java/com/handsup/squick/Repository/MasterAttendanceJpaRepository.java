package com.handsup.squick.Repository;

import com.handsup.squick.Entity.MasterAttendance;
import com.handsup.squick.Entity.SubAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MasterAttendanceJpaRepository extends JpaRepository<MasterAttendance, Long> {
    @Query("select ma from MasterAttendance ma " +
            "join GroupAttendence ga on ga.masterAttendance.masterAttandanceId = ma.masterAttandanceId " +
            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId and ma.date = :date")
    List<MasterAttendance> findMasterAttendanceByGroupIdAndMemberId(long groupId, long memberId);

    @Query("select ma from MasterAttendance ma " +
            "where ma.groupName = :groupName and ma.date = :date")
    MasterAttendance findMasterAttendanceByGroupNameAndDate(String groupName, LocalDate date);

    @Query("select MAX(ma.date) from MasterAttendance ma " +
            "where ma.masterAttandanceId = :attendanceId")
    MasterAttendance findRecentMasterAttendanceByMasterAttandanceId(long attendanceId);

    //테스트 완
    @Query(value = "select a from SubAttendance a " +
            "join GroupAttendence ga on a.subAttandanceId = ga.subAttandanceId.attandanceId " +
            "join MemberAttendance ma on a.subAttandanceId = ma.subAttandanceId.attandanceId " +
            "where ga.group.groupName = :groupName and ma.member.memberName = :memberName and " +
            "a.date = :date")
    SubAttendance findAttandanceByGroupNameAndMemberNameAndDate(String groupName, String memberName, LocalDate date);

    //테스트 완
    @Query(value = "select a from SubAttendance a " +
            "join GroupAttendence ga on ga.subAttendance.attandanceId = a.attandanceId " +
            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId")
    List<SubAttendance> findAllAttendanceByGroupIdAndMemberId(long groupId, long memberId);

    //테스트 완
    @Query(value = "select a from SubAttendance a " +
            "join GroupAttendence ga on a.attandanceId = ga.subAttendance.attandanceId " +
            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId and function('YEAR', a.date) = :year and function('MONTH', a.date) = :month")
    List<SubAttendance> findMonthAttendanceByGroupIdAndMemberIdAndDate(long groupId, long memberId, int year, int month);

    //테스트 완
    @Query(value = "select a from SubAttendance a " +
            "join GroupAttendence ga on a.attandanceId = ga.subAttendance.attandanceId " +
            "join MemberGroup mg on ga.group.groupId = mg.member.memberId " +
            "where a.date = :date and ga.group.groupId = :groupId and mg.member.memberId = :memberId")
    List<SubAttendance> findGroupCurAttendStatus(LocalDate date, long groupId, long memberId);
}
