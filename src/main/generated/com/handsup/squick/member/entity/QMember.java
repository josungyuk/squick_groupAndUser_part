package com.handsup.squick.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 1281527561L;

    public static final QMember member = new QMember("member1");

    public final com.handsup.squick.config.QBaseTimeEntity _super = new com.handsup.squick.config.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> created = _super.created;

    public final StringPath email = createString("email");

    public final StringPath groupName = createString("groupName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath img = createString("img");

    public final EnumPath<Member.InvitationStatus> invitationStatus = createEnum("invitationStatus", Member.InvitationStatus.class);

    public final BooleanPath isPin = createBoolean("isPin");

    public final ListPath<com.handsup.squick.attendance.entity.MemberAttendance, com.handsup.squick.attendance.entity.QMemberAttendance> memberAttendances = this.<com.handsup.squick.attendance.entity.MemberAttendance, com.handsup.squick.attendance.entity.QMemberAttendance>createList("memberAttendances", com.handsup.squick.attendance.entity.MemberAttendance.class, com.handsup.squick.attendance.entity.QMemberAttendance.class, PathInits.DIRECT2);

    public final ListPath<com.handsup.squick.group.entity.MemberGroup, com.handsup.squick.group.entity.QMemberGroup> memberGroups = this.<com.handsup.squick.group.entity.MemberGroup, com.handsup.squick.group.entity.QMemberGroup>createList("memberGroups", com.handsup.squick.group.entity.MemberGroup.class, com.handsup.squick.group.entity.QMemberGroup.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modified = _super.modified;

    public final StringPath username = createString("username");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

