package com.handsup.squick.attendance.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceInfoDto {
    int timeLeft;
    long attendanceId;
}
