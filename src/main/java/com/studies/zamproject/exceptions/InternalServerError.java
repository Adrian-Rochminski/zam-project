/* (C)2023 */
package com.studies.zamproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends RuntimeException {

    private InternalServerError(String message) {
        super(message);
    }

    public static InternalServerError couldNotSendEmail(String email) {
        return new InternalServerError(String.format("Could not send email to: %s", email));
    }
}
