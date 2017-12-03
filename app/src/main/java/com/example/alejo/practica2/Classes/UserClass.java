package com.example.alejo.practica2.Classes;

/**
 * Created by alejo on 5/11/2017.
 */

public class UserClass {
    private String email,  name;
    private String lat, longi, phone;
    private String key;

    public UserClass(String email, String lat, String longi, String name,  String phone) {
        this.email = email;
        this.name = name;
        this.lat = lat;
        this.longi = longi;
        this.phone = phone;
    }

    public UserClass() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
