package com.handsup.squick.attendance.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSubAttendance is a Querydsl query type for SubAttendance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubAttendance extends EntityPathBase<SubAttendance> {

    private static final long serialVersionUID = 403126539L;

    public static final QSubAttendance subAttendance = new QSubAttendance("subAttendance");

    public final com.handsup.squick.config.QBaseTimeEntity _super = new com.handsup.squick.config.QBaseTimeEntity(this);

    public final EnumPath<SubAttendance.AttendanceStatus> attendanceStatus = createEnum("attendanceStatus", SubAttendance.AttendanceStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final NumberPath<Integer> day = createNumber("day", Integer.class);

    public final NumberPath<Long> groupId = createNumber("groupId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> latitude = createNumber("latitude", Double.class);

    public final NumberPath<Double> longitude = createNumber("longitude", Double.class);

    public final ListPath<MasterSubAttendance, QMasterSubAttendance> masterSubAttendances = this.<MasterSubAttendance, QMasterSubAttendance>createList("masterSubAttendances", MasterSubAttendance.class, QMasterSubAttendance.class, PathInits.DIRECT2);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modified = _super.modified;

    public QSubAttendance(String variable) {
        super(SubAttendance.class, forVariable(variable));
    }

    public QSubAttendance(Path<? extends SubAttendance> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubAttendance(PathMetadata metadata) {
        super(SubAttendance.class, metadata);
    }

}

