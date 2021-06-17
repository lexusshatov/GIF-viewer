package com.example.gif_viewer.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("/v1/gifs/search")
    Call<RootJSON> searchGIFs(@Query("api_key") String APIKey,
                              @Query("q") String query,
                              @Query("limit") int limit,
                              @Query("offset") int offset,
                              @Query("rating") String rating,
                              @Query("lang") String language);

    @GET("/v1/gifs/trending")
    Call<RootJSON> trendingGIFs(@Query("api_key") String APIKey,
                                @Query("limit") int limit,
                                @Query("rating") String rating);
}
