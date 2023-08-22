package com.handsup.squick.group.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCreateDto {
    String groupName;
    String memberName;
    int timeLeft;
    String authCode;
    double latitude;
    double longitude;
}
