package com.handsup.squick.Dto.MemberDto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberExpulsionDto {
    String hostId;
    String userId;
    String groupName;
}
