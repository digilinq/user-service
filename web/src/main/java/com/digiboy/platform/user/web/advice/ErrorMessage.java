package com.digiboy.platform.user.web.advice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class ErrorMessage {
    private final LocalDateTime timestamp;
    private final Set<String> messages;
    private final String description;

    public ErrorMessage(LocalDateTime timestamp, Set<String> messages, String description) {
        this.timestamp = timestamp;
        this.messages = messages;
        this.description = description;
    }

    public ErrorMessage(String message) {
        this(Collections.singleton(message));
    }

    public ErrorMessage(Set<String> messages) {
        this(LocalDateTime.now(), messages, null);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Set<String> getMessages() {
        return messages;
    }

    public String getDescription() {
        return description;
    }
}
