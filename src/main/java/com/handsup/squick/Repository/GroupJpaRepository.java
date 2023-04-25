package com.handsup.squick.Repository;

import com.handsup.squick.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupJpaRepository extends JpaRepository<Group, Long> {
    Group findByGroupId(long groupId);
    List<Group> findGroup();

    int findGroupMemberCnt(long groupId);

}
