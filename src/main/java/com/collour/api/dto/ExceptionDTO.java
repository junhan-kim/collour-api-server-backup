package com.collour.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionDTO extends Exception {

    @Getter
    @AllArgsConstructor
    public enum ErrorCode {

        // Common
        INTERNAL_SERVER_ERROR(500, "COMMON_1", "Error occurred in server."),

        // User
        USER_DISABLED(401, "USER_1", "User is disabled."),
        USER_INVALID_CREDENTIALS(401, "USER_2", "Username or password is incorrect."),

        USER_NOT_FOUND(500, "USER_10", "User is not found."),
        USER_ALREADY_EXISTS(500, "USER_11", "User already exists."),
        USER_NOT_ADMIN(403, "USER_12", "User is not admin."),
        USER_NOT_AUTHORIZED(403, "USER_13", "User is not authorized."),
        USER_EMAIL_NOT_VALID(403, "USER_14", "User email is not valid."),

        // Team
        TEAM_ALREADY_EXISTS(500, "TEAM_1", "Team already exists."),

        // File
        FILE_NOT_EXISTS(500, "FILE_1", "File is not found."),

        // Chat Room
        CHATROOM_NOT_FOUND(500, "CHAT_1", "Chat room is not found"),

        // Calendar
        CALENDAR_NOT_FOUND(500, "CALENDAR_1", "Calendar is not found."),
        ;

        final int status;
        final String code;
        final String message;
    }

    private ErrorCode errorCode;
}
