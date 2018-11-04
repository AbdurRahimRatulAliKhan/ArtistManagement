package com.shajib.artistmanagement;

public class Artist {

    public String id;
    public String name;
    public String country;
    public String gender;

    public Artist(){

    }

    public Artist(String id, String name, String country, String gender) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.gender = gender;
    }
}
