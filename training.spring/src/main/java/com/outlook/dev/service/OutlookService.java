package com.outlook.dev.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OutlookService {

    @GET("/v1.0/me")
    Call<OutlookUser> getCurrentUser();

    @GET("/v1.0/me/events")
    Call<PagedResult<Event>> getEvents(
            @Query("$orderby") String orderBy,
            @Query("$select") String select,
            @Query("$top") Integer maxResults
    );
}