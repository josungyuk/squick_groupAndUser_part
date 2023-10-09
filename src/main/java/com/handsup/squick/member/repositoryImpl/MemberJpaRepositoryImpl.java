package com.handsup.squick.member.repositoryImpl;

import com.handsup.squick.group.entity.QMemberGroup;
import com.handsup.squick.member.entity.Member;
import com.handsup.squick.member.entity.QMember;
import com.handsup.squick.member.repositoryCus.MemberJpaRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class MemberJpaRepositoryImpl implements MemberJpaRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    public Optional<Member> findByName(String name) {
        QMember member = QMember.member;

        return Optional.ofNullable(
                queryFactory.select(member)
                .from(member)
                .where(member.username.eq(name))
                .fetchOne()
        );
    }

    public Optional<Member> findById(Long id) {
        QMember member = QMember.member;

        return Optional.ofNullable(
                queryFactory.select(member)
                        .from(member)
                        .where(member.id.eq(id))
                        .fetchOne()
        );
    }

    public List<Member> findByGroupId(Long id){
        QMember member = QMember.member;
        QMemberGroup memberGroup = QMemberGroup.memberGroup;

        return queryFactory.select(member)
                        .from(member)
                        .innerJoin(member.memberGroups, memberGroup)
                        .where(memberGroup.group.id.eq(id))
                        .fetch();
    }

    public Optional<Member> findByNameAndGroupId(String name, Long groupId){
        QMember member = QMember.member;
        QMemberGroup memberGroup = QMemberGroup.memberGroup;

        return Optional.ofNullable(
                queryFactory.select(member)
                .from(member)
                .innerJoin(member.memberGroups, memberGroup)
                .where(member.username.eq(name), memberGroup.group.id.eq(groupId))
                .fetchOne()
        );
    }
}
