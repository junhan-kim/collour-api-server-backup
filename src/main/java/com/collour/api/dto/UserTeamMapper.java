package com.collour.api.dto;

import com.collour.api.domain.UserTeam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserTeamMapper {

    @Mapping(target = "admin")
    @Mapping(target = "user")
    @Mapping(target = "team")
    UserTeam toUserTeam(UserTeamDTO userteamDTO);


}
