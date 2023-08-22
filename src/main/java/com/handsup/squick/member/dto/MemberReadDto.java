package com.handsup.squick.member.dto;

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
