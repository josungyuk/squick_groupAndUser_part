package com.handsup.squick.attendance.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMasterAttendance is a Querydsl query type for MasterAttendance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMasterAttendance extends EntityPathBase<MasterAttendance> {

    private static final long serialVersionUID = 966433769L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMasterAttendance masterAttendance = new QMasterAttendance("masterAttendance");

    public final com.handsup.squick.config.QBaseTimeEntity _super = new com.handsup.squick.config.QBaseTimeEntity(this);

    public final BooleanPath activation = createBoolean("activation");

    public final EnumPath<MasterAttendance.AttendanceStatus> attendanceStatus = createEnum("attendanceStatus", MasterAttendance.AttendanceStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final NumberPath<Integer> day = createNumber("day", Integer.class);

    public final ListPath<com.handsup.squick.group.entity.GroupAttendance, com.handsup.squick.group.entity.QGroupAttendance> groupAttendances = this.<com.handsup.squick.group.entity.GroupAttendance, com.handsup.squick.group.entity.QGroupAttendance>createList("groupAttendances", com.handsup.squick.group.entity.GroupAttendance.class, com.handsup.squick.group.entity.QGroupAttendance.class, PathInits.DIRECT2);

    public final NumberPath<Long> groupId = createNumber("groupId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final QMasterSubAttendance masterSubAttendance;

    public final ListPath<MemberAttendance, QMemberAttendance> memberAttendances = this.<MemberAttendance, QMemberAttendance>createList("memberAttendances", MemberAttendance.class, QMemberAttendance.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modified = _super.modified;

    public QMasterAttendance(String variable) {
        this(MasterAttendance.class, forVariable(variable), INITS);
    }

    public QMasterAttendance(Path<? extends MasterAttendance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMasterAttendance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMasterAttendance(PathMetadata metadata, PathInits inits) {
        this(MasterAttendance.class, metadata, inits);
    }

    public QMasterAttendance(Class<? extends MasterAttendance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.masterSubAttendance = inits.isInitialized("masterSubAttendance") ? new QMasterSubAttendance(forProperty("masterSubAttendance"), inits.get("masterSubAttendance")) : null;
    }

}

