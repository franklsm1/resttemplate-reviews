package com.galvanize.models;

public class MovieResult {

    private String title;
    private int year;
    private long id;

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setId(long id) {
        this.id = id;
    }
}