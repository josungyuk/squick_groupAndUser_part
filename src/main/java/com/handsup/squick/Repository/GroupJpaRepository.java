package com.handsup.squick.Repository;

import com.handsup.squick.Dao.GroupDao;
import com.handsup.squick.Dto.GroupDto.GroupCreateDto;
import com.handsup.squick.Dto.GroupDto.GroupDeleteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GroupJpaRepository extends JpaRepository<GroupDao, Long> {
    List<GroupDao> findDistinctByGroupName(String groupName);
    List<GroupDao> findGroupDaoByGroupName(String groupName);
}
