package com.handsup.squick.Repository;

import com.handsup.squick.Entity.SubAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubAttendanceJpaRepository extends JpaRepository<SubAttendance, Long> {

    SubAttendance findById(long attendanceId);
    //테스트 완
    @Query(value = "select sa from SubAttendance sa " +
            "join MasterSubAttendance msa on sa.subAttandanceId = msa.subAttendance.subAttandanceId " +
            "join GroupAttendence ga on msa.masterAttendance.masterAttandanceId = ga.masterAttendance.masterAttandanceId " +
            "where ga.group.groupName = :groupName and msa.masterAttendance.memberName = :memberName and " +
            "sa.date = :date")
    SubAttendance findAttandanceByGroupNameAndMemberNameAndDate(String groupName, String memberName, LocalDate date);

    //테스트 완
    @Query(value = "select sa from SubAttendance sa " +
            "join MasterSubAttendance msa on sa.subAttandanceId = msa.subAttendance.subAttandanceId " +
            "join GroupAttendence ga on ga.masterAttendance.masterAttandanceId = msa.masterAttendance.masterAttandanceId " +
            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId")
    List<SubAttendance> findAllAttendanceByGroupIdAndMemberId(long groupId, long memberId);

    //테스트 완
    @Query(value = "select sa from SubAttendance sa " +
            "join MasterSubAttendance msa on sa.subAttandanceId = msa.subAttendance.subAttandanceId " +
            "join GroupAttendence ga on msa.masterAttendance.masterAttandanceId = ga.masterAttendance.masterAttandanceId " +
            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId and function('YEAR', sa.date) = :year and function('MONTH', sa.date) = :month")
    List<SubAttendance> findMonthAttendanceByGroupIdAndMemberIdAndDate(long groupId, long memberId, int year, int month);

    //테스트 완
    @Query(value = "select sa from SubAttendance sa " +
            "join MasterSubAttendance msa on sa.subAttandanceId = msa.subAttendance.subAttandanceId " +
            "join GroupAttendence ga on msa.masterAttendance.masterAttandanceId = ga.masterAttendance.masterAttandanceId " +
            "join MemberGroup mg on ga.group.groupId = mg.member.memberId " +
            "where sa.date = :date and ga.group.groupId = :groupId and mg.member.memberId = :memberId")
    List<SubAttendance> findGroupCurAttendStatus(LocalDate date, long groupId, long memberId);

}
