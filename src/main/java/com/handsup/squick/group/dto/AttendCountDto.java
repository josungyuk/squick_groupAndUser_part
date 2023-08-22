package com.handsup.squick.group.dto;

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
