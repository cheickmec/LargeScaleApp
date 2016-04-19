package com.example.alex.assignment5;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by YoussefBJ on 3/28/2016.
 */
public interface PushClient {
    //@GET( /{userId}/messages)
    @GET("/testing/{userId}/messages")
    @Headers("x-api-key: F2yxLdt3dNfvsncGwl0g8eCik3OxNej3LO9M2iHj")
    Call<PushMessage> getData(
            @Path("userId") String userId
    );

    @DELETE("/testing/{userId}/messages/{messageId}")
    @Headers("x-api-key: F2yxLdt3dNfvsncGwl0g8eCik3OxNej3LO9M2iHj")
    Call<ResponseBody>deleteNotification(
            @Path("userId") String userId,
            @Path("messageId") String messageId
    );
}