package com.handsup.squick.Entity;

import com.handsup.squick.Entity.JoinEntity.GroupAttendence;
import com.handsup.squick.Entity.JoinEntity.MemberGroup;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "`Group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long groupId;
    @Column(name = "groupName")
    String groupName;
    @Column(name = "invitationCode")
    String invitationCode;
    @Column(name = "description")
    String description;
    @Column(name = "isMaster")
    boolean isMaster;
    @Column(name = "isPin")
    boolean isPin;
    @Column(name = "isAlarm")
    boolean isAlarm;
    @Column(name = "img", length = 65536)
    String img;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<GroupAttendence> groupAttendances = new ArrayList<>();


    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MemberGroup> memberGroups = new ArrayList<>();
}
