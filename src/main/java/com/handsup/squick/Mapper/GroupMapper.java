package com.handsup.squick.Mapper;

import com.handsup.squick.Dto.GroupDto.GroupCreateDto;
import com.handsup.squick.Dto.GroupDto.GroupDeleteDto;
import com.handsup.squick.Dto.GroupDto.GroupUpdateDto;
import com.handsup.squick.Dto.GroupDto.GroupReadDto;
import com.handsup.squick.Entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    Group groupReadDtoToGroup(GroupReadDto groupReadDto);
    Group groupCreateDtoToGroup(GroupCreateDto groupCreateDto);
    Group groupModifyDtoToGroup(GroupUpdateDto groupUpdateDto);
    Group groupDeleteDtoToGroup(GroupDeleteDto groupDeleteDto);
}
