package com.collour.api.repository;

import com.collour.api.domain.Team;
import com.collour.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Team findByTeamname(String teamname);

    Team save(Team team);

}
