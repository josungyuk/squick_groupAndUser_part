package com.handsup.squick.Dto.AttendanceDto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceUpdate {
    String groupName;

    String memberName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;

    String status;
}
