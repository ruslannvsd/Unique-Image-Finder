package com.example.uniqueimagefinder.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApi {
    @GET("customsearch/v1")
    Call<ResponseBody> searchResults(
        @Query("key") String api,
        @Query("cx") String cx,
        @Query("searchType") String type,
        @Query("q") String text //,
        //@Query("start") int start
    );
}