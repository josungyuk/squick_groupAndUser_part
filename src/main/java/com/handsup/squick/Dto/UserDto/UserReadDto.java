package com.handsup.squick.Dto.UserDto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadDto {
    String hostId;
    String groupName;
    int Attendence;
    int lateness;
}
