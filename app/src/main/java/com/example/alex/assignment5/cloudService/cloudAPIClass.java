package com.example.alex.assignment5.cloudService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zach on 4/18/16.
 */
public class cloudAPIClass {
    private static cloudAPIClient client;
    private static final String basePath = "http://10.0.2.2:8080/api/";

    public static cloudAPIClient getApiClient(){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        if(client == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(basePath)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            client = retrofit.create(cloudAPIClient.class);

        }
        return client;

    }
}
