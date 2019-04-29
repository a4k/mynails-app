package com.example.mynails.model;

public class Master {

    private int id;
    private String name;
    private String phone;
    private String description;
    private String image;



    public Master() {
    }

    public Master(int id, String name, String phone, String description, String image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.image = image;
    }




    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }



    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
