package com.handsup.squick.Repository;

import com.handsup.squick.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupJpaRepository extends JpaRepository<Group, Long> {
    Group findByGroupId(long groupId);
    @Query(value = "select g from Group g")
    List<Group> findGroup();

    @Query(value = "select m.memberId from Member m " +
            "join MemberGroup mg on m.memberId = mg.member.memberId " +
            "where mg.group.groupId = :groupId")
    List<Long> findMemberByGroupId(long groupId);
}
