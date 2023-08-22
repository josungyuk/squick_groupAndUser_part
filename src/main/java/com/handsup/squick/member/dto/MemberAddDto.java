package com.handsup.squick.member.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberAddDto {
    String memberName;
    String code;
}
