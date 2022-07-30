package com.festdepartment.awaaz_e_bezuban.Model;

import android.content.Intent;

public class ReportModel {
    String userName, post_time, userID,  postDes, location, postType, gender, age, petType, postID, lostType;

    String reportImg;

    public ReportModel() {
    }

    public ReportModel(String userName, String post_time, String userID, String postDes, String location, String postType, String gender, String age, String petType, String lostType, String reportImg) {
        this.userName = userName;
        this.post_time = post_time;
        this.userID = userID;
        this.postDes = postDes;
        this.location = location;
        this.postType = postType;
        this.gender = gender;
        this.age = age;
        this.petType = petType;
        this.lostType = lostType;
        this.reportImg = reportImg;
    }

    public String getLostType() {
        return lostType;
    }

    public void setLostType(String lostType) {
        this.lostType = lostType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPost_time() {
        return post_time;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getReportImg() {
        return reportImg;
    }

    public void setReportImg(String reportImg) {
        this.reportImg = reportImg;
    }

    public String getPostDes() {
        return postDes;
    }

    public void setPostDes(String postDes) {
        this.postDes = postDes;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }
}
