package com.handsup.squick.Entity;

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
@Table(name = "GroupTable")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long groupId;
    @Column(name = "name")
    String name;
    @Column(name = "groupExplain")
    String description;
    @Column(name = "hostId")
    boolean isMaster;
    @Column(name = "isPin")
    boolean isPin;
    @Column(name = "isAlarm")
    boolean isAlarm;
    @Column(name = "limitPerson")
    String invitationCode;
    @Column(name = "img")
    String img;

    @OneToMany(mappedBy = "group")
    List<MemberGroupAttendance> memberGroupAttendances = new ArrayList<>();
}
