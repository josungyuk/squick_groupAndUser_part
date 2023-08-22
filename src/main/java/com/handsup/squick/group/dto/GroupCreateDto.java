package com.handsup.squick.group.dto;

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
