package com.digiboy.platform.user.dto;

import java.io.Serializable;
import java.util.UUID;

public class UserDTO implements DataTransferObject, Serializable {

    private static final long serialVersionUID = 7119662012368017730L;

    private UUID userId;

    private String username;

    private String encryptedPassword;

    private String email;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
