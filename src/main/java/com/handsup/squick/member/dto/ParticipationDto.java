package com.handsup.squick.member.dto;

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
