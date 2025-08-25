package org.project.subscriptionservice.domain.exception;

import org.project.subscriptionservice.share.globalException.HttpException;
import org.springframework.http.HttpStatus;

public class SubException extends HttpException {

    public SubException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static SubException notFound(){
        return new SubException(HttpStatus.NOT_FOUND, "Subscription Not Found");
    }

}
