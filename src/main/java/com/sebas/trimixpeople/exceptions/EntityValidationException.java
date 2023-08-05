package com.sebas.trimixpeople.exceptions;

import java.util.HashMap;

public class EntityValidationException extends Exception {

    private int status;
    private HashMap<String, String> responseDetail = new HashMap<>();

    public EntityValidationException(String message, int status) {
        super(message);
        this.status = status;
        responseDetail.put("message", message);
    }

    public EntityValidationException(String message) {
        super(message);
    }

    public int getStatus() {
        return status;
    }

    public HashMap<String, String> getResponseDetail() {
        return responseDetail;
    }

}
