package com.example.zhangbolun.jinjidebishe.otheruser.checkon;

/**
 * Created by zhangbolun on 2017/4/26.
 */

public class CheckOnDetail {
    private String name;
    private String id;
    private String absence;
    private String intime;
    private String outtime;
    private String reason;
    private String date;


    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAbsence(String absence) {
        this.absence = absence;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAbsence() {
        return absence;
    }

    public String getIntime() {
        return intime;
    }

    public String getOuttime() {
        return outtime;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {return date;}
}
