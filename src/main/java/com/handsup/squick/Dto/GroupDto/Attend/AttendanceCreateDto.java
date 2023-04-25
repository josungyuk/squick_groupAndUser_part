package com.handsup.squick.Dto.GroupDto.Attend;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCreateDto {
    long groupId;
    int timeLeft;
    String authCode;
    double latitude;
    double longitude;
}
