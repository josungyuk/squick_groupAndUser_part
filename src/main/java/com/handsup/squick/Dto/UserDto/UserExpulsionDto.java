package com.handsup.squick.Dto.UserDto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserExpulsionDto {
    String hostId;
    String userId;
    String groupName;
}
