package com.handsup.squick.member.dto;

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
