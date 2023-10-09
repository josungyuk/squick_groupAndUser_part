package com.handsup.squick.attendance.repositoryCus;

import com.handsup.squick.attendance.entity.SubAttendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SubAttendanceJpaRepositoryCustom {

    Optional<SubAttendance> findByGroupIdAndMemberIdAndDate(long groupId, long memberId, LocalDateTime date);
    List<SubAttendance> findByGroupIdAndMemberId(long groupId, long memberId);
    List<SubAttendance> findMonthByGroupIdAndMemberIdAndDate(long groupId, long memberId, int year, int month);
    List<SubAttendance> findGroupAttendStatus(LocalDateTime date, long groupId, long memberId);

}
