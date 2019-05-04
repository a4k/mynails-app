package com.example.mynails.model;

public class Schedule {

    private int id;
    private int user_id;
    private int service_id;
    private int master_id;
    private int day;
    private int month;
    private int year;
    private int hour;
    private int minute;
    private int type;


    

    public int getId() {
        return id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getService_id() {
        return service_id;
    }

    public int getMaster_id() {
        return master_id;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getType() {
        return type;
    }




    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public void setMaster_id(int master_id) {
        this.master_id = master_id;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setType(int type) {
        this.type = type;
    }
}
