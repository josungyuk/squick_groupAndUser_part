package com.handsup.squick.Mapper;

import com.handsup.squick.Entity.Member;
import com.handsup.squick.Dto.MemberDto.MemberAddDto;
import com.handsup.squick.Dto.MemberDto.MemberDeleteDto;
import com.handsup.squick.Dto.MemberDto.MemberExpulsionDto;
import com.handsup.squick.Dto.MemberDto.MemberReadDto;
import com.handsup.squick.Dao.MemberDao;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface MemberMapper {
    MemberDao MemberAddDtoToMember(MemberAddDto memberAddDto);
    MemberDao MemberDeleteDtoToMember(MemberDeleteDto memberDeleteDto);
    MemberDao MemberExpulsionDtoToMember(MemberExpulsionDto memberExpulsionDto);
    MemberDao MemberReadDtoToMember(MemberReadDto memberReadDto);
    MemberDao MemberDaoToMember(Member memberAddDto);

}
