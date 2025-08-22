package org.project.subscriptionservice.share.globalException;

import lombok.Getter;

@Getter
public enum ErrorConstantException {

    ERROR("An unexpected error occurred"),
    UNKNOWN_ERROR("Unknown error occurred"),
    VALIDATION_ERROR("Validation error"),
    UNAUTHORIZED("Authentication failed");

    private final String message;

    ErrorConstantException(String message) {
        this.message = message;
    }

}
