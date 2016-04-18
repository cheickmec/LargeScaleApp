package com.example.alex.assignment5.cloudService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zach on 4/18/16.
 */
public class usersPostResponse {
    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }
}
