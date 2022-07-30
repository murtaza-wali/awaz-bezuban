package com.festdepartment.awaaz_e_bezuban.Model;

import com.google.firebase.database.ServerValue;

public class Discussion_Comment {
    String content,uid,unname;
    private String cId;
    private Object timestamp;
    private String gender;


    public Discussion_Comment() {
    }

    public Discussion_Comment(String content, String uid, String unname, String cId,String gender) {

        this.content = content;
        this.uid = uid;
        this.unname = unname;
        this.cId = cId;
        this.timestamp = ServerValue.TIMESTAMP;
        this.gender = gender;
    }



    public String getContent() {
        return content;
    }


    public String getgender() {
        return gender;
    }

    public void setgender(String gender) {
        this.gender = gender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public String getUnname() {
        return unname;
    }

    public void setUnname(String unname) {
        this.unname = unname;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}

