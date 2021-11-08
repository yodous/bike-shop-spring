package com.example.model;

public class ActivationEmail extends Message {
    private static final int SERVER_PORT = 8080; // todo: can I make it not hardcoded
    private static final String DEFAULT_SENDER_EMAIL = "e@commerce.shop";
    private static final String ACTIVATION_SUBJECT = "Activation email";
    private static final String ACTIVATION_TEXT = "localhost:" + SERVER_PORT + "/auth/account-verification?token=";

    public ActivationEmail(String to, String token) {
        super(DEFAULT_SENDER_EMAIL, to, ACTIVATION_SUBJECT, ACTIVATION_TEXT + token);
    }
}
