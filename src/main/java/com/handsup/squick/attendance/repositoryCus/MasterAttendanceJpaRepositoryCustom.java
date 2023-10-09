package com.handsup.squick.attendance.repositoryCus;

import com.handsup.squick.attendance.entity.MasterAttendance;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MasterAttendanceJpaRepositoryCustom {
    List<MasterAttendance> findByGroupIdAndMemberId(long groupId, long memberId);
    Optional<MasterAttendance> findByGroupIdAndDate(long groupId, LocalDateTime date);
    Page<MasterAttendance> findByMasterGroupId(long groupId, Pageable pageable);
}
