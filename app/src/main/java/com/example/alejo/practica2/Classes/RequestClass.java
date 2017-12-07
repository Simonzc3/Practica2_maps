package com.example.alejo.practica2.Classes;

/**
 * Created by alejo on 5/11/2017.
 */

public class RequestClass {
    private String guide_id, tour_id, user_id;

    public RequestClass(String guideid, String tourid, String userid) {
        this.guide_id = guideid;
        this.tour_id = tourid;
        this.user_id = userid;
    }

    public String getGuide_id() {
        return guide_id;
    }

    public void setGuide_id(String guide_id) {
        this.guide_id = guide_id;
    }

    public String getTour_id() {
        return tour_id;
    }

    public void setTour_id(String tour_id) {
        this.tour_id = tour_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
