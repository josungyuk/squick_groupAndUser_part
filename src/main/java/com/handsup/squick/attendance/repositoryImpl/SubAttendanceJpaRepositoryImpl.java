package com.handsup.squick.attendance.repositoryImpl;

import com.handsup.squick.attendance.entity.QMasterAttendance;
import com.handsup.squick.attendance.entity.QMasterSubAttendance;
import com.handsup.squick.attendance.entity.QSubAttendance;
import com.handsup.squick.attendance.entity.SubAttendance;
import com.handsup.squick.attendance.repositoryCus.SubAttendanceJpaRepositoryCustom;
import com.handsup.squick.group.entity.QGroupAttendance;
import com.handsup.squick.group.entity.QMemberGroup;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.beans.Expression;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SubAttendanceJpaRepositoryImpl implements SubAttendanceJpaRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Optional<SubAttendance> findByGroupIdAndMemberIdAndDate(long groupId, long memberId, LocalDateTime date){
        QSubAttendance subAttendance = QSubAttendance.subAttendance;

        return Optional.ofNullable(
            queryFactory.selectFrom(subAttendance)
                    .where(subAttendance.groupId.eq(groupId),
                            subAttendance.memberId.eq(memberId),
                            subAttendance.created.eq(date))
                    .fetchOne()
        );
    }

    public List<SubAttendance> findByGroupIdAndMemberId(long groupId, long memberId){
        QSubAttendance subAttendance = QSubAttendance.subAttendance;
        QMasterSubAttendance masterSubAttendance = QMasterSubAttendance.masterSubAttendance;
        QGroupAttendance groupAttendance = QGroupAttendance.groupAttendance;
        QMemberGroup memberGroup = QMemberGroup.memberGroup;

        return queryFactory.selectFrom(subAttendance)

                .innerJoin(masterSubAttendance)
                .on(subAttendance.id.eq(masterSubAttendance.subAttendance.id))

                .innerJoin(groupAttendance)
                .on(groupAttendance.masterAttendance.id.eq(masterSubAttendance.masterAttendance.id))

                .innerJoin(memberGroup)
                .on(memberGroup.group.id.eq(groupAttendance.group.id))

                .where(memberGroup.group.id.eq(groupId), memberGroup.member.id.eq(memberId))
                .fetch();
    }

    public List<SubAttendance> findMonthByGroupIdAndMemberIdAndDate(long groupId, long memberId, int year, int month){
        QSubAttendance subAttendance = QSubAttendance.subAttendance;
        QMasterSubAttendance masterSubAttendance = QMasterSubAttendance.masterSubAttendance;
        QGroupAttendance groupAttendance = QGroupAttendance.groupAttendance;
        QMemberGroup memberGroup = QMemberGroup.memberGroup;

        return queryFactory.selectFrom(subAttendance)

                .innerJoin(masterSubAttendance)
                .on(subAttendance.id.eq(masterSubAttendance.subAttendance.id))

                .innerJoin(groupAttendance)
                .on(groupAttendance.masterAttendance.id.eq(masterSubAttendance.masterAttendance.id))

                .innerJoin(memberGroup)
                .on(memberGroup.group.id.eq(groupAttendance.group.id))

                .where(
                        memberGroup.group.id.eq(groupId),
                        memberGroup.member.id.eq(memberId),
                        Expressions.dateTemplate(Integer.class, "{year(date)}", subAttendance.created).eq(year),
                        Expressions.dateTemplate(Integer.class, "{month(date)}", subAttendance.created).eq(month)
                )
                .fetch();
    }

    public List<SubAttendance> findGroupAttendStatus(LocalDateTime date, long groupId, long memberId){
        QSubAttendance subAttendance = QSubAttendance.subAttendance;
        QMasterSubAttendance masterSubAttendance = QMasterSubAttendance.masterSubAttendance;
        QGroupAttendance groupAttendance = QGroupAttendance.groupAttendance;
        QMemberGroup memberGroup = QMemberGroup.memberGroup;

        return queryFactory.selectFrom(subAttendance)

                .innerJoin(masterSubAttendance)
                .on(subAttendance.id.eq(masterSubAttendance.subAttendance.id))

                .innerJoin(groupAttendance)
                .on(groupAttendance.masterAttendance.id.eq(masterSubAttendance.masterAttendance.id))

                .innerJoin(memberGroup)
                .on(memberGroup.group.id.eq(groupAttendance.group.id))

                .where(
                        subAttendance.created.eq(date),
                        groupAttendance.group.id.eq(groupId),
                        memberGroup.member.id.eq(memberId)
                )
                .fetch();
    }
}
