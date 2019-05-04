package com.example.mynails.model;

public class Service {

    private int id;
    private String name;
    private int price;
    private int time;

    private int master_id;
    private int service_id;



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

    public int getMaster_id() {
        return master_id;
    }

    public int getService_id() {
        return service_id;
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

    public void setMaster_id(int master_id) {
        this.master_id = master_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

}
