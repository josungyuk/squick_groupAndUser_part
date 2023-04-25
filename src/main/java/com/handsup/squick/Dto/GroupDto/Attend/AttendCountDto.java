package com.handsup.squick.Dto.GroupDto.Attend;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendCountDto {
    int attend;
    int absent;
    int late;
}
