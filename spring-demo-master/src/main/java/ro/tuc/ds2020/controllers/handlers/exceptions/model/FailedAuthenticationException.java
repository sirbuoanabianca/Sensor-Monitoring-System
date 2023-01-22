package ro.tuc.ds2020.controllers.handlers.exceptions.model;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

public class FailedAuthenticationException extends CustomException {
    private static final String MESSAGE = "Username or password wrong!";
    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public FailedAuthenticationException(String resource) {
        super(MESSAGE,httpStatus, resource, new ArrayList<>());
    }
}
