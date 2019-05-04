package com.example.mynails.model;

public class Config {

    private String api_host = "http://192.168.0.100";
    private String api_url = api_host + "/api/index.php?method=";


    public String getApi_url() {
        return api_url;
    }

    public String getApi_host() {
        return api_host;
    }

}
