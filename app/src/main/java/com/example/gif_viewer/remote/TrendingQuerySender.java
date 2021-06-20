package com.example.gif_viewer.remote;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.preference.PreferenceManager;

import com.example.gif_viewer.R;

import java.io.IOException;

import retrofit2.Response;

public class TrendingQuerySender extends SearchQuerySender {
    public TrendingQuerySender(Context context, int offset) {
        super(context, offset);
    }

    @Override
    public void send(String query) {
        clearResponse();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String limit = sharedPreferences.getString("limit", context.getString(R.string.limit_default));
        String content_rating = sharedPreferences.getString("content_rating", context.getString(R.string.content_rating_default));
        try {
            response = apiService.trendingGIFs(
                    context.getString(R.string.GIPHY_API_KEY),
                    Integer.parseInt(limit),
                    offset,
                    content_rating)
                    .execute();
            if (response.isSuccessful()) {
                Log.d("QUERY", response.toString());
                if (response.body() != null) {
                    Log.d("QUERY", "Count GIFs: " + response.body().gifs.size());
                    offset += response.body().gifs.size();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Response<RootJSON> getResponse() {
        return super.getResponse();
    }

    @Override
    public void clearResponse() {
        super.clearResponse();
    }
}
