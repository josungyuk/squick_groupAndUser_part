package com.handsup.squick.member.mapper;

import com.handsup.squick.member.dto.MemberExpelDto;
import com.handsup.squick.member.entity.Member;
import com.handsup.squick.member.dto.MemberAddDto;
import com.handsup.squick.member.dto.MemberDeleteDto;
import com.handsup.squick.member.dto.MemberReadDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface MemberMapper {
    Member MemberAddDtoToMember(MemberAddDto memberAddDto);
    Member MemberDeleteDtoToMember(MemberDeleteDto memberDeleteDto);
    Member MemberExpulsionDtoToMember(MemberExpelDto memberExpelDto);
    Member MemberReadDtoToMember(MemberReadDto memberReadDto);

}
