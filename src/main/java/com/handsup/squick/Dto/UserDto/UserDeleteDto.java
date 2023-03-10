package com.handsup.squick.Dto.UserDto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDeleteDto {
    String userId;
    String checkUserId;
    String groupName;
}
