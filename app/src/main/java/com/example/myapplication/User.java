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
    Parent() {}

    Parent(String name, int point, String key) {
        super(name, point, key);
    }
}

class Child extends User {
    private long startTime;
    private long endTime;
    private int restTime;

    Child() {}

    Child(String name, int point, String key) {
        super(name, point, key);
    }

    @Override
    public String toString() {
        return super.getName() + "\t\t" + super.getPoint() + "\t\t" + super.getKey() + "\t\t" + getStartTime() + "\t\t" + getEndTime() + "\t\t" + getRestTime() ;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public long getStartTime() {
        return startTime;
    }
}
