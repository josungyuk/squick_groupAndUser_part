package com.handsup.squick.Repository;

import com.handsup.squick.Entity.SubAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubAttendanceJpaRepository extends JpaRepository<SubAttendance, Long> {
    //테스트 완
    @Query(value = "select a from SubAttendance a " +
            "join GroupAttendence ga on a.attandanceId = ga.subAttendance.attandanceId " +
            "join MemberAttendance ma on a.attandanceId = ma.subAttendance.attandanceId " +
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
