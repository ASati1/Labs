package com.example.labs;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class VolleyItemListRetriever implements VolleyJSONObjectResponse,VolleyItemImageResponse{
    private String mUrl;
    private MutableLiveData<ArrayList<item>> mItemsData;
    private ArrayList<item> mItems;
    private RequestQueue mQueue;

    public VolleyItemListRetriever(String pUrl, Context pContext){
        mUrl = pUrl;
        mQueue = Volley.newRequestQueue(pContext);
    }

    public LiveData<ArrayList<item>> getItems(){
        mItemsData = new MutableLiveData<ArrayList<item>>();
        CustomJSONObjectRequest request = new CustomJSONObjectRequest(Request.Method.GET,mUrl,
                null,"ItemListJSON",this);
        mQueue.add(request.getJsonObjectRequest());
        return mItemsData;
    }

    @Override
    public void onResponse(JSONObject pObject, String pTag){
        Log.i("VolleyItemListRetriever",pTag);
        mItems = parseJSONResponse(pObject);
        mItemsData.setValue(mItems);
    }

    @Override
    public void onResponse(Bitmap pImage, item pItem){
        Log.i("VolleyItemListRetriever","Image retrieved for:"+pItem.getTitle());
        pItem.setImage(pImage);
        mItemsData.setValue(mItems);
    }

    @Override
    public void onError(VolleyError pError, String pTag){Log.e("VolleyItemListRetriever",pTag);}

    private ArrayList<item> parseJSONResponse(JSONObject pObject) {
        ArrayList<item> items = new ArrayList<>();
        return items;
    }
}
