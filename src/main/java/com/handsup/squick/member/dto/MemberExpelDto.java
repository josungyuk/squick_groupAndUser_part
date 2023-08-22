package com.handsup.squick.member.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberExpelDto {
    String memberName;
    String groupName;
}
