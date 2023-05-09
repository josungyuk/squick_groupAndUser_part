package com.handsup.squick.Dto.GroupDto;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupCreateDto {
    String groupName;

    String memberName;

    String description;
}
