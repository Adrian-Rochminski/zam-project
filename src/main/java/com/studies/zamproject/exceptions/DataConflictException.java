/* (C)2023 */
package com.studies.zamproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataConflictException extends RuntimeException {

    private DataConflictException(String message) {
        super(message);
    }

    public static DataConflictException userIsAlreadyActivated(String email) {
        return new DataConflictException(
                String.format("User with email: %s is already activated", email));
    }
}
