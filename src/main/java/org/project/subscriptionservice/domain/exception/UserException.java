package org.project.subscriptionservice.domain.exception;

import org.project.subscriptionservice.share.globalException.HttpException;
import org.springframework.http.HttpStatus;

public class UserException extends HttpException {

    public UserException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static UserException badRequest() {
        return new UserException(HttpStatus.BAD_REQUEST, "User input fields wrong");
    }

    public static UserException notFound() {
        return new UserException(HttpStatus.NOT_FOUND, "User ID not found");
    }

    public static UserException forbidden() {
        return new UserException(HttpStatus.FORBIDDEN, "Forbidden");
    }

    public static UserException notAuthorized() {
        return new UserException(HttpStatus.UNAUTHORIZED, "Not Authorized");
    }

    public static UserException alreadyExists() {
        return new UserException(HttpStatus.CONFLICT, "User already exists");
    }

    public static UserException invalidCode(){
        return new UserException(HttpStatus.BAD_REQUEST, "Invalid code");
    }

    public static UserException invalidEmail(){
        return new UserException(HttpStatus.BAD_REQUEST, "Invalid email");
    }

    public static UserException invalidPhone(){
        return new UserException(HttpStatus.BAD_REQUEST, "Invalid phone");
    }

    public static UserException outBalance(){
        return new UserException(HttpStatus.BAD_REQUEST, "Out balance");
    }

    public static UserException efficientBalance(){
        return new UserException(HttpStatus.FORBIDDEN, "Out balance");
    }
}
