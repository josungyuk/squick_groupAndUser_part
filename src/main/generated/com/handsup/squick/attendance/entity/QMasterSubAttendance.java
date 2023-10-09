package com.handsup.squick.attendance.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMasterSubAttendance is a Querydsl query type for MasterSubAttendance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMasterSubAttendance extends EntityPathBase<MasterSubAttendance> {

    private static final long serialVersionUID = 886658121L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMasterSubAttendance masterSubAttendance = new QMasterSubAttendance("masterSubAttendance");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMasterAttendance masterAttendance;

    public final QSubAttendance subAttendance;

    public QMasterSubAttendance(String variable) {
        this(MasterSubAttendance.class, forVariable(variable), INITS);
    }

    public QMasterSubAttendance(Path<? extends MasterSubAttendance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMasterSubAttendance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMasterSubAttendance(PathMetadata metadata, PathInits inits) {
        this(MasterSubAttendance.class, metadata, inits);
    }

    public QMasterSubAttendance(Class<? extends MasterSubAttendance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.masterAttendance = inits.isInitialized("masterAttendance") ? new QMasterAttendance(forProperty("masterAttendance"), inits.get("masterAttendance")) : null;
        this.subAttendance = inits.isInitialized("subAttendance") ? new QSubAttendance(forProperty("subAttendance")) : null;
    }

}

