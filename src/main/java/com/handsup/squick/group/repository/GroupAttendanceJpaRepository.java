package com.handsup.squick.group.repository;

import com.handsup.squick.group.entity.GroupAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAttendanceJpaRepository extends JpaRepository<GroupAttendance, Long> {
}
