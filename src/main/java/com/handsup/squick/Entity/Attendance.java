package com.handsup.squick.Entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
@Table(name = "AttendanceTable")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long attandanceId;
    @Column(name = "day")
    int day;
    @Column(name = "time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate time;
    @Column(name = "status")
    String status;

    @OneToMany(mappedBy = "attendance")
    List<MemberGroupAttendance> memberGroupAttendances = new ArrayList<>();
}
