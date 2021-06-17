package com.example.gif_viewer.remote;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RootJSON implements Serializable {
    @SerializedName("data")
    public List<GIF> gifs;
    public Pagination pagination;
    public Meta meta;
    
    public class Original implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
        public String mp4_size;
        public String mp4;
        public String webp_size;
        public String webp;
        public String frames;
        public String hash;
    }

    public class Downsized implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class DownsizedLarge implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class DownsizedMedium implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class DownsizedSmall implements Serializable{
        public String height;
        public String width;
        public String mp4_size;
        public String mp4;
    }

    public class DownsizedStill implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class FixedHeight implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
        public String mp4_size;
        public String mp4;
        public String webp_size;
        public String webp;
    }

    public class FixedHeightDownsampled implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
        public String webp_size;
        public String webp;
    }

    public class FixedHeightSmall implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
        public String mp4_size;
        public String mp4;
        public String webp_size;
        public String webp;
    }

    public class FixedHeightSmallStill implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class FixedHeightStill implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class FixedWidth implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
        public String mp4_size;
        public String mp4;
        public String webp_size;
        public String webp;
    }

    public class FixedWidthDownsampled implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
        public String webp_size;
        public String webp;
    }

    public class FixedWidthSmall implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
        public String mp4_size;
        public String mp4;
        public String webp_size;
        public String webp;
    }

    public class FixedWidthSmallStill implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class FixedWidthStill implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class Looping implements Serializable{
        public String mp4_size;
        public String mp4;
    }

    public class OriginalStill implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class OriginalMp4 implements Serializable{
        public String height;
        public String width;
        public String mp4_size;
        public String mp4;
    }

    public class Preview implements Serializable{
        public String height;
        public String width;
        public String mp4_size;
        public String mp4;
    }

    public class PreviewGif implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class PreviewWebp implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class _480wStill implements Serializable{
        public String height;
        public String width;
        public String size;
        public String url;
    }

    public class Hd implements Serializable{
        public String height;
        public String width;
        public String mp4_size;
        public String mp4;
    }

    public class Images implements Serializable{
        public Original original;
        public Downsized downsized;
        public DownsizedLarge downsized_large;
        public DownsizedMedium downsized_medium;
        public DownsizedSmall downsized_small;
        public DownsizedStill downsized_still;
        public FixedHeight fixed_height;
        public FixedHeightDownsampled fixed_height_downsampled;
        public FixedHeightSmall fixed_height_small;
        public FixedHeightSmallStill fixed_height_small_still;
        public FixedHeightStill fixed_height_still;
        public FixedWidth fixed_width;
        public FixedWidthDownsampled fixed_width_downsampled;
        public FixedWidthSmall fixed_width_small;
        public FixedWidthSmallStill fixed_width_small_still;
        public FixedWidthStill fixed_width_still;
        public Looping looping;
        public OriginalStill original_still;
        public OriginalMp4 original_mp4;
        public Preview preview;
        public PreviewGif preview_gif;
        public PreviewWebp preview_webp;
        @SerializedName("480w_still")
        public _480wStill _480w_still;
        public Hd hd;
    }

    public class Onload implements Serializable{
        public String url;
    }

    public class Onclick implements Serializable{
        public String url;
    }

    public class Onsent implements Serializable{
        public String url;
    }

    public class Analytics implements Serializable{
        public Onload onload;
        public Onclick onclick;
        public Onsent onsent;
    }

    public class User implements Serializable{
        public String avatar_url;
        public String banner_image;
        public String banner_url;
        public String profile_url;
        public String username;
        public String display_name;
        public String description;
        public String instagram_url;
        public String website_url;
        public boolean is_verified;
    }

    public class GIF implements Serializable{
        public String type;
        public String id;
        public String url;
        public String slug;
        public String bitly_gif_url;
        public String bitly_url;
        public String embed_url;
        public String username;
        public String source;
        public String title;
        public String rating;
        public String content_url;
        public String source_tld;
        public String source_post_url;
        public int is_sticker;
        public String import_datetime;
        public String trending_datetime;
        public Images images;
        public String analytics_response_payload;
        public Analytics analytics;
        public User user;
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
