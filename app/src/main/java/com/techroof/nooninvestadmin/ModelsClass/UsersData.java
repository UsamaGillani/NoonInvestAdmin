package com.techroof.nooninvestadmin.ModelsClass;

public class UsersData {

    public UsersData(String email, String id, String name, String phoneNumber, String referedid, String userRId,String Uid) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.referedid = referedid;
        this.userRId = userRId;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getReferedid() {
        return referedid;
    }

    public String getUserRId() {
        return userRId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setReferedid(String referedid) {
        this.referedid = referedid;
    }

    public void setUserRId(String userRId) {
        this.userRId = userRId;
    }

    private String email;
    private String id;
    private String name;
    private String phoneNumber;
    private String referedid;
    private String userRId;


    public UsersData() {
    }


}
