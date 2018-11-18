package com.example.juniordesign.chemistry;

public class User {

    String id;
    String firstName;
    String lastName;
    int age;
    String email;
    String phone;


    public User(){

    }

    public User(String id, String firstName, String lastName, int age, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }
}
