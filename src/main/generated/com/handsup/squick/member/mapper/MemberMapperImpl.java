package com.handsup.squick.member.mapper;

import com.handsup.squick.member.dto.MemberAddDto;
import com.handsup.squick.member.dto.MemberDeleteDto;
import com.handsup.squick.member.dto.MemberExpelDto;
import com.handsup.squick.member.dto.MemberReadDto;
import com.handsup.squick.member.entity.Member;
import com.handsup.squick.member.entity.Member.MemberBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-08T13:04:36+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class MemberMapperImpl implements MemberMapper {

    @Override
    public Member MemberAddDtoToMember(MemberAddDto memberAddDto) {
        if ( memberAddDto == null ) {
            return null;
        }

        MemberBuilder member = Member.builder();

        return member.build();
    }

    @Override
    public Member MemberDeleteDtoToMember(MemberDeleteDto memberDeleteDto) {
        if ( memberDeleteDto == null ) {
            return null;
        }

        MemberBuilder member = Member.builder();

        member.groupName( memberDeleteDto.getGroupName() );

        return member.build();
    }

    @Override
    public Member MemberExpulsionDtoToMember(MemberExpelDto memberExpelDto) {
        if ( memberExpelDto == null ) {
            return null;
        }

        MemberBuilder member = Member.builder();

        member.groupName( memberExpelDto.getGroupName() );

        return member.build();
    }

    @Override
    public Member MemberReadDtoToMember(MemberReadDto memberReadDto) {
        if ( memberReadDto == null ) {
            return null;
        }

        MemberBuilder member = Member.builder();

        member.groupName( memberReadDto.getGroupName() );

        return member.build();
    }
}
