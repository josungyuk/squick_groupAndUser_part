package com.handsup.squick.Dto.MemberDto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberDeleteDto {
    String userId;
    String checkUserId;
    String groupName;
}
