package com.example.myapplication;

public class User {
    private String account;
    private String name;

    public User() {
    }

    public User(String account, String name) {
        this.account = account;
        this.name = name;
    }


    @Override
    public String toString() {
        return "[" + name + "]" + " account: " + account;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}