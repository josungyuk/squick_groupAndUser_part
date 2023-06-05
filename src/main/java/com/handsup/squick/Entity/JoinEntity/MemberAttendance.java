package com.handsup.squick.Entity.JoinEntity;

import com.handsup.squick.Entity.MasterAttendance;
import com.handsup.squick.Entity.SubAttendance;
import com.handsup.squick.Entity.Member;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Member_Attendance")
public class MemberAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masterAttandanceId")
    MasterAttendance masterAttendance;
}
