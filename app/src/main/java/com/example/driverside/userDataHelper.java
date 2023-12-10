package com.example.driverside;

public class userDataHelper {
    String uniID, email,uid;
    public String getUniID() {
        return uniID;
    }
    public void setUniID(String uniID) {
        this.uniID = uniID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    public userDataHelper(String uniID, String email, String uid) {
        this.uniID = uniID;
        this.email = email;
        this.uid=uid;

    }

    public userDataHelper() {
    }
}
