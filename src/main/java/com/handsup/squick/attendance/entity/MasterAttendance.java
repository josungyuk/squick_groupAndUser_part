package com.handsup.squick.attendance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handsup.squick.config.BaseTimeEntity;
import com.handsup.squick.group.entity.GroupAttendance;
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
public class MasterAttendance extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    @Column(name = "day")
    int day;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    AttendanceStatus attendanceStatus;

    @Column
    boolean activation;

    @Column(name = "groupId")
    long groupId;

    @Column(name = "memberId")
    long memberId;


    @Column(name = "latitude")
    double latitude;

    @Column(name = "longitude")
    double longitude;

    @OneToMany(mappedBy = "masterAttendance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MemberAttendance> memberAttendances = new ArrayList<>();

    @OneToMany(mappedBy = "masterAttendance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<GroupAttendance> groupAttendances = new ArrayList<>();

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
