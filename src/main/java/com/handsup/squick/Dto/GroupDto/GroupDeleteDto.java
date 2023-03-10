package com.handsup.squick.Dto.GroupDto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupDeleteDto {
    String hostId;
    String groupName;
}
