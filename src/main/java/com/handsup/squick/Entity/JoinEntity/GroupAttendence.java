package com.handsup.squick.Entity.JoinEntity;

import com.handsup.squick.Entity.MasterAttendance;
import com.handsup.squick.Entity.SubAttendance;
import com.handsup.squick.Entity.Group;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Group_Attendance")
public class GroupAttendence {
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
