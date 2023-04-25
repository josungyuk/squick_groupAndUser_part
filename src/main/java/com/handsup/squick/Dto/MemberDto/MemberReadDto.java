package com.handsup.squick.Dto.MemberDto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberReadDto {
    String hostId;
    String groupName;
    int Attendence;
    int lateness;
}
