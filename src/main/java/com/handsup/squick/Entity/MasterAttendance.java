package com.handsup.squick.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handsup.squick.Entity.JoinEntity.GroupAttendence;
import com.handsup.squick.Entity.JoinEntity.MasterSubAttendance;
import com.handsup.squick.Entity.JoinEntity.MemberAttendance;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "MasterAttendance")
public class MasterAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "masterAttendanceId")
    long masterAttandanceId;
    @Column(name = "day")
    int day;
    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    @Column(name = "time")
    @JsonFormat(pattern = "kk:mm:ss")
    LocalTime time;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    AttendanceStatus attendanceStatus;

    @Column
    boolean activation;

    @Column(name = "groupName")
    String groupName;

    @Column(name = "memberName")
    String memberName;

    @Column(name = "latitude")
    double latitude;

    @Column(name = "longitude")
    double longitude;

    @OneToMany(mappedBy = "masterAttendance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MemberAttendance> memberAttendances = new ArrayList<>();

    @OneToMany(mappedBy = "masterAttendance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<GroupAttendence> groupAttendences = new ArrayList<>();

    @OneToOne(mappedBy = "masterAttendance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    MasterSubAttendance masterSubAttendance;

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
