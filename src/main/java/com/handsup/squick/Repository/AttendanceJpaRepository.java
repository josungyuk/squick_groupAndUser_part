package com.handsup.squick.Repository;

import com.handsup.squick.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceJpaRepository extends JpaRepository<Attendance, Long> {
    Attendance findByAttandanceId(long attendanceId);

}
