package com.handsup.squick.attendance.repository;

import com.handsup.squick.attendance.entity.MasterAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface MasterAttendanceJpaRepository extends JpaRepository<MasterAttendance, Long> {
    MasterAttendance findMasterAttendanceByMasterAttandanceId(long attendanceId);
    @Query("select ma from MasterAttendance ma " +
            "join GroupAttendance ga on ga.masterAttendance.masterAttandanceId = ma.masterAttandanceId " +
            "join MemberGroup mg on mg.group.groupId = ga.group.groupId " +
            "where mg.group.groupId = :groupId and mg.member.memberId = :memberId")
    List<MasterAttendance> findMasterAttendanceByGroupIdAndMemberId(long groupId, long memberId);

    @Query("select ma from MasterAttendance ma " +
            "where ma.groupId = :groupId and ma.date = :date")
    MasterAttendance findMasterAttendanceByGroupIdAndDate(long groupId, LocalDate date);

    @Query("select ma from MasterAttendance ma " +
            "join GroupAttendance ga on ga.masterAttendance.masterAttandanceId = ma.masterAttandanceId " +
            "where ga.group.groupId = :groupId " +
            "order by ma.date desc")
    Page<MasterAttendance> findRecentMasterAttendanceByMasterGroupId(long groupId, Pageable pageable);

}