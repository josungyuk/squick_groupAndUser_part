package com.handsup.squick.Dto.GroupDto.Attend;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendStatus {
    String userName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate time;
    String state; //Attend, Absent, Late
}
