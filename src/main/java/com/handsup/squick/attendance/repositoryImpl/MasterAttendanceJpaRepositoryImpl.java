package com.handsup.squick.attendance.repositoryImpl;

import com.handsup.squick.attendance.entity.MasterAttendance;
import com.handsup.squick.attendance.entity.QMasterAttendance;
import com.handsup.squick.attendance.repositoryCus.MasterAttendanceJpaRepositoryCustom;
import com.handsup.squick.group.entity.QGroup;
import com.handsup.squick.group.entity.QGroupAttendance;
import com.handsup.squick.group.entity.QMemberGroup;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MasterAttendanceJpaRepositoryImpl implements MasterAttendanceJpaRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<MasterAttendance> findByGroupIdAndMemberId(long groupId, long memberId){
        QMasterAttendance masterAttendance = QMasterAttendance.masterAttendance;
        QMemberGroup memberGroup = QMemberGroup.memberGroup;
        QGroupAttendance groupAttendance = QGroupAttendance.groupAttendance;

        return queryFactory.selectFrom(masterAttendance)
                .innerJoin(groupAttendance)
                .on(groupAttendance.masterAttendance.id.eq(masterAttendance.id))

                .innerJoin(memberGroup)
                .on(memberGroup.group.id.eq(groupAttendance.group.id))

                .where(memberGroup.group.id.eq(groupId), memberGroup.member.id.eq(memberId))
                .fetch();
    }

    public Optional<MasterAttendance> findByGroupIdAndDate(long groupId, LocalDateTime date){
        QMasterAttendance masterAttendance = QMasterAttendance.masterAttendance;

        return Optional.ofNullable(
                queryFactory.selectFrom(masterAttendance)
                .where(masterAttendance.groupId.eq(groupId), masterAttendance.created.eq(date))
                .fetchOne()
        );
    }

    public Page<MasterAttendance> findByMasterGroupId(long groupId, Pageable pageable){
        QMasterAttendance masterAttendance = QMasterAttendance.masterAttendance;
        QGroupAttendance groupAttendance = QGroupAttendance.groupAttendance;

        QueryResults<MasterAttendance> results =  queryFactory.selectFrom(masterAttendance)
                .innerJoin(groupAttendance)
                .on(groupAttendance.masterAttendance.id.eq(masterAttendance.id))
                .where(groupAttendance.group.id.eq(groupId))
                .offset(1)
                .limit(0)
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
