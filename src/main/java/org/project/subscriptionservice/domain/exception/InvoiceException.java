package org.project.subscriptionservice.domain.exception;

import org.project.subscriptionservice.share.globalException.HttpException;
import org.springframework.http.HttpStatus;

public class InvoiceException extends HttpException {

    public InvoiceException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static InvoiceException notFound(){
        return new InvoiceException(HttpStatus.NOT_FOUND, "Invoice not found");
    }
}
