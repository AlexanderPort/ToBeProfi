package com.example.freeplayandroidclient.dataClasses;

public class User {
    private String name;
    private String email;
    private String password;
    private String telephone;
    private Boolean status;
    public User(String name, String email, String password,
                String telephone, Boolean status) {
        this.name = name;
        this.email = email;
        this.status = status;
        this.password = password;
        this.telephone = telephone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public String getTelephone() {
        return telephone;
    }
}
