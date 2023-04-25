package com.handsup.squick.Dto.AttendanceDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDto {
    long userId;
    double latitude; //위도
    double longitude; //경도
    String authCode;
}
