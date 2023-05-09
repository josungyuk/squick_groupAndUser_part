package com.handsup.squick.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handsup.squick.Entity.JoinEntity.GroupAttendence;
import com.handsup.squick.Entity.JoinEntity.MemberAttendance;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "Attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendanceId")
    long attandanceId;
    @Column(name = "day")
    int day;
    @Column(name = "time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    AttendanceStatus attendanceStatus;

    @Column(name = "groupName")
    String groupName;

    @Column(name = "memberName")
    String memberName;

    @OneToMany(mappedBy = "attendance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MemberAttendance> memberAttendances = new ArrayList<>();

    @OneToMany(mappedBy = "attendance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<GroupAttendence> groupAttendences = new ArrayList<>();

    public enum AttendanceStatus{
        STATUS_ATTEND("참석"),
        STATUS_ABSENT("결석"),
        STATUS_LATE("지각");

        private String status;

        AttendanceStatus(String status){
            this.status = status;
        }
    }
}
