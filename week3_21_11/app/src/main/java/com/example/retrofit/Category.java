package com.example.retrofit;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;


    @SerializedName("images")
    private String imageUrl;

    @SerializedName("description")
    private String description;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}
