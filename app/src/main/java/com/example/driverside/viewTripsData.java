package com.example.driverside;

public class viewTripsData {
    String timeTrip,priceTrip,numTrip,toTrip,fromTrip,driverStatus,driverEmail,orderId;
    public viewTripsData(){

    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public viewTripsData(String timeTrip, String priceTrip, String numTrip, String toTrip, String fromTrip, String driverEmail, String driverStatus,String orderId) {
        this.timeTrip = timeTrip;
        this.priceTrip = priceTrip;
        this.numTrip = numTrip;
        this.toTrip = toTrip;
        this.fromTrip = fromTrip;
        this.driverEmail = driverEmail;
        this.driverStatus = driverStatus;
        this.orderId=orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTimeTrip() {
        return timeTrip;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public void setTimeTrip(String timeTrip) {
        this.timeTrip = timeTrip;
    }

    public String getPriceTrip() {
        return priceTrip;
    }

    public void setPriceTrip(String priceTrip) {
        this.priceTrip = priceTrip;
    }

    public String getNumTrip() {
        return numTrip;
    }

    public void setNumTrip(String numTrip) {
        this.numTrip = numTrip;
    }

    public String getToTrip() {
        return toTrip;
    }

    public void setToTrip(String toTrip) {
        this.toTrip = toTrip;
    }

    public String getFromTrip() {
        return fromTrip;
    }

    public void setFromTrip(String fromTrip) {
        this.fromTrip = fromTrip;
    }



}
