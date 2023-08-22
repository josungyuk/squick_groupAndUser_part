package com.handsup.squick.attendance.repository;

import com.handsup.squick.attendance.entity.MemberAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberAttendanceJpaRepository extends JpaRepository<MemberAttendance, Long> {
}
