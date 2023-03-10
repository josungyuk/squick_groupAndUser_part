package com.handsup.squick.Dto.UserDto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAddDto {
    String userId;
    String groupName;
}
