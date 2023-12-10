package com.example.driverside;

public class tripRequestsHelper {
    String timeTrip, userid, email, driverEmail, tripState, orderId; // Added orderId field

    public tripRequestsHelper() {
    }

    public String getTripState() {
        return tripState;
    }

    public void setTripState(String tripState) {
        this.tripState = tripState;
    }

    public tripRequestsHelper(String timeTrip, String userid, String email, String driverEmail, String tripState, String orderId) {
        this.timeTrip = timeTrip;
        this.userid = userid;
        this.email = email;
        this.driverEmail = driverEmail;
        this.tripState = tripState;
        this.orderId = orderId; // Added orderId initialization
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderId() {
        return orderId;
    }
}
