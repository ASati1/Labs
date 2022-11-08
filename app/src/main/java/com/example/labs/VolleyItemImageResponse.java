package com.example.labs;

import android.graphics.Bitmap;

import com.android.volley.VolleyError;

public interface VolleyItemImageResponse {

    void onResponse(Bitmap pImage, item pItem);

    void onError(VolleyError error, String tag);
}
