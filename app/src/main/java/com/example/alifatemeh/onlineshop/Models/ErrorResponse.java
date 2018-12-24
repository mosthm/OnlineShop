package com.example.alifatemeh.onlineshop.Models;

public class ErrorResponse {
    private String name;
    private String message;

    public ErrorResponse(String name) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
