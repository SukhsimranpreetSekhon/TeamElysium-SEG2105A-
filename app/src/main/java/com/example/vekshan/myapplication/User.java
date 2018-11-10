package com.example.vekshan.myapplication;

public class User {


        public String firstName, lastName, email, phoneNumber;

        public User(){

        }

        public User(String firstName, String lastName, String email, String phoneNumber) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}


