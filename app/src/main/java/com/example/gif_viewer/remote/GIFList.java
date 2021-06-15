package com.example.gif_viewer.remote;

import com.google.gson.annotations.SerializedName;

public class GIFList {
    private String data;

    public GIFList(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
