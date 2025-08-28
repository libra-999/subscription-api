package org.project.subscriptionservice.domain.exception;

import org.project.subscriptionservice.share.globalException.HttpException;
import org.springframework.http.HttpStatus;

public class PaymentException extends HttpException {

    public PaymentException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static PaymentException paymentFailed(){
        return new PaymentException(HttpStatus.BAD_REQUEST,"Payment failed");
    }

    public static PaymentException notFound(){
        return new PaymentException(HttpStatus.NOT_FOUND,"Payment not found");
    }
}
