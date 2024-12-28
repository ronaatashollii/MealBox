package com.example.mealbox;


public class UserMessage {
    private String name;
    private String email;
    private String message;

    // Konstruktor
    public UserMessage(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Funksionet get për të marrë të dhënat
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}

