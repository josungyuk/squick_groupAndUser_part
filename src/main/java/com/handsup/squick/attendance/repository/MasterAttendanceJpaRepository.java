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
}
