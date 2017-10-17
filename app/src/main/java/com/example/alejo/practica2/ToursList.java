package com.example.alejo.practica2;

import java.util.ArrayList;

/**
 * Created by Usuario on 16/10/2017.
 */

public class ToursList {
    private String title;
    private String time;
    private String cost;
    private int imageID;
    private int actionItem;

    public ToursList(String title, String time, String cost, int imageID, int actionItem) {
        this.title = title;
        this.time = time;
        this.cost = cost;
        this.imageID = imageID;
        this.actionItem = actionItem;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getCost() {
        return cost;
    }

    public int getImageID() {
        return imageID;
    }

    public int getActionItem() {
        return actionItem;
    }

    public static ArrayList<ToursList> addAvailableTours(){
        ArrayList<ToursList> toursLists = new ArrayList<>();
        toursLists.add(new ToursList("Medellin city tour", "4 hours", "$ 92 each", R.drawable.image_medellin, R.drawable.geopoint));
        toursLists.add(new ToursList("East old Paisa Towns", "Full day (9am to 5pm)", "$ 180 each", R.drawable.image_east, R.drawable.geopoint));
        toursLists.add(new ToursList("SocialNnovation tour", "4 hours", "$ 120 each", R.drawable.image_social, R.drawable.geopoint));
        return toursLists;
    }

    public static ArrayList<ToursList> addSchedduledTours(){
        ArrayList<ToursList> toursLists = new ArrayList<>();
        toursLists.add(new ToursList("East old Paisa Towns", "Full day (9am to 5pm)", "$ 180 each", R.drawable.image_east, R.drawable.geopoint));
        return toursLists;
    }
}
