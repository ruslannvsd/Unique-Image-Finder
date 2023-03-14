package com.example.uniqueimagefinder.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.uniqueimagefinder.adapters.ImageAdapter;
import com.example.uniqueimagefinder.classes.SearchItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {
    OkHttpClient okkClient = new OkHttpClient.Builder().build();
    static String BASE_URL = "https://www.googleapis.com/";
    Retrofit retrofit = new Retrofit
            .Builder()
            .client(okkClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory
                    .create())
            .build();
    public SearchApi searchApi = retrofit.create(SearchApi.class);

    public void getImages(String key, String cx, String type, String text, RecyclerView rv, ImageAdapter ad, Context ctx) {
        List<Integer> listOfPages = new ArrayList<>(Arrays.asList(1, 11, 21, 31));
        List<SearchItem> links = new ArrayList<>();
        for (int i = 0; i < listOfPages.size(); i++) {
            int item = listOfPages.get(i);
            searchApi.searchResults(key, cx, type, text, item).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            JSONObject jsonObj;
                            String jsonString;
                            Log.d("Zzz...", links.toString());

                            try {
                                jsonString = response.body().string();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                jsonObj = new JSONObject(jsonString);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            JSONArray itemsArray;
                            if (jsonObj.has("items")) {
                                try {
                                    itemsArray = jsonObj.getJSONArray("items");
                                    for (int i = 0; i < itemsArray.length(); i++) {
                                        JSONObject dataFinder = itemsArray.getJSONObject(i);
                                        String title = null;
                                        String link = null;
                                        if (dataFinder.has("link")) {
                                            Object linkObj = dataFinder.get("link");
                                            link = linkObj.toString();
                                            if (link.startsWith("http:")) {
                                                link = link.replace("http:", "https:");
                                            }
                                        }
                                        if (dataFinder.has("title")) {
                                            Object titleObj = dataFinder.get("title");
                                            title = titleObj.toString();
                                        }
                                        SearchItem searchItem = new SearchItem(title, link);
                                        links.add(searchItem);

                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                            } else
                                Toast.makeText(ctx, "No data available", Toast.LENGTH_SHORT).show();
                            if (item == 31) {
                                rv.setLayoutManager(new StaggeredGridLayoutManager(
                                        2, StaggeredGridLayoutManager.VERTICAL
                                ));
                                ad.setImages(links, ctx);
                                Toast.makeText(ctx, links.size() + " images", Toast.LENGTH_SHORT).show();
                                rv.setAdapter(ad);
                            }
                        } else Toast.makeText(ctx, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {

                }
            });
        }
    }
}
