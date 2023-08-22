package com.handsup.squick.member.repository;

import com.handsup.squick.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m " +
            "join MemberGroup mg on mg.member.memberId = m.memberId " +
            "join Group g on mg.group.groupId = g.groupId " +
            "where g.groupId = :groupId and m.memberName = :memberName")
    Member findMemberByGroupIdAndMemberName(long groupId, String memberName);

    //테스트 완
    Member findMemberByMemberName(String memberName);


    //테스트 완
    Member findMemberByMemberId(long memberId);

    //테스트 완
    @Query(value = "select m from Member m " +
            "join MemberGroup mg on m.memberId = mg.member.memberId " +
            "where mg.group.groupId = :groupId")
    List<Member> findMemberByGroupId(long groupId);

    //테스트 완
    @Query(value = "select m from Member m " +
            "join MemberGroup mg on mg.member.memberId = m.memberId " +
            "where m.memberName = :memberName and mg.group.groupId = :groupId")
    Member findMemberByMemberNameAndGroupId(String memberName, long groupId);

}
