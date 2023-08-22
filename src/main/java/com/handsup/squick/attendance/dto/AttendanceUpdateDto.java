package com.handsup.squick.attendance.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceUpdateDto {
    String groupName;

    String memberName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    String status;
}
