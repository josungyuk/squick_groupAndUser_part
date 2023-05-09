package com.handsup.squick.Mapper;

import com.handsup.squick.Dto.MemberDto.MemberExpelDto;
import com.handsup.squick.Entity.Member;
import com.handsup.squick.Dto.MemberDto.MemberAddDto;
import com.handsup.squick.Dto.MemberDto.MemberDeleteDto;
import com.handsup.squick.Dto.MemberDto.MemberReadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface MemberMapper {
    Member MemberAddDtoToMember(MemberAddDto memberAddDto);
    Member MemberDeleteDtoToMember(MemberDeleteDto memberDeleteDto);
    Member MemberExpulsionDtoToMember(MemberExpelDto memberExpelDto);
    Member MemberReadDtoToMember(MemberReadDto memberReadDto);

}
