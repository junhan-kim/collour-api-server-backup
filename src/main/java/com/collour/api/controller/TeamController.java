package com.collour.api.controller;

import com.collour.api.common.JwtTokenUtil;
import com.collour.api.dto.TeamDTO;
import com.collour.api.service.TeamService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/groups")
@AllArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "")
    public ResponseEntity<?> createTeam(@RequestHeader("Authorization") String token, @RequestBody TeamDTO teamDTO) throws Exception {
        teamService.create(teamDTO, jwtTokenUtil.getTokenFromBearerToken(token));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
