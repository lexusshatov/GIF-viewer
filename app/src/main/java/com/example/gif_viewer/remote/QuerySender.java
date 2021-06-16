package com.example.gif_viewer.remote;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.gif_viewer.R;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuerySender {
    public static final String BASE_URL = "https://api.giphy.com/";
    private final APIService apiService;
    private final Context context;
    private Response<RootJSON> response;

    public QuerySender(Context context){
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public void send(String query){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = sharedPreferences.getString("language", "en");
        String count = sharedPreferences.getString("count", "25");
        String content_rating = sharedPreferences.getString("content_rating", "g");
        try {
            response = apiService.getGIFs(
                    context.getString(R.string.GIPHY_API_KEY),
                    query,
                    Integer.parseInt(count),
                    0,
                    content_rating,
                    language).execute();
            if (response.isSuccessful()){
                Log.d("QUERY", response.toString());
                Log.d("QUERY", "Count GIFs: " + response.body().gifs.size());
            }else {
                if (response.errorBody() != null) {
                    Log.d("ERROR", response.errorBody().string());
                } else {
                    Log.d("ERROR", "response is null");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response<RootJSON> getResponse() {
        return response;
    }
    public void clearResponse(){
        response = null;
    }
}
