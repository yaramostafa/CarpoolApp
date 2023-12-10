package com.example.myproject;

public class tripsData {
    String timeTrip,priceTrip,numTrip,toTrip,fromTrip,driverEmail,driverStatus,maxRider;
    tripsData(){}

    public String getDriverEmail() {
        return driverEmail;
    }

    public String getMaxRider() {
        return maxRider;
    }

    public void setMaxRider(String maxRider) {
        this.maxRider = maxRider;
    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getTimeTrip() {
        return timeTrip;
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
