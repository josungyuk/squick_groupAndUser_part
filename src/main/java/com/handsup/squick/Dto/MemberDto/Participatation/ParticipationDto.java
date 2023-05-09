package com.handsup.squick.Dto.MemberDto.Participatation;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipationDto {
    long groupId;
    long memberId;
    boolean isAccept;
}
