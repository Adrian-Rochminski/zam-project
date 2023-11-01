/* (C)2023 */
package com.studies.zamproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private BadRequestException(String message) {
        super(message);
    }

    public static BadRequestException userAlreadyExistsException(String email) {
        return new BadRequestException(String.format("User with email: %s already exists", email));
    }
}
