package com.festdepartment.awaaz_e_bezuban.Model;

public class ResgisteredUser {
    private String userName, EmailId, phoneNo, account_createdOn, userId;


    public ResgisteredUser() {
    }

    public ResgisteredUser(String userName, String emailId, String phoneNo, String account_createdOn, String userId) {
        this.userName = userName;
        EmailId = emailId;
        this.phoneNo = phoneNo;
        this.account_createdOn = account_createdOn;
        this.userId = userId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String emailId) {
        EmailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAccount_createdOn() {
        return account_createdOn;
    }

    public void setAccount_createdOn(String account_createdOn) {
        this.account_createdOn = account_createdOn;
    }
}
