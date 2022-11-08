package com.example.labs;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;
public class CustomItemImageRequest implements Response.Listener<Bitmap>, Response.ErrorListener{
    private VolleyItemImageResponse mVolleyItemImageResponse;
    private item mItem;
    private ImageRequest mImageRequest;

    public CustomItemImageRequest(String pUrl, item pItem,
                                   VolleyItemImageResponse pVolleyItemImageResponse){
        mVolleyItemImageResponse = pVolleyItemImageResponse;
        mItem = pItem;
        mImageRequest = new ImageRequest(
                pUrl,this,0,0,
                ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,this);
    }

    @Override
    public void onResponse(Bitmap pResponse){
        mVolleyItemImageResponse.onResponse(pResponse,mItem);
    }

    @Override
    public void onErrorResponse(VolleyError pError){
        mVolleyItemImageResponse.onError(pError,mItem.getTitle());
    }

    public ImageRequest getItemImageRequest(){return mImageRequest;}
}
