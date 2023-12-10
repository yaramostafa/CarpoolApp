package com.example.driverside;

public class addTripHelper {
    String fromTrip,toTrip,priceTrip,numTrip,timeTrip,MaxRider,driverEmail,driverStatus;

    public addTripHelper(String fromTrip, String toTrip, String priceTrip, String numTrip, String timeTrip,
                         String MaxRider, String driverEmail,String driverStatus) {
        this.fromTrip = fromTrip;
        this.toTrip = toTrip;
        this.priceTrip = priceTrip;
        this.numTrip = numTrip;
        this.timeTrip = timeTrip;
        this.MaxRider = MaxRider;
        this.driverEmail= driverEmail;
        this.driverStatus=driverStatus;

    }

    public String getDriverStatus() {
        return driverStatus;
    }

    public void setDriverStatus(String driverStatus) {
        this.driverStatus = driverStatus;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getMaxRider() {
        return MaxRider;
    }

    public void setMaxRider(String maxRider) {
        MaxRider = maxRider;
    }

    public String getFromTrip() {
        return fromTrip;
    }

    public void setFromTrip(String fromTrip) {
        this.fromTrip = fromTrip;
    }

    public String getToTrip() {
        return toTrip;
    }

    public void setToTrip(String toTrip) {
        this.toTrip = toTrip;
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

    public String getTimeTrip() {
        return timeTrip;
    }

    public void setTimeTrip(String timeTrip) {
        this.timeTrip = timeTrip;
    }
}
