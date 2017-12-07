package com.example.alejo.practica2.Classes;

import android.graphics.Bitmap;

/**
 * Created by alejo on 30/10/2017.
 */

public class TourClass {
    private String cost, description,detail,duration,id,name,url;
    Bitmap  bmap;
    public TourClass() {
    }

    public TourClass(String cost, String description, String detail, String duration, String name, String url) {
        this.cost = cost;
        this.description = description;
        this.detail = detail;
        this.duration = duration;
        this.name = name;
        this.url = url;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetail() {
        return detail;
    }

    public Bitmap getBmap() {
        return bmap;
    }

    public void setBmap(Bitmap bmap) {
        this.bmap = bmap;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
