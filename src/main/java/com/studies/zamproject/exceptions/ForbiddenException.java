/* (C)2023 */
package com.studies.zamproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    private ForbiddenException(String message) {
        super(message);
    }

    public static ForbiddenException noPermissionToOperation() {
        return new ForbiddenException("You have no permission to this operation");
    }
}
