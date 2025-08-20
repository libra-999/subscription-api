package org.project.subscriptionservice.share.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = false)
public class HttpException extends RuntimeException {

    private HttpStatus httpStatus;
    private ErrorConstantException error;

    public HttpException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.error = ErrorConstantException.UNKNOWN_ERROR;
    }

}
