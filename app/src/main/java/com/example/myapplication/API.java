package com.example.myapplication;

import okhttp3.HttpUrl;

public class API {

    public void get (String url,String searchKey) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

    }
}

