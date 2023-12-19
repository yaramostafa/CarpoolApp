package com.example.myproject.Helpers;

import java.util.ArrayList;

public class orderHelper {
    String tripState, fromTrip, toTrip, priceTrip, numTrip, timeTrip, userid,
            driverEmail, orderId,driverStatus;

    public orderHelper(String tripState, String fromTrip, String toTrip, String priceTrip,
                       String numTrip, String timeTrip, String userid, String driverEmail,
                       String orderId, String driverStatus) {
        this.tripState = tripState;
        this.fromTrip = fromTrip;
        this.toTrip = toTrip;
        this.priceTrip = priceTrip;
        this.numTrip = numTrip;
        this.timeTrip = timeTrip;
        this.userid = userid;
        this.driverEmail = driverEmail;
        this.orderId = orderId;
        this.driverStatus = driverStatus;
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

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTripState() {
        return tripState;
    }

    public void setTripState(String tripState) {
        this.tripState = tripState;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getOrderId() {
        return orderId;
    }
}
