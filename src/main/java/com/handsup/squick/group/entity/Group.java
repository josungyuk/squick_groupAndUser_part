package com.handsup.squick.group.entity;

import com.handsup.squick.config.BaseTimeEntity;
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
public class Group extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupId")
    Long id;
    @Column(name = "groupName")
    String groupName;
    @Column(name = "masterName")
    String masterName;
    @Column(columnDefinition = "TEXT")
    String img;
    @Column(name = "description")
    String description;
    @Column(name = "invitationCode")
    String invitationCode;
    @Column(name = "isPin")
    Boolean isPin;
    @Column(name = "isAlarm")
    Boolean isAlarm;


    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<GroupAttendance> groupAttendances = new ArrayList<>();


    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MemberGroup> memberGroups = new ArrayList<>();
}
