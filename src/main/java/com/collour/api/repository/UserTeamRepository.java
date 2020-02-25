package com.collour.api.repository;

import com.collour.api.domain.Team;
import com.collour.api.domain.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {

    UserTeam save(UserTeam userteam);

}
