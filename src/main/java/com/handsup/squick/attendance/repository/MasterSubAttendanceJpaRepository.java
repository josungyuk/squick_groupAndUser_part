package com.handsup.squick.attendance.repository;

import com.handsup.squick.attendance.entity.MasterSubAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterSubAttendanceJpaRepository extends JpaRepository<MasterSubAttendance, Long> {
}
