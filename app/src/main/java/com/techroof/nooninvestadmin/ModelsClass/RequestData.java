package com.techroof.nooninvestadmin.ModelsClass;

public class RequestData {

    public RequestData() {
    }


    public RequestData(String uid, String status, String name, String accountNumber, String withDrawalId, String date, double withdrawalAmount) {
        Uid = uid;
        Status = status;
        this.name = name;
        AccountNumber = accountNumber;
        WithDrawalId = withDrawalId;
        Date = date;
        this.withdrawalAmount = withdrawalAmount;
    }

    public String getUid() {
        return Uid;
    }

    public String getStatus() {
        return Status;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public String getWithDrawalId() {
        return WithDrawalId;
    }

    public String getDate() {
        return Date;
    }

    public double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }

    public void setWithDrawalId(String withDrawalId) {
        WithDrawalId = withDrawalId;
    }

    public void setDate(String date) {
        Date = date;
    }

    public void setWithdrawalAmount(double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    private String Uid;
    private String Status;
    private String name;
    private String AccountNumber;
    private String WithDrawalId;
    private String Date;
    private double withdrawalAmount;



}
