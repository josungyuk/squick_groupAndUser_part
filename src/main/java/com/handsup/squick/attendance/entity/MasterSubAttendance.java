package com.handsup.squick.attendance.entity;

import com.handsup.squick.attendance.entity.MasterAttendance;
import com.handsup.squick.attendance.entity.SubAttendance;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Master_SubAttendance")
public class MasterSubAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterAttandanceId")
    MasterAttendance masterAttendance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subAttendanceId")
    SubAttendance subAttendance;
}
