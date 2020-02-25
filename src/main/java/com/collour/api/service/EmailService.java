package com.collour.api.service;

import com.collour.api.dto.MailDTO;

public interface EmailService {

    void sendFindPasswordMail(String mailTo, String password);

    void sendAuthenticationMail(MailDTO mailDTO);

    void authenticate(String token) throws Exception;

    Boolean readEmailValid(String token) throws Exception;

    void revoke(String token) throws Exception;
}
