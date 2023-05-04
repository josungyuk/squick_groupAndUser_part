package com.handsup.squick.Repository;

import com.handsup.squick.Entity.Attendance;
import com.handsup.squick.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Member findMemberByMemberName(String memberName);
    @Query(value = "select m from Member m " +
            "join MemberGroup mg on m.memberId = mg.member.memberId " +
            "where mg.group.groupId = :groupId")
    List<Member> findMemberByGroupId(long groupId);

    Member findMemberByMemberId(long memberId);

}
