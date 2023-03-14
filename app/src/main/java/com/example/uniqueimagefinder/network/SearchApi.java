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
        @Query("q") String text
        //@Query("start") int start
    );
}

// https://www.googleapis.com/customsearch/v1?key=AIzaSyCWIhnmKRlAio1xolDF9ufEMPnMoeoT29k&cx=017576662512468239146:omuauf_lfve&searthType=image&q=space
