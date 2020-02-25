package com.collour.api.service;

import com.collour.api.common.JwtTokenUtil;
import com.collour.api.domain.User;
import com.collour.api.dto.MailDTO;
import com.collour.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender,
                            @Lazy JwtUserDetailsService jwtUserDetailsService,
                            JwtTokenUtil jwtTokenUtil,
                            UserRepository userRepository) {
        this.javaMailSender = javaMailSender;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    public void sendFindPasswordMail(String mailTo, String password) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailTo);
        msg.setSubject("Collour: Find Password Mail");
        msg.setText("Your temporary password is " + password + "." + "\n" +
                "Please sign in and change your password.");
        javaMailSender.send(msg);
    }

    public void sendAuthenticationMail(MailDTO mailDTO) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(mailDTO.getMailTo());
        msg.setSubject("Collour: Authentication Mail");
        msg.setText("Your authentication number is " + mailDTO.getAuthenticationNumber() + ".");
        javaMailSender.send(msg);
    }

    public void authenticate(String token) throws Exception {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = jwtUserDetailsService.findUser(username);
        user.setEmailValid(true);
        userRepository.save(user);
    }

    public Boolean readEmailValid(String token) throws Exception {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return jwtUserDetailsService.findUser(username).getEmailValid();
    }

    public void revoke(String token) throws Exception {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = jwtUserDetailsService.findUser(username);
        user.setEmailValid(false);
        userRepository.save(user);
    }
}
