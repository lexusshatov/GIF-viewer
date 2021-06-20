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
import com.example.gif_viewer.activity.FullscreenActivity;
import com.example.gif_viewer.R;
import com.example.gif_viewer.remote.RootJSON;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FlexboxAdapter extends RecyclerView.Adapter<FlexboxAdapter.ViewHolder> {
    private final Context context;
    private List<RootJSON.GIF> gifs;

    public FlexboxAdapter(Context context, List<RootJSON.GIF> responseGifs) {
        this.context = context;
        this.gifs = responseGifs;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView gifView;

        public ViewHolder(View view) {
            super(view);
            gifView = view.findViewById(R.id.gif);
        }

        public ImageView getGifView() {
            return gifView;
        }
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_image_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide
                .with(context)
                .load(gifs.get(position).images.downsized.url)
                .into(holder.getGifView());
        holder.gifView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullscreenActivity.class);
            intent.putExtra(RootJSON.GIF.class.getSimpleName(), gifs.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return gifs.size();
    }
}
