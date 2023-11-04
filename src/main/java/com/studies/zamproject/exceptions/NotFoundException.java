/* (C)2023 */
package com.studies.zamproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException userNotFound(String email) {
        return new NotFoundException(String.format("User with email: %s not found", email));
    }

    public static NotFoundException userWithIdNotFound(Long id) {
        return new NotFoundException(String.format("User with id: %d not found", id));
    }

    public static NotFoundException userWithTokenNotFound(String token) {
        return new NotFoundException(String.format("User with token: %s not found", token));
    }

    public static NotFoundException eventWithIdNotFound(Long id) {
        return new NotFoundException(String.format("Event with id: %d not found", id));
    }
}
