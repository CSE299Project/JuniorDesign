package com.example.juniordesign.chemistry;

public class User {

    String firstName;
    String lastName;
    int age;
    String email;
    String phone;


    public User(){

    }

    public User(String firstName, String lastName, int age, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }
}
