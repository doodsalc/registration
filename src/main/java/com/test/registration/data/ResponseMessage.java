package com.test.registration.data;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMessage {

    private String message;
    private String[] details;

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(String[] details) {
        this.details = details;
    }

    public ResponseMessage(String message, String[] details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getDetails() {
        return details;
    }

    public void setDetails(String[] details) {
        this.details = details;
    }



}
