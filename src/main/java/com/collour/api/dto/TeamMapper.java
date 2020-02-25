package com.collour.api.dto;

import com.collour.api.domain.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {

    @Mapping(target = "teamname")
    @Mapping(target = "photo")
    @Mapping(target = "userList")
        //@Mapping(target = "chatRoomList")
    Team toTeam(TeamDTO teamDTO);


}
