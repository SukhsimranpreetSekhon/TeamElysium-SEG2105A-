package com.example.vekshan.myapplication;

public class Administrator extends User{

    private String password;
    public Administrator(){

    }

    public Administrator(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Administrator(String firstName, String lastName, String email, String phoneNumber, String password) {
        super(firstName, lastName, email, phoneNumber);
        this.password = password;
    }

}
