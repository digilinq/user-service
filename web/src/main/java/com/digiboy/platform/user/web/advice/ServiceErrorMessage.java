package com.digiboy.platform.user.web.advice;

import com.digiboy.platform.user.exception.ErrorCode;

public class ServiceErrorMessage {
    final ErrorCode errorCode;
    final String message;

    public ServiceErrorMessage(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
