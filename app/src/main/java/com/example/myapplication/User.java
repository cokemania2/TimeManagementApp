package com.example.myapplication;

import java.util.ArrayList;

public class User {


    private String address;
    private String name;
    private String privateKey;
    private String payLoad;
    private String totalTime;
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

    public String getTotalTime() { return totalTime; }
    public void setTotalTime(String totaltime) { this.totalTime = totaltime; }

    public User() { }

    public User(String address, String name, String privateKey, String payload, String total_time) {
        this.address = address;
        this.name = name;
        this.privateKey = privateKey;
        this.payLoad = payload;
        this.totalTime = total_time;
    }

    @Override
    public String toString() {
        return "[" + name + "]" + " address: " + address + "\nprivateKey : "+ privateKey + "\npayload : " + payLoad + "\ntotalTime : "+ totalTime + "\ntransaction: " + txList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}