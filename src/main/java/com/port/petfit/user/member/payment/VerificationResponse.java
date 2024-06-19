package com.port.petfit.user.member.payment;

public class VerificationResponse {
    private final boolean success;
    private final String message;

    public VerificationResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // Getters...

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}