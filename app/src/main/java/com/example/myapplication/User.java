package com.example.myapplication;

import java.util.ArrayList;

public class User {


    private String address;
    private String name;
    private String privateKey;
    private ArrayList<String> txList;


    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public void setTxList(ArrayList<String> txList) {
        this.txList = txList;
    }

    public ArrayList<String> getTxList() {
        return txList;
    }

    public User() {

    }

    public User(String address, String name,String privateKey) {
        this.address = address;
        this.name = name;
        this.privateKey = privateKey;
    }


    @Override
    public String toString() {
        return "[" + name + "]" + " address: " + address + "\n privateKey : "+ privateKey + "\ntransaction: " + txList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}