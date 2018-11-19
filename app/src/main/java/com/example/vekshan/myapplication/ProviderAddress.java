package com.example.vekshan.myapplication;

public class ProviderAddress {
    private String buildingNum;
    private String streetName;
    private String city;
    private String postalCode;

    public ProviderAddress(String buildingNum, String streetName, String city, String postalCode){
        this.buildingNum = buildingNum;
        this.streetName = streetName;
        this.city = city;
        this.postalCode = postalCode;
    }

    public ProviderAddress(){

    }

    public String getBuildingNum() {
        return buildingNum;
    }

    public void setBuildingNum(String buildingNum) {
        this.buildingNum = buildingNum;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
