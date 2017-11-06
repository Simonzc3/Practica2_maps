package com.example.alejo.practica2.Classes;

/**
 * Created by alejo on 5/11/2017.
 */

public class RequestClass {
    private int guideid,tourid,userid;

    public RequestClass(int guideid, int tourid, int userid) {
        this.guideid = guideid;
        this.tourid = tourid;
        this.userid = userid;
    }

    public int getGuideid() {
        return guideid;
    }

    public void setGuideid(int guideid) {
        this.guideid = guideid;
    }

    public int getTourid() {
        return tourid;
    }

    public void setTourid(int tourid) {
        this.tourid = tourid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
