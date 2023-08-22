package com.handsup.squick.group.mapper;

import com.handsup.squick.group.dto.GroupCreateDto;
import com.handsup.squick.group.dto.GroupDeleteDto;
import com.handsup.squick.group.dto.GroupUpdateDto;
import com.handsup.squick.group.dto.GroupReadDto;
import com.handsup.squick.group.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    Group groupReadDtoToGroup(GroupReadDto groupReadDto);
    Group groupCreateDtoToGroup(GroupCreateDto groupCreateDto);
    Group groupModifyDtoToGroup(GroupUpdateDto groupUpdateDto);
    Group groupDeleteDtoToGroup(GroupDeleteDto groupDeleteDto);
}
