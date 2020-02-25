package com.collour.api.service;

import com.collour.api.domain.User;
import com.collour.api.dto.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface JwtUserDetailsService extends UserDetailsService {

    void authenticate(String username, String password) throws Exception;

    UserDetails loadUserByUsername(String username);

    Boolean isAdminByToken(String token) throws Exception;

    Boolean isEqualTokenAndUsername(String token, String username);

    Boolean isEmailValidByToken(String token) throws Exception;

    void checkUserIsAuthorized(String token, String username) throws Exception;

    void checkUserIsAdmin(String token) throws Exception;

    void checkUserExists(String username) throws Exception;

    void checkUserEmailIsValid(User user) throws Exception;

    User findUser(String username) throws Exception;

    String getRandomPassword(int len);


    void create(UserDTO userDTO) throws Exception;


    List<String> readAll(String token) throws Exception;

    UserDTO read(String token, String username) throws Exception;

    FileDownloadDTO readPhoto(String token, String username) throws Exception;


    void update(String token, String username, UserDTO userDTO) throws Exception;

    void updatePhoto(String token, String username, MultipartFile file) throws Exception;


    void delete(String token, String username) throws Exception;


    String login(UserDTO userDTO) throws Exception;

    void findPassword(UserDTO userDTO) throws Exception;
}
