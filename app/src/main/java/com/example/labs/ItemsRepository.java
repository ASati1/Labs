package com.example.labs;

import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class ItemsRepository {
    public static ItemsRepository sItemsRepository;

    private Context mApplicationContext;

//    private LiveData<ArrayList<item>> mItems;
    private MediatorLiveData<ArrayList<item>> mItems;
    private LiveData<item> mSelectedItem;
    private VolleyItemListRetriever mRemoteItemList;
    private ItemsRepository(Context pApplicationContext){
        mApplicationContext = pApplicationContext;
        mItems = new MediatorLiveData<>();
        mRemoteItemList = new VolleyItemListRetriever("https://www.goparker.com/600096/index.json",pApplicationContext);
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
                        saveIndexLocally(response,"index.json");
                        ArrayList<item> items = parseJSONResponse(response);
                        mutableItems.setValue(items);
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
        String image= pItemObject.getString("image");;

        item item = new item(title,link,date,description);
        item.setImageUrl(image);
        return item;
    }

    public LiveData<ArrayList<item>> getItems(){
        LiveData<ArrayList<item>> remoteData = mRemoteItemList.getItems();
        LiveData<ArrayList<item>> localData = loadIndexLocally("index.json");
        mItems.addSource(remoteData,value-> mItems.setValue(value));
        mItems.addSource(localData,value-> mItems.setValue(value));

        return mItems;
    }

    public LiveData<item> getItem(int pItemIndex) {

        LiveData<item> transformedItem= Transformations.switchMap(mItems, items ->{
            MutableLiveData<item> itemData = new  MutableLiveData();
            item item = items.get(pItemIndex);
            itemData.setValue(item);
            if(!loadImageLocally(Uri.parse(item.getImageUrl()).getLastPathSegment(),itemData)){
                loadImage(item.getImageUrl(),itemData);
            }

            return itemData;
        });

        mSelectedItem = transformedItem;
        return mSelectedItem;
    }

    public void loadImage(String pUrl, MutableLiveData<item> pItemData){
        RequestQueue queue = Volley.newRequestQueue(mApplicationContext);
        final MutableLiveData<item> mutableItem = pItemData;

        ImageRequest imageRequest = new ImageRequest(
                pUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                //do something with the image;
                item item = mutableItem.getValue();
                item.setImage(bitmap);
                saveImageLocally(bitmap, Uri.parse(pUrl).getLastPathSegment());
                mutableItem.setValue(item);
            }
        },
                0,  0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorResponse = "That didn't work!";
                    }
                });
        }
        // saving data from the application on a local drive
        public void saveIndexLocally(JSONObject pIndexObject, String pFileName) {
            ContextWrapper contextWrapper = new ContextWrapper(mApplicationContext);
            OutputStreamWriter outputStreamWriter = null;
            try{
                outputStreamWriter = new OutputStreamWriter(
                        contextWrapper.openFileOutput(pFileName,Context.MODE_PRIVATE));
                outputStreamWriter.write(pIndexObject.toString());
                outputStreamWriter.flush();
                outputStreamWriter.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
        // Loading stored data to the application
        private LiveData<ArrayList<item>> loadIndexLocally(String pFileName){
            JSONObject indexObject = null;
            MutableLiveData<ArrayList<item>> mutableItems = new MutableLiveData<ArrayList<item>>();
            try{
                InputStream inputStream = mApplicationContext.openFileInput(pFileName);

                if(inputStream !=null){
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String receiveString ="";
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((receiveString = bufferedReader.readLine()) !=null){
                        stringBuilder.append(receiveString);
                    }

                    inputStream.close();
                    String builtString = stringBuilder.toString();
                    indexObject = new JSONObject(builtString);
                }
            }
            catch (FileNotFoundException e){
                Log.e("JSONLoading","File not found"+e.toString());
            }catch (IOException e){
                Log.e("JSONLoading","Can not read file"+e.toString());
            }catch (JSONException e){
                Log.e("JSONLoading","json error"+e.toString());
            }
            if(indexObject !=null){
                ArrayList<item> items = parseJSONResponse(indexObject);
                mutableItems.setValue(items);
            }

            return mutableItems;
        }

        public void saveImageLocally(Bitmap pBitmap, String pFileName){
            ContextWrapper contextWrapper = new ContextWrapper(mApplicationContext);
            File directory = contextWrapper.getDir("itemsImages",Context.MODE_PRIVATE);
            File file = new File(directory,pFileName);
            if(!file.exists()){
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(file);
                    pBitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }catch (java.io.IOException e){
                    e.printStackTrace();
                }
            }
        }

        public boolean loadImageLocally(String pFileName, MutableLiveData<item> pItemData){
            boolean loaded = false;
            ContextWrapper contextWrapper = new ContextWrapper(mApplicationContext);
            File directory = contextWrapper.getDir("itemImages",Context.MODE_PRIVATE);
            File file = new File(directory, pFileName);
            if(file.exists()){
                FileInputStream fileInputStream = null;
                try{
                    fileInputStream = new FileInputStream(file);
                    Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                    item item = pItemData.getValue();
                    item.setImage(bitmap);
                    pItemData.setValue(item);

                    fileInputStream.close();
                    loaded=true;
                }catch (java.io.IOException e){
                    e.printStackTrace();
                }
            }
            return loaded;
        }
    }
