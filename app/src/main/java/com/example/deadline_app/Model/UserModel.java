package com.example.deadline_app.Model;

public class UserModel {
    String phone, username, password;

    public UserModel(){

    }
    public UserModel(String phone, String username, String password) {
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getPhone() {
        return phone ;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
