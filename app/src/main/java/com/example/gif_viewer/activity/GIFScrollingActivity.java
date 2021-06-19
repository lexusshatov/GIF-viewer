package com.example.gif_viewer.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gif_viewer.R;
import com.example.gif_viewer.adapter.FlexboxAdapter;
import com.example.gif_viewer.databinding.ActivityScrollingBinding;
import com.example.gif_viewer.remote.SearchQuerySender;
import com.example.gif_viewer.remote.RootJSON;
import com.example.gif_viewer.remote.TrendingQuerySender;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexLine;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GIFScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private RootJSON responseBody;
    private FlexboxAdapter adapter;
    private String searchQuery;
    private final int spanCountVertical = 3;
    private final int spanCountHorizontal = 5;
    private TrendingQuerySender trendingQuerySender;
    private SearchQuerySender searchQuerySender;
    private int offset;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putSerializable(RootJSON.class.getSimpleName(), responseBody);
        outState.putString("searchQuery", searchQuery);
        outState.putInt("position", ((GridLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
        outState.putInt("offset", offset);
        Parcelable recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
        outState.putParcelable("recyclerViewState", recyclerViewState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recyclerView.getLayoutManager().onRestoreInstanceState(savedInstanceState.getParcelable("recyclerViewState"));
        responseBody = (RootJSON) savedInstanceState.getSerializable(RootJSON.class.getSimpleName());
        if (responseBody != null) {
            adapter = new FlexboxAdapter(
                    GIFScrollingActivity.this,
                    responseBody);
            recyclerView.setAdapter(adapter);
        }
        searchQuery = savedInstanceState.getString("searchQuery");
        offset = savedInstanceState.getInt("offset");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intentStartSettings = new Intent(this, SettingsActivity.class);
                startActivity(intentStartSettings);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if ((searchQuery == null || searchQuery.isEmpty()) && responseBody == null) {
            new Thread(() -> {
                trendingQuerySender.send(null);
                try {
                    responseBody = (RootJSON) trendingQuerySender.getResponse().body().clone();
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
                offset += trendingQuerySender.getResponse().body().gifs.size();
                Log.d("ENDPOINT1", "offset trending:" + offset);
                adapter = new FlexboxAdapter(
                        GIFScrollingActivity.this,
                        responseBody);
                runOnUiThread(() -> GIFScrollingActivity.this.recyclerView.setAdapter(adapter));
            }).start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        offset = 0;
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler_view_content);
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, spanCountVertical));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, spanCountHorizontal));
        }

        trendingQuerySender = new TrendingQuerySender(this, offset);
        searchQuerySender = new SearchQuerySender(this, offset);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    new Thread(() -> {
                        RootJSON response;
                        if (searchQuery == null || searchQuery.isEmpty()) {
                            //trending add
                            trendingQuerySender.send(null);
                            response = trendingQuerySender.getResponse().body();
                        } else {
                            //search add
                            searchQuerySender.send(searchQuery);
                            response = searchQuerySender.getResponse().body();
                        }
                        try {
                            List<RootJSON.GIF> gifsList = ((RootJSON) response.clone()).gifs;
                            responseBody.gifs.addAll(gifsList);
                            adapter.addViews(response);
                            offset += gifsList.size();
                            runOnUiThread(() -> recyclerView.scrollToPosition(recyclerView.getScrollState() - 1));
                        } catch (CloneNotSupportedException e) {
                            e.printStackTrace();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        Log.d("ENDPOINT2", "offset trending:" + offset);
                    }).start();

                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                searchView.clearFocus();
                searchQuerySender.clearResponse();
                recyclerView.removeAllViews();
                responseBody = null;
                offset = 0;
                recyclerView.scrollToPosition(View.SCROLLBAR_POSITION_DEFAULT);

                new Thread(() -> {
                    searchQuerySender.send(query);
                    //Save response for restore on rotation screen
                    responseBody = searchQuerySender.getResponse().body();
                    offset += searchQuerySender.getResponse().body().gifs.size();
                    Log.d("ENDPOINT3", "offset search:" + offset);
                    adapter = new FlexboxAdapter(
                            GIFScrollingActivity.this,
                            responseBody);
                    runOnUiThread(() -> recyclerView.setAdapter(adapter));
                }).start();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}