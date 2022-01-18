package com.shopme.admin.response;

import jdk.jfr.DataAmount;
import org.springframework.http.HttpStatus;

public class ResponseMessage {
    private HttpStatus status;
    private String message;

    public ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
