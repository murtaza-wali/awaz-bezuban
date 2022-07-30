package com.festdepartment.awaaz_e_bezuban.Model;

import com.google.firebase.database.ServerValue;

public class Discussion_Post_Model {
    private String postKey;

    private String title, Description;
    private String post_picture;
    private String UserId;
    private String username;
    //    private  String userPhoto;
    private Object timeStamp;


    public Discussion_Post_Model() {
    }

    public Discussion_Post_Model( String title, String description, String post_picture, String userId, String username) {

        this.title = title;
        Description = description;
        this.post_picture = post_picture;
        UserId = userId;
        this.username = username;
        this.timeStamp = ServerValue.TIMESTAMP;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostKey() {
        return postKey;
    }

    public void setPostKey(String postKey) {
        this.postKey = postKey;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPost_picture() {
        return post_picture;
    }

    public void setPost_picture(String post_picture) {
        this.post_picture = post_picture;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }
}

