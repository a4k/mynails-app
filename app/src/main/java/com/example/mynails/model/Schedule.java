package com.example.mynails.model;

public class Schedule {

    private int id;
    private int service_id;
    private int master_id;
    private int day;
    private int month;
    private int year;
    private String hour;
    private String minute;
    private int type;


    

    public int getId() {
        return id;
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

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public int getType() {
        return type;
    }




    public void setId(int id) {
        this.id = id;
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

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public void setType(int type) {
        this.type = type;
    }
}
