package com.example.myapplication;

public class User {
    String user_name;
    String mailaddress;
    String password;
    String street_address;
    int avatar_id;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMailAddress() {
        return mailaddress;
    }

    public void setMailAddress(String mailaddress) {
        this.mailaddress = mailaddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStreet_address() {
        return street_address;
    }

    public void setStreet_address(String street_address) {
        this.street_address = street_address;
    }

    public int getAvatar_id() {
        return avatar_id;
    }

    public void setAvatar_id(int avatar_id) {
        this.avatar_id = avatar_id;
    }
}
