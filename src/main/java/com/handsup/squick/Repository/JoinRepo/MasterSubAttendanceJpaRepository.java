package com.handsup.squick.Repository.JoinRepo;

import com.handsup.squick.Entity.JoinEntity.MasterSubAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterSubAttendanceJpaRepository extends JpaRepository<MasterSubAttendance, Long> {
}
