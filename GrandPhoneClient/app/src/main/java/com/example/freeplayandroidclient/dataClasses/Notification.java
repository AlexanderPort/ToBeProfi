package com.example.freeplayandroidclient.dataClasses;

public class Notification {
    private String id;
    private String message;

    public Notification(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public Notification(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
