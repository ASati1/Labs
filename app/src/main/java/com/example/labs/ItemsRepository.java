package com.example.labs;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ItemsRepository {
    public static ItemsRepository sItemsRepository;

    private Context mApplicationContext;

    private LiveData<ArrayList<item>> mItems;
    private LiveData<item> mSelectedItem;

    private ItemsRepository(Context pApplicationContext){
        this.mApplicationContext = pApplicationContext;
    }

    public static ItemsRepository getInstance(Context pApplicationContext){
        if(sItemsRepository ==null){
            sItemsRepository = new ItemsRepository(pApplicationContext);
        }
        return sItemsRepository;
    }

    public LiveData<ArrayList<item>> loadItemsFromJSON(){
        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);
        String url = "https://www.goparker.com/600096/index.json";
        final MutableLiveData<ArrayList<item>> mutableItems = new MutableLiveData<>();
        //Request a jsonObject response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<item> items = parseJSONResponse(response);
                        mutableItems.setValue(items);
                        mItems = mutableItems;
                    }
                 },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        String errorResponse = "That didn't work!";
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
        return mutableItems;

        }
    private ArrayList<item> parseJSONResponse(JSONObject pResponse) {
        ArrayList<item> items = new ArrayList();
        try {
            JSONArray itemsArray = pResponse.getJSONArray("items");
            for (int i =0;i<itemsArray.length(); i++){
                JSONObject itemObject = itemsArray.getJSONObject(i);
                item item = parseJSONItem(itemObject);
                items.add(item);
            }
        }
        catch(JSONException e) {
            e.printStackTrace();
        }
        return items;
    }

    private item parseJSONItem(JSONObject pItemObject) throws org.json.JSONException {
        String title = pItemObject.getString("title");
        String link= pItemObject.getString("link");;
        String date= pItemObject.getString("pubDate");;
        String description= pItemObject.getString("description");;

        item item = new item(title,link,date,description);

        return item;
    }

    public LiveData<ArrayList<item>> getItems(){
        if(mItems==null){
            mItems = loadItemsFromJSON();
        }
        return mItems;
    }
    public LiveData<item> getItem(int pItemIndex) {

        LiveData<item> transformedItem= Transformations.switchMap(mItems, items ->{
            MutableLiveData<item> itemData = new  MutableLiveData();
            item item = items.get(pItemIndex);
            itemData.setValue(item);

            return itemData;
        });

        mSelectedItem = transformedItem;
        return mSelectedItem;
    }
}
