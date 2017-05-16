package com.example.zhangbolun.jinjidebishe.dynamic;

/**
 * Created by zhangbolun on 2017/5/14.
 */

public class DynamicSchool {
    private String id;
    private String senderHead;
    private String sender;
    private String mark;
    private String content;
    private String date;
    private String time;
    private String picture;
    private String current_user;

    public String getCurrent_user() {
        return current_user;
    }

    public void setCurrent_user(String current_user) {
        this.current_user = current_user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderHead() {
        return senderHead;
    }

    public void setSenderHead(String senderHead) {
        this.senderHead = senderHead;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
