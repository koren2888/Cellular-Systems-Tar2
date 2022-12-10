package com.example.cellular_systems_tar2.model;

public class Student {
    public String name;
    public String id;
    public String phoneNumber;
    public String address;
    public String avatarUrl;
    public Boolean isChecked;

    public Student(String name, String id, String phoneNumber, String address, String avatarUrl, Boolean isChecked) {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.avatarUrl = avatarUrl;
        this.isChecked = isChecked;
    }
}
