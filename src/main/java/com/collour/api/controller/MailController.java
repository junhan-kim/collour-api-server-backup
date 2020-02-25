package com.collour.api.controller;

import com.collour.api.common.JwtTokenUtil;
import com.collour.api.dto.MailDTO;
import com.collour.api.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/mail")
@AllArgsConstructor
public class MailController {

    private JwtTokenUtil jwtTokenUtil;
    private EmailService emailService;

    @PostMapping(value = "/send")
    public ResponseEntity<?> sendEmail(@RequestBody MailDTO mailDTO) {
        emailService.sendAuthenticationMail(mailDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/authentication")
    public ResponseEntity<?> authenticate(@RequestHeader("Authorization") String token) throws Exception {
        emailService.authenticate(jwtTokenUtil.getTokenFromBearerToken(token));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/authentication")
    public ResponseEntity<?> readEmailValid(@RequestHeader("Authorization") String token) throws Exception {
        return ResponseEntity.ok(emailService.readEmailValid(jwtTokenUtil.getTokenFromBearerToken(token)));
    }

    @DeleteMapping(value = "/authentication")
    public ResponseEntity<?> revoke(@RequestHeader("Authorization") String token) throws Exception {
        emailService.revoke(jwtTokenUtil.getTokenFromBearerToken(token));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
