package com.example.gif_viewer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gif_viewer.R;
import com.example.gif_viewer.adapter.FlexboxAdapter;
import com.example.gif_viewer.databinding.ActivityScrollingBinding;
import com.example.gif_viewer.remote.SearchQuerySender;
import com.example.gif_viewer.remote.RootJSON;
import com.example.gif_viewer.remote.TrendingQuerySender;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.jetbrains.annotations.NotNull;

public class GIFScrollingActivity extends AppCompatActivity {

    private ActivityScrollingBinding binding;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private FlexboxLayoutManager flexboxLayoutManager;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> adapter;
    private RootJSON responseBody;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull @NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RootJSON.class.getSimpleName(), responseBody);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        responseBody = (RootJSON) savedInstanceState.getSerializable(RootJSON.class.getSimpleName());
        if (responseBody != null){
            FlexboxAdapter adapter = new FlexboxAdapter(
                    GIFScrollingActivity.this,
                    responseBody);
            recyclerView.setAdapter(adapter);
        }
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScrollingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());
        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recycler_view_content);
        flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        recyclerView.setLayoutManager(flexboxLayoutManager);

        TrendingQuerySender trendingQuerySender = new TrendingQuerySender(this);
        new Thread(() -> {
            trendingQuerySender.send(null);
            responseBody = trendingQuerySender.getResponse().body();
            FlexboxAdapter adapter = new FlexboxAdapter(
                    GIFScrollingActivity.this,
                    responseBody);
            runOnUiThread(() -> GIFScrollingActivity.this.recyclerView.setAdapter(adapter));
        }).start();

        SearchQuerySender searchQuerySender = new SearchQuerySender(this);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                searchQuerySender.clearResponse();
                recyclerView.removeAllViews();
                responseBody = null;
                recyclerView.scrollToPosition(View.SCROLLBAR_POSITION_DEFAULT);
                new Thread(() -> searchQuerySender.send(query)).start();
                while (searchQuerySender.getResponse() == null) {
                }
                //Save response for restore on rotation screen
                responseBody = searchQuerySender.getResponse().body();
                FlexboxAdapter adapter = new FlexboxAdapter(
                        GIFScrollingActivity.this,
                        responseBody);
                recyclerView.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}