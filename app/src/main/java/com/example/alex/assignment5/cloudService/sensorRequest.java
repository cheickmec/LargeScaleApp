package com.example.alex.assignment5.cloudService;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zach on 4/18/16.
 */
public class sensorRequest {
    @SerializedName("type")
    @Expose
    private String type;

    public sensorRequest(String typeP)
    {
        this.type = typeP;


    }

    public void setType( String typeP)
    {
        this.type = typeP;
    }


}
