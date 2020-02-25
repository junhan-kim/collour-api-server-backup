package com.collour.api.repository;

import com.collour.api.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameAndValid(String username, Boolean valid);

    List<User> findAllByValidOrderByNoAsc(Boolean valid);

    User save(User user);



    /*  example code block : Do not use query sentence in JPA.  DEPRECATED -> hard for management.
    @Query(value = "select u.admin from User u where u.username = :username and u.valid = :valid")
    Boolean findAdminByUsernameAndValid(@Param("username") String username, @Param("valid") Boolean valid);
    */
}