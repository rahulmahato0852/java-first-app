package com.example.server.model;

import java.util.Optional;

public class Res {
    boolean status;
    Object data;
    Optional<String> errors;
    Optional<String> message;

    public Res(boolean status, Optional<String> errors, Optional<String> message) {
        this.status = status;
        this.errors = errors;
        this.message = message;
    }

    public Optional<String> getErrors() {
        return errors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Optional<String> getMessage() {
        return message;
    }

    public boolean getStatus() {
        return status || false;
    }

    public void setErrors(Optional<String> errors) {
        this.errors = errors;
    }

    public void setMessage(Optional<String> message) {
        this.message = message;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
