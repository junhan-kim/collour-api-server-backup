package com.collour.api.service;

import com.collour.api.common.JwtTokenUtil;
import com.collour.api.domain.User;
import com.collour.api.dto.ExceptionDTO;
import com.collour.api.dto.FileDownloadDTO;
import com.collour.api.dto.UserDTO;
import com.collour.api.dto.UserMapper;
import com.collour.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class JwtUserDetailsServiceImpl implements JwtUserDetailsService {

    private UserRepository userRepository;
    private PasswordEncoder bcryptEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private FileSystemStorageService fileSystemStorageService;
    private UserMapper userMapper;
    private EmailService emailService;

    // WON'T FIX : This method is related with spring security.
    @Override
    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new ExceptionDTO(ExceptionDTO.ErrorCode.USER_DISABLED);
        } catch (BadCredentialsException e) {
            throw new ExceptionDTO(ExceptionDTO.ErrorCode.USER_INVALID_CREDENTIALS);
        }
    }

    // WON'T FIX : This method is related with spring security.
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsernameAndValid(username, true);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    @Override
    public Boolean isAdminByToken(String token) throws Exception {
        return findUser(jwtTokenUtil.getUsernameFromToken(token)).getAdmin();
    }

    @Override
    public Boolean isEqualTokenAndUsername(String token, String username) {
        return username.equals(jwtTokenUtil.getUsernameFromToken(token));
    }

    @Override
    public Boolean isEmailValidByToken(String token) throws Exception {
        return findUser(jwtTokenUtil.getUsernameFromToken(token)).getEmailValid();
    }

    @Override
    public void checkUserIsAuthorized(String token, String username) throws Exception {
        if (!isEqualTokenAndUsername(token, username))
            throw new ExceptionDTO(ExceptionDTO.ErrorCode.USER_NOT_AUTHORIZED);
    }

    @Override
    public void checkUserIsAdmin(String token) throws Exception {
        if (!isAdminByToken(token)) throw new ExceptionDTO(ExceptionDTO.ErrorCode.USER_NOT_ADMIN);
    }

    @Override
    public void checkUserExists(String username) throws Exception {
        if (userRepository.findByUsernameAndValid(username, true) != null)
            throw new ExceptionDTO(ExceptionDTO.ErrorCode.USER_ALREADY_EXISTS);
    }

    @Override
    public void checkUserEmailIsValid(User user) throws Exception {
        if (!user.getEmailValid()) throw new ExceptionDTO(ExceptionDTO.ErrorCode.USER_EMAIL_NOT_VALID);
    }

    @Override
    public User findUser(String username) throws Exception {
        User user = userRepository.findByUsernameAndValid(username, true);
        if (user == null) throw new ExceptionDTO(ExceptionDTO.ErrorCode.USER_NOT_FOUND);
        return user;
    }

    @Override
    public String getRandomPassword(int len) {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
                'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < len; i++) {
            int idx = (int) (charSet.length * Math.random());
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }


    @Override
    public void create(UserDTO userDTO) throws Exception {
        checkUserExists(userDTO.getUsername());
        userRepository.save(userMapper.toUser(userDTO, bcryptEncoder));
    }

    @Override
    public List<String> readAll(String token) throws Exception {
        checkUserIsAdmin(token);
        List<User> userList = userRepository.findAllByValidOrderByNoAsc(true);
        return userList.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO read(String token, String username) throws Exception {
        checkUserIsAuthorized(token, username);
        return userMapper.toUserDTO(findUser(username));
    }

    @Override
    public FileDownloadDTO readPhoto(String token, String username) throws Exception {
        checkUserIsAuthorized(token, username);
        return fileSystemStorageService.downloadFile(findUser(username).getPhotoUri());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String token, String username, UserDTO userDTO) throws Exception {
        checkUserIsAuthorized(token, username);
        userMapper.updateUser(userDTO, findUser(username), bcryptEncoder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePhoto(String token, String username, MultipartFile file) throws Exception {
        checkUserIsAuthorized(token, username);
        User user = findUser(username);
        fileSystemStorageService.delete(user.getPhotoUri());
        user.setPhotoUri(fileSystemStorageService.uploadUserPhotoByUsername(file, username).getName());
        userRepository.save(user);
    }

    @Override
    public void delete(String token, String username) throws Exception {
        checkUserIsAuthorized(token, username);
        User user = findUser(username);
        user.setValid(false);
        userRepository.save(user);
    }

    @Override
    public String login(UserDTO userDTO) throws Exception {
        authenticate(userDTO.getUsername(), userDTO.getPassword());
        UserDetails userDetails = loadUserByUsername(userDTO.getUsername());
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public void findPassword(UserDTO userDTO) throws Exception {
        User user = findUser(userDTO.getUsername());
        checkUserEmailIsValid(user);
        String newPassword = getRandomPassword(10);
        user.setPassword(bcryptEncoder.encode(newPassword));
        userRepository.save(user);
        emailService.sendFindPasswordMail(user.getEmail(), newPassword);
    }
}

