package com.example.vekshan.myapplication;

public class Service {
    private String serviceId;
    private String serviceName;
    private double servicePrice;


    public Service(){

    }

    public Service(String serviceId, String serviceName, double servicePrice){

        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;

    }

    public Service(String serviceName, double servicePrice){
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServicePrice() {
        return servicePrice;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setServicePrice(double servicePrice) {
        this.servicePrice = servicePrice;
    }


}
