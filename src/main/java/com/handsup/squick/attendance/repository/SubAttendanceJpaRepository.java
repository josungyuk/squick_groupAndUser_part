package com.handsup.squick.attendance.repository;

import com.handsup.squick.attendance.entity.SubAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubAttendanceJpaRepository extends JpaRepository<SubAttendance, Long> {

//    SubAttendance findById(long attendanceId);
//    //테스트 완
//    @Query(value = "select sa from SubAttendance sa " +
//            "where sa.groupId = :groupId and sa.memberId = :memberId and " +
//            "sa.date = :date")
//    Optional<SubAttendance> findSubAttandanceByGroupIdAndMemberIdAndDate(long groupId, long memberId, LocalDate date);
//
//    //테스트 완
//    @Query(value = "select sa from SubAttendance sa " +
//            "join MasterSubAttendance msa on sa.subAttandanceId = msa.subAttendance.subAttandanceId " +
//            "join GroupAttendance ga on ga.masterAttendance.masterAttandanceId = msa.masterAttendance.masterAttandanceId " +
//            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
//            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId")
//    List<SubAttendance> findAllAttendanceByGroupIdAndMemberId(long groupId, long memberId);
//
//    //테스트 완
//    @Query(value = "select sa from SubAttendance sa " +
//            "join MasterSubAttendance msa on sa.subAttandanceId = msa.subAttendance.subAttandanceId " +
//            "join GroupAttendance ga on msa.masterAttendance.masterAttandanceId = ga.masterAttendance.masterAttandanceId " +
//            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
//            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId and function('YEAR', sa.date) = :year and function('MONTH', sa.date) = :month")
//    List<SubAttendance> findMonthAttendanceByGroupIdAndMemberIdAndDate(long groupId, long memberId, int year, int month);
//
//    //테스트 완
//    @Query(value = "select sa from SubAttendance sa " +
//            "join MasterSubAttendance msa on sa.subAttandanceId = msa.subAttendance.subAttandanceId " +
//            "join GroupAttendance ga on msa.masterAttendance.masterAttandanceId = ga.masterAttendance.masterAttandanceId " +
//            "join MemberGroup mg on ga.group.groupId = mg.member.memberId " +
//            "where sa.date = :date and ga.group.groupId = :groupId and mg.member.memberId = :memberId")
//    List<SubAttendance> findGroupCurAttendStatus(LocalDate date, long groupId, long memberId);

}
