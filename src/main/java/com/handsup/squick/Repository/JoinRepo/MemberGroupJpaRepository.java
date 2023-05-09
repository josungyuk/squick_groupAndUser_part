package com.handsup.squick.Repository.JoinRepo;

import com.handsup.squick.Entity.JoinEntity.MemberGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberGroupJpaRepository extends JpaRepository<MemberGroup, Long> {

    @Query(value = "select mg from MemberGroup mg " +
            "where mg.member.memberId = :memberId")
    MemberGroup findByMemberId(long memberId);
}
