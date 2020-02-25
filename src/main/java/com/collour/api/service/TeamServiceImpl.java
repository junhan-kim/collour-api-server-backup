package com.collour.api.service;

import com.collour.api.common.JwtTokenUtil;
import com.collour.api.domain.Team;
import com.collour.api.domain.UserTeam;
import com.collour.api.dto.ExceptionDTO;
import com.collour.api.dto.TeamDTO;
import com.collour.api.dto.TeamMapper;
import com.collour.api.dto.UserTeamMapper;
import com.collour.api.repository.TeamRepository;
import com.collour.api.repository.UserRepository;
import com.collour.api.repository.UserTeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class TeamServiceImpl implements TeamService {

    private TeamRepository teamRepository;
    private TeamMapper teamMapper;
    private UserTeamMapper userTeamMapper;
    private UserTeamRepository userTeamRepository;
    private JwtTokenUtil jwtTokenUtil;
    private UserRepository userRepository;

    @Override
    public void checkTeamExists(String teamname) throws Exception {
        if (teamRepository.findByTeamname(teamname) != null)
            throw new ExceptionDTO(ExceptionDTO.ErrorCode.TEAM_ALREADY_EXISTS);
    }

    @Override
    public void create(TeamDTO teamDTO, String token) throws Exception {

        checkTeamExists(teamDTO.getTeamname());
        Team team = teamRepository.save(teamMapper.toTeam(teamDTO));
        String username = jwtTokenUtil.getUsernameFromToken(token);
        UserTeam userteam = new UserTeam();
        userteam.setUser(userRepository.findByUsernameAndValid(username, true));
        userteam.setTeam(team);
        userTeamRepository.save(userteam);

    }

}
