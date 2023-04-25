package com.handsup.squick.Mapper;

import com.handsup.squick.Dao.GroupDao;
import com.handsup.squick.Dto.GroupDto.GroupCreateDto;
import com.handsup.squick.Dto.GroupDto.GroupDeleteDto;
import com.handsup.squick.Dto.GroupDto.GroupUpdateDto;
import com.handsup.squick.Dto.GroupDto.GroupReadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupDao groupReadDtoToGroup(GroupReadDto groupReadDto);
    GroupDao groupCreateDtoToGroup(GroupCreateDto groupCreateDto);
    GroupDao groupModifyDtoToGroup(GroupUpdateDto groupUpdateDto);
    GroupDao groupDeleteDtoToGroup(GroupDeleteDto groupDeleteDto);
    GroupDao groupDaoToGroup(GroupDao group);
}
