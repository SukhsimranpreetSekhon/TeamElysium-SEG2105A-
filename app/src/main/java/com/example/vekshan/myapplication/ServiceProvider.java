package com.example.vekshan.myapplication;

public class ServiceProvider  extends User {

    private boolean licensed;
    private String address;
    private String generalDescription;
    private String companyName;
    private String id;

    public ServiceProvider(String firstName, String lastName, String email, String phoneNumber) {
        super(firstName, lastName, email, phoneNumber);
        this.licensed=licensed;

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
