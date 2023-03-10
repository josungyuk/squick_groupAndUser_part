package com.handsup.squick.Dto.GroupDto;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupReadDto {
    String groupName;
}
