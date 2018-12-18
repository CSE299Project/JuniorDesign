package com.example.juniordesign.chemistry;

import java.util.ArrayList;

public class User {

    String firstName;
    String lastName;
    int age;
    String email;
    String phone;
    String currentMood;
    ArrayList<String> interests;


    public User(){

    }

    public User(String firstName, String lastName, int age, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }


    public ArrayList<String> getInterestList() {
        return interests;
    }

    public void setInterestList(ArrayList<String> interests) {
        this.interests = interests;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCurrentMood() {
        return currentMood;
    }

    public void setCurrentMood(String currentMood) {
        this.currentMood = currentMood;
    }
}
