package com.handsup.squick.Repository;

import com.handsup.squick.Entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupJpaRepository extends JpaRepository<Group, Long> {

    @Query("select g from Group g " +
            "where g.groupName = :groupName")
    Group findGroupByGroupName(String groupName);

    //테스트 완
    Group findByGroupId(long groupId);

    //테스트 완
    @Query(value = "select g from Group g")
    List<Group> findGroup();

    //테스트 완
    @Query(value = "select m.memberId from Member m " +
            "join MemberGroup mg on m.memberId = mg.member.memberId " +
            "where mg.group.groupId = :groupId")
    List<Long> findMemberIdByGroupId(long groupId);
}
