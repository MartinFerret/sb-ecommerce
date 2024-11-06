package com.ecommerce.sb_ecom.exceptions;

public class ApiException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ApiException() {}

    public ApiException(String message) {
        super(message);
    }
}
