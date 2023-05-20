package com.handsup.squick.Repository;

import com.handsup.squick.Entity.MasterAttendance;
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
}
