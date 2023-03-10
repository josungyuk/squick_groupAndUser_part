package com.handsup.squick.Dto.GroupDto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupModifyDto {
    String curGroupName;
    String newGroupName;
    String groupExplain;
    int limitPerson;
}
