package com.handsup.squick.Dto.GroupDto;

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
