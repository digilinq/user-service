package com.digiboy.platform.user.exception;

public class UserNotFoundException extends ServiceException {
    private static final long serialVersionUID = 5171871903314498602L;

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.USER_NOT_FOUND;
    }
}
