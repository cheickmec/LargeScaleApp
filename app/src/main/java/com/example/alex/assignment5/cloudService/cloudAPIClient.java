package com.example.alex.assignment5.cloudService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by zach on 4/17/16.
 */
public interface cloudAPIClient {

    //post user
    @POST("users/")
    Call<usersPostResponse> userCreate(
            //no params
    );

    @DELETE("users/{userid}")
    Call<usersPostResponse> userDelete(
            @Path("userid") String userId
    );

    //post robot
    @POST("robots/")
    Call<usersPostResponse> robotCreate(
            //no params
    );

    //post sensor
    @POST("sensors/")
    Call<usersPostResponse> sensorCreate(

            //no params
    );

    //put sensor update
    @PUT("sensors/{sensorID}")
    Call<Void> sensorUpdate(
            @Body sensorRequest body,
            @Path("sensorID") String sensorId
    );

    //put user update
    @PUT("users/{userID}")
    Call<Void> userUpdate(
      @Body userRequest req,
      @Path("userID") String userId
    );

}



