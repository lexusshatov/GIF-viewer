package com.example.gif_viewer.remote;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class RootJSON implements Serializable, Cloneable {
    @SerializedName("data")
    public List<GIF> gifs;
    public Pagination pagination;
    public Meta meta;

    @NonNull
    @NotNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public class Original implements Serializable{
        public String url;
    }

    public class Downsized implements Serializable{
        public String url;
    }

    public class Images implements Serializable{
        public Original original;
        public Downsized downsized;
    }

    public class GIF implements Serializable{
        public String type;
        public String id;
        public String title;
        public Images images;
    }

    public class Pagination implements Serializable{
        public int total_count;
        public int count;
        public int offset;
    }

    public class Meta implements Serializable{
        public int status;
        public String msg;
        public String response_id;
    }

}
