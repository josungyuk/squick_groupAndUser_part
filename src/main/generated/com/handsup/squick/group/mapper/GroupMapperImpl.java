package com.handsup.squick.group.mapper;

import com.handsup.squick.group.dto.GroupCreateDto;
import com.handsup.squick.group.dto.GroupDeleteDto;
import com.handsup.squick.group.dto.GroupReadDto;
import com.handsup.squick.group.dto.GroupUpdateDto;
import com.handsup.squick.group.entity.Group;
import com.handsup.squick.group.entity.Group.GroupBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-08T13:04:36+0900",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.18 (Azul Systems, Inc.)"
)
@Component
public class GroupMapperImpl implements GroupMapper {

    @Override
    public Group groupReadDtoToGroup(GroupReadDto groupReadDto) {
        if ( groupReadDto == null ) {
            return null;
        }

        GroupBuilder group = Group.builder();

        return group.build();
    }

    @Override
    public Group groupCreateDtoToGroup(GroupCreateDto groupCreateDto) {
        if ( groupCreateDto == null ) {
            return null;
        }

        GroupBuilder group = Group.builder();

        group.groupName( groupCreateDto.getGroupName() );
        group.description( groupCreateDto.getDescription() );

        return group.build();
    }

    @Override
    public Group groupModifyDtoToGroup(GroupUpdateDto groupUpdateDto) {
        if ( groupUpdateDto == null ) {
            return null;
        }

        GroupBuilder group = Group.builder();

        return group.build();
    }

    @Override
    public Group groupDeleteDtoToGroup(GroupDeleteDto groupDeleteDto) {
        if ( groupDeleteDto == null ) {
            return null;
        }

        GroupBuilder group = Group.builder();

        return group.build();
    }
}
