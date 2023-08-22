package com.handsup.squick.group.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupUpdateDto {
    String curName;
    String newName;
    String discription;
}
