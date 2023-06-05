package com.handsup.squick.Entity.JoinEntity;

import com.handsup.squick.Entity.MasterAttendance;
import com.handsup.squick.Entity.SubAttendance;
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
