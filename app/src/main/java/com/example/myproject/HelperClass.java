package com.example.myproject;

public class HelperClass {
    String uniID, email,uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

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
    public HelperClass(String uniID, String email,String uid) {
        this.uniID = uniID;
        this.email = email;
        this.uid=uid;
    }
    public HelperClass() {
    }
}