package com.handsup.squick.Dto.AttendanceDto;

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
