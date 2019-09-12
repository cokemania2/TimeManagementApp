package com.example.myapplication;

public class User {
    private String name;
    private int point;
    private String key;

    public User() {
    }

    public User(String name, int point, String key) {
        this.name = name;
        this.point = point;
        this.key = key;
    }


    @Override
    public String toString() {
        return name + "\t\t" + point + "\t\t" + key;
    }

    public int getPoint() {
        return point;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

class Parent extends User {
    Parent(String name, int point, String key) {
        super(name, point, key);
    }
}

class Child extends User {
    Child(String name, int point, String key) {
        super(name, point, key);
    }
}