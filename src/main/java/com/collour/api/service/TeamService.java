package com.collour.api.service;

import com.collour.api.dto.*;


public interface TeamService {

    void checkTeamExists(String teamname) throws Exception;

    void create(TeamDTO teamDTO, String token) throws Exception;


}
