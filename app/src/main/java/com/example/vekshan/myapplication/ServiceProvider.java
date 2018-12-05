package com.example.vekshan.myapplication;

import java.util.List;

public class ServiceProvider  extends User {

    private boolean licensed;
    private String address;
    private String generalDescription;
    private String companyName;
    private String id;
    private String password;
    private String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ServiceProvider(String firstName, String lastName, String email, String phoneNumber) {

        super(firstName, lastName, email, phoneNumber);
        this.licensed=licensed;

    }

    public ServiceProvider(String firstName, String lastName, String email, String phoneNumber,String password) {
        super(firstName, lastName, email, phoneNumber);
        this.licensed=licensed;
        this.password= password;

    }

    public ServiceProvider(String id, String FirstName, String phoneNumber){
        this.id=id;
        this.firstName=FirstName;
        this.phoneNumber=phoneNumber;
    }

    public ServiceProvider(){

    }
    public ServiceProvider(String id) {
        this.id=id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isLicensed() {
        return licensed;
    }

    public void setLicensed(boolean licensed) {
        this.licensed = licensed;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generalDescription) {
        this.generalDescription = generalDescription;
    }
}
