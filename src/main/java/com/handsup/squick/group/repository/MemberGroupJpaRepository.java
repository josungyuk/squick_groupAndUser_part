package com.handsup.squick.group.repository;

import com.handsup.squick.group.entity.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberGroupJpaRepository extends JpaRepository<MemberGroup, Long> {

    @Query(value = "select mg from MemberGroup mg " +
            "where mg.member.memberId = :memberId")
    MemberGroup findByMemberId(long memberId);
}
