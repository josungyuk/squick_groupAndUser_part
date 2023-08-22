package com.handsup.squick.group.entity;

import com.handsup.squick.attendance.entity.MasterAttendance;
import com.handsup.squick.group.entity.Group;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Group_Attendance")
public class GroupAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterAttandanceId")
    MasterAttendance masterAttendance;
}
