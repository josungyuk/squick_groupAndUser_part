package com.handsup.squick.group.repositoryImpl;

import com.handsup.squick.group.entity.Group;
import com.handsup.squick.group.entity.QGroup;
import com.handsup.squick.group.repositoryCus.GroupJpaRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GroupJpaRepositoryImpl implements GroupJpaRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Optional<Group> findByName(String name){
        QGroup group = QGroup.group;

        return Optional.ofNullable(
            queryFactory.select(group)
                    .from(group)
                    .where(group.groupName.eq(name))
                    .fetchOne()
        );
    }

    public Optional<Group> findById(Long id){
        QGroup group = QGroup.group;

        return Optional.ofNullable(
                queryFactory.select(group)
                        .from(group)
                        .where(group.id.eq(id))
                        .fetchOne()
        );
    }

    public List<Group> findGroup(){
        QGroup group = QGroup.group;

        return queryFactory.select(group)
                .from(group)
                .fetch();
    }
}
