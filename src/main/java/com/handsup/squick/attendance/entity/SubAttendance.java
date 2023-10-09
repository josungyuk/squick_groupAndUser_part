package com.handsup.squick.attendance.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.handsup.squick.config.BaseTimeEntity;
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
@Table(name = "SubAttendance")
public class SubAttendance extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;
    @Column(name = "day")
    int day;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    AttendanceStatus attendanceStatus;

    @Column(name = "groupId")
    long groupId;

    @Column(name = "memberId")
    long memberId;

    @Column(name = "latitude")
    double latitude;

    @Column(name = "longitude")
    double longitude;

    @OneToMany(mappedBy = "subAttendance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<MasterSubAttendance> masterSubAttendances = new ArrayList<>();

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
