package com.example.driverside.Helpers;

public class addTripHelper {
    String fromTrip,toTrip,priceTrip,numTrip,timeTrip,MaxRider,driverEmail,driverStatus,tripID,tripDate;
    public addTripHelper() {
        // Default constructor required for Firebase
    }

    public addTripHelper(String fromTrip, String toTrip, String priceTrip, String numTrip, String timeTrip,
                         String MaxRider, String driverEmail,String driverStatus,String tripID,String tripDate ) {
        this.fromTrip = fromTrip;
        this.toTrip = toTrip;
        this.priceTrip = priceTrip;
        this.numTrip = numTrip;
        this.timeTrip = timeTrip;
        this.MaxRider = MaxRider;
        this.driverEmail= driverEmail;
        this.driverStatus=driverStatus;
        this.tripID=tripID;
        this.tripDate=tripDate;

    }

    public String getTripDate() {
        return tripDate;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
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
