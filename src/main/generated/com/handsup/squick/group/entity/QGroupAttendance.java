package com.handsup.squick.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroupAttendance is a Querydsl query type for GroupAttendance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupAttendance extends EntityPathBase<GroupAttendance> {

    private static final long serialVersionUID = -272765148L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupAttendance groupAttendance = new QGroupAttendance("groupAttendance");

    public final QGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.handsup.squick.attendance.entity.QMasterAttendance masterAttendance;

    public QGroupAttendance(String variable) {
        this(GroupAttendance.class, forVariable(variable), INITS);
    }

    public QGroupAttendance(Path<? extends GroupAttendance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupAttendance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupAttendance(PathMetadata metadata, PathInits inits) {
        this(GroupAttendance.class, metadata, inits);
    }

    public QGroupAttendance(Class<? extends GroupAttendance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group")) : null;
        this.masterAttendance = inits.isInitialized("masterAttendance") ? new com.handsup.squick.attendance.entity.QMasterAttendance(forProperty("masterAttendance"), inits.get("masterAttendance")) : null;
    }

}

