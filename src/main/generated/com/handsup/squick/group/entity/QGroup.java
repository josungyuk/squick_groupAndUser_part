package com.handsup.squick.group.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGroup is a Querydsl query type for Group
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroup extends EntityPathBase<Group> {

    private static final long serialVersionUID = -1735293477L;

    public static final QGroup group = new QGroup("group1");

    public final com.handsup.squick.config.QBaseTimeEntity _super = new com.handsup.squick.config.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final StringPath description = createString("description");

    public final ListPath<GroupAttendance, QGroupAttendance> groupAttendances = this.<GroupAttendance, QGroupAttendance>createList("groupAttendances", GroupAttendance.class, QGroupAttendance.class, PathInits.DIRECT2);

    public final StringPath groupName = createString("groupName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img = createString("img");

    public final StringPath invitationCode = createString("invitationCode");

    public final BooleanPath isAlarm = createBoolean("isAlarm");

    public final BooleanPath isPin = createBoolean("isPin");

    public final StringPath masterName = createString("masterName");

    public final ListPath<MemberGroup, QMemberGroup> memberGroups = this.<MemberGroup, QMemberGroup>createList("memberGroups", MemberGroup.class, QMemberGroup.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modified = _super.modified;

    public QGroup(String variable) {
        super(Group.class, forVariable(variable));
    }

    public QGroup(Path<? extends Group> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGroup(PathMetadata metadata) {
        super(Group.class, metadata);
    }

}

