package com.handsup.squick.Dto.GroupDto.Attend;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendStatus {
    String memberName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date;
    String state; //Attend, Absent, Late
}
