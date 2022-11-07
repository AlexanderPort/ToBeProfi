package com.example.freeplayandroidclient.dataClasses;

import java.util.UUID;

public class User {
    private String id;
    private String name;
    private String email;
    private String status;
    private String password;
    private String telephone;

    public User(String name, String email, String password,
                String telephone, String status) {
        this.id = UUID.randomUUID().toString().replace('-', 'x');;
        this.name = name;
        this.email = email;
        this.status = status;
        this.password = password;
        this.telephone = telephone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public String getTelephone() {
        return telephone;
    }
}
