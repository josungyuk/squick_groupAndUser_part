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
    @Query(value = "select a from Member m join MemberGroupAttendance mga on mga.member.memberId = m.memberId " +
            "join Attendance a on mga.attendance.attandanceId = a.attandanceId " +
            "where mga.group.groupId = :groupId")
    List<Attendance> findMemberDetail(long groupId, long memberId);

    @Query(value = "select a from Member m " +
            "join MemberGroupAttendance mga on mga.member.memberId = m.memberId " +
            "join Attendance a on a.attandanceId = mga.attendance.attandanceId " +
            "where a.time = :date and mga.group.groupId = :groupId and m.memberId = :memberId")
    List<Attendance> findMemberAttendance(long groupId, long memberId, LocalDate date);

    List<Member> deleteByUserIdAndGroupName(String userName, String groupName);

    List<Member> findMemberDaoByGroupId(long groupId);
}
