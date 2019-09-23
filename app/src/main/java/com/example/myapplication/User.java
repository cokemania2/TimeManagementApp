package com.example.myapplication;

import java.util.ArrayList;

public class User {


    private String address;
    private String name;
    private String privateKey;
    private String payLoad;
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

    public String getPayLoad() { return payLoad; }
    public void setPayLoad(String payLoad) { this.payLoad = payLoad; }

    public User() { }

    public User(String address, String name, String privateKey, String payload) {
        this.address = address;
        this.name = name;
        this.privateKey = privateKey;
        this.payLoad = payload;
    }


    @Override
    public String toString() {
        return "[" + name + "]" + " address: " + address + "\nprivateKey : "+ privateKey + "\npayload : " + payLoad + "\ntransaction: " + txList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}