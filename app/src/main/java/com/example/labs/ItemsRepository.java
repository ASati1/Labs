package com.example.labs;

import android.content.ClipData;
import android.content.Context;

import androidx.lifecycle.LiveData;

import org.json.JSONObject;

import java.util.ArrayList;

public class ItemsRepository {
    public static ItemsRepository sItemsRepository;

    private Context mApplicationContext;

    private ItemsRepository(Context pApplicationContext){
        this.mApplicationContext = pApplicationContext;
    }
    
    public static ItemsRepository getInstance(Context pApplicationContext){
        if(sItemsRepository ==null){
            sItemsRepository = new ItemsRepository(pApplicationContext);
        }
        return sItemsRepository;
    }
    
    public LiveData<ArrayList<ClipData.Item>> loadItemsFromJSON(){
        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);
        String url = "https://www.goparker.com/600096/index.json";
        
        //Request a jsonObject response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.Get,
                url,
                null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        
                        
                    }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        String errorResponse = "That didn't work!";
                    }
                }
        );
    }
}
