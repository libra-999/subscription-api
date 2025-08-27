package org.project.subscriptionservice.domain.exception;

import org.project.subscriptionservice.share.globalException.HttpException;
import org.springframework.http.HttpStatus;

public class SubPlanException extends HttpException {

    public SubPlanException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public static SubPlanException alreadyExist() {
        return new SubPlanException(HttpStatus.CONFLICT, "Plan already exist");
    }

    public static SubPlanException notFound() {
        return new SubPlanException(HttpStatus.NOT_FOUND, "Plan not found");
    }

    public static SubPlanException notSub() {
        return new SubPlanException(HttpStatus.NOT_FOUND, "User not in subscription");
    }
}
