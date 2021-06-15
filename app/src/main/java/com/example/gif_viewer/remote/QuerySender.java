package com.example.gif_viewer.remote;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.telecom.Call;
import android.util.Log;

import androidx.preference.PreferenceDataStore;

import com.example.gif_viewer.GIFScrollingActivity;
import com.example.gif_viewer.R;

import java.io.IOException;
import java.util.prefs.Preferences;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuerySender {
    public static final String BASE_URL = "https://api.giphy.com/";
    private Retrofit retrofit;
    private APIService apiService;
    private Context context;

    public QuerySender(Context context){
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    public void send(String query){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Search settings", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "en");
        String count = sharedPreferences.getString("count", "25");
        String content_rating = sharedPreferences.getString("content_rating", "g");
        Response<GIFList> gifListResponse = null;
        try {
            gifListResponse = apiService.getGIFs(
                    context.getString(R.string.GIPHY_API_KEY),
                    query,
                    Integer.valueOf(count),
                    0,
                    content_rating,
                    language).execute();
            Log.d("QUERY", gifListResponse.body().getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
