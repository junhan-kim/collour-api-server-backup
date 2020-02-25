package com.collour.api.controller;

import com.collour.api.common.JwtTokenUtil;
import com.collour.api.dto.FileDownloadDTO;
import com.collour.api.dto.UserDTO;
import com.collour.api.service.JwtUserDetailsService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) throws Exception {
        return ResponseEntity.ok(jwtUserDetailsService.login(userDTO));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<String>> readUserList(@RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity.ok(jwtUserDetailsService.readAll(jwtTokenUtil.getTokenFromBearerToken(token)));
    }


    @PostMapping(value = "")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        jwtUserDetailsService.create(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<UserDTO> readUser(@RequestHeader("Authorization") String token, @PathVariable String username) throws Exception {
        return ResponseEntity.ok(jwtUserDetailsService.read(jwtTokenUtil.getTokenFromBearerToken(token), username));
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token, @PathVariable String username, @Valid @RequestBody UserDTO userDTO) throws Exception {
        jwtUserDetailsService.update(jwtTokenUtil.getTokenFromBearerToken(token), username, userDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token, @PathVariable String username) throws Exception {
        jwtUserDetailsService.delete(jwtTokenUtil.getTokenFromBearerToken(token), username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping(value = "/{username}/photo")
    public ResponseEntity<?> readUserPhoto(@RequestHeader("Authorization") String token, @PathVariable String username) throws Exception {
        FileDownloadDTO fileDownloadDTO = jwtUserDetailsService.readPhoto(jwtTokenUtil.getTokenFromBearerToken(token), username);
        byte[] encodedBase64FromResource = Base64.getEncoder().encode(FileUtils.readFileToByteArray(fileDownloadDTO.getResource().getFile()));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileDownloadDTO.getType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileDownloadDTO.getResource().getFilename() + "\"")
                .body(encodedBase64FromResource);
    }

    @PostMapping(value = "/{username}/photo")
    public ResponseEntity<?> updateUserPhoto(@RequestHeader("Authorization") String token, @PathVariable String username, @RequestParam("file") MultipartFile file) throws Exception {
        jwtUserDetailsService.updatePhoto(jwtTokenUtil.getTokenFromBearerToken(token), username, file);
        return ResponseEntity.ok(jwtUserDetailsService.read(jwtTokenUtil.getTokenFromBearerToken(token), username));
    }


    @PostMapping(value = "/find/password")
    public ResponseEntity<?> findPassword(@RequestBody UserDTO userDTO) throws Exception {
        jwtUserDetailsService.findPassword(userDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /* example code block : req with file and JSON.  DEPRECATED -> can't use @Valid, json should be param(form-data) in req  .. etc
    public ResponseEntity<Boolean> createUser(@RequestParam("userInfo") String userInfoString, @RequestParam(value = "file", required = false) MultipartFile file) throws JsonProcessingException {
        @Valid UserCreateDTO userCreateDTO  = new ObjectMapper().readValue(userInfoString, UserCreateDTO.class);
        return ResponseEntity.ok(jwtUserDetailsService.create(userCreateDTO, file));
    }
     */

}
