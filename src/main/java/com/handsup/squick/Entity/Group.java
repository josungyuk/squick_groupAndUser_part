package com.handsup.squick.Entity;

import com.handsup.squick.Entity.JoinEntity.GroupAttendance;
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
    @Column(name = "groupId")
    long groupId;
    @Column(name = "groupName")
    String groupName;
    @Column(name = "masterName")
    String masterName;
    @Column(name = "img", length = 65536)
    String img;
    @Column(name = "description")
    String description;
    @Column(name = "invitationCode")
    String invitationCode;
    @Column(name = "isPin")
    boolean isPin;
    @Column(name = "isAlarm")
    boolean isAlarm;


    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<GroupAttendance> groupAttendances = new ArrayList<>();


    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MemberGroup> memberGroups = new ArrayList<>();
}
