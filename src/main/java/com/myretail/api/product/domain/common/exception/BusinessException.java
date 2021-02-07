package com.myretail.api.product.domain.common.exception;


import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -3770003796986186393L;

    private final String errorCode;

    private BindingResult validationErrors;

    private final Integer statusCode;


    public BusinessException(String errorCode, String message, Integer statusCode) {
        super(message);
        this.errorCode = errorCode;
        this.statusCode = statusCode;
    }

}

