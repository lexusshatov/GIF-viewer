package com.example.gif_viewer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gif_viewer.FullscreenActivity;
import com.example.gif_viewer.R;
import com.example.gif_viewer.remote.RootJSON;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FlexboxAdapter extends RecyclerView.Adapter<FlexboxAdapter.ViewHolder> {
    private List<String> urlsDownsized;
    private Context context;
    private RootJSON fullResponse;

    public FlexboxAdapter(Context context, RootJSON fullResponse) {
        this.context = context;
        this.fullResponse = fullResponse;
        urlsDownsized = new ArrayList<>();
        for (RootJSON.GIF gif : fullResponse.gifs){
            urlsDownsized.add(gif.images.downsized.url);
        }
    }

    public Context getContext() {
        return context;
    }

    public RootJSON getFullResponse() {
        return fullResponse;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView gifView;
        private RootJSON fullResponse;

        public ViewHolder(View view, Context context, RootJSON fullResponse){
            super(view);
            gifView = view.findViewById(R.id.gif);
            this.fullResponse = fullResponse;
        }

        public ImageView getGifView() {
            return gifView;
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flex_image_items, parent, false);
        return new ViewHolder(view, getContext(), getFullResponse());
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide
                .with(context)
                .load(urlsDownsized.get(position))
                .into(holder.getGifView());
        holder.gifView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullscreenActivity.class);
            intent.putExtra(RootJSON.GIF.class.getSimpleName(), fullResponse.gifs.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return urlsDownsized.size();
    }
}
