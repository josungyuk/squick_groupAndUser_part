package com.handsup.squick.Dto.MemberDto;

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
