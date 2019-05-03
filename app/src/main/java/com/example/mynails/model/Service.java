package com.example.mynails.model;

public class Service {

    private int id;
    private String name;
    private int price;
    private int time;



    public Service() {
    }

    public Service(int id, String name, int price, int time) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.time = time;
    }



    //getter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getTime() {
        return time;
    }

    // setter
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
