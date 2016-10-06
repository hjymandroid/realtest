package com.hongjie.realm.service;


import java.util.HashMap;
import java.util.List;

import com.hongjie.realm.model.Topic;
import com.hongjie.realm.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/auth/login")
    Call<User> login (@Body HashMap<String, String> credential);

    @GET("/api/topics")
    Call<List<Topic>> listTopics (@Query("tab") String tab,
                                  @Query("category") String category,
                                  @Query("limit") int limit,
                                  @Query("page") int page);
    @POST("/api/topics")
    Call<Topic> post(@Body HashMap<String, String> values);
}
