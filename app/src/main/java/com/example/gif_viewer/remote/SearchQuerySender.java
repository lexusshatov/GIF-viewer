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

public class SearchQuerySender {
    transient public static final String BASE_URL = "https://api.giphy.com/";
    protected final APIService apiService;
    protected final Context context;
    protected Response<RootJSON> response;
    protected int offset;

    public SearchQuerySender(Context context, int offset){
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
        this.offset = offset;
    }

    public void send(String query){
        clearResponse();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = sharedPreferences.getString("language", context.getString(R.string.language_default));
        String limit = sharedPreferences.getString("limit", context.getString(R.string.limit_default));
        String content_rating = sharedPreferences.getString("content_rating", context.getString(R.string.content_rating_default));
        try {
            response = apiService.searchGIFs(
                    context.getString(R.string.GIPHY_API_KEY),
                    query,
                    Integer.parseInt(limit),
                    offset,
                    content_rating,
                    language).execute();
            if (response.isSuccessful()){
                Log.d("QUERY", response.toString());
                Log.d("QUERY", "Count GIFs: " + response.body().gifs.size());
                offset += response.body().gifs.size();
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
