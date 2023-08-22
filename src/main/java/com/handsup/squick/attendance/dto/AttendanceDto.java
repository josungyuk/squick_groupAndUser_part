package com.handsup.squick.attendance.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    long memberId;
    double latitude; //위도
    double longitude; //경도
    String authCode;
}
