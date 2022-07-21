package com.example.skymovies;

public class Movie {
    String title;
    String uuid;
    String rating;
    String image;

    public String getTitle() {
        return title;
    }

    public String getUuid() {
        return uuid;
    }

    public String getRating() {
        return rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public Movie(String title, String uuid, String rating, String image) {
        this.title = title;
        this.uuid = uuid;
        this.rating = rating;
        this.image = image;
    }
}
