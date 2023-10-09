package com.handsup.squick.attendance.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberAttendance is a Querydsl query type for MemberAttendance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberAttendance extends EntityPathBase<MemberAttendance> {

    private static final long serialVersionUID = -1868622303L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberAttendance memberAttendance = new QMemberAttendance("memberAttendance");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMasterAttendance masterAttendance;

    public final com.handsup.squick.member.entity.QMember member;

    public QMemberAttendance(String variable) {
        this(MemberAttendance.class, forVariable(variable), INITS);
    }

    public QMemberAttendance(Path<? extends MemberAttendance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberAttendance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberAttendance(PathMetadata metadata, PathInits inits) {
        this(MemberAttendance.class, metadata, inits);
    }

    public QMemberAttendance(Class<? extends MemberAttendance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.masterAttendance = inits.isInitialized("masterAttendance") ? new QMasterAttendance(forProperty("masterAttendance"), inits.get("masterAttendance")) : null;
        this.member = inits.isInitialized("member") ? new com.handsup.squick.member.entity.QMember(forProperty("member")) : null;
    }

}

