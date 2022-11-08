package com.example.labs;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class CustomJSONObjectRequest implements Response.Listener<JSONObject>, Response.ErrorListener{
    private VolleyJSONObjectResponse mVolleyJSONObjectResponse;
    private String mTag;
    private JsonObjectRequest mJsonObjectRequest;

    public CustomJSONObjectRequest(int pMethod, String pUrl, JSONObject pJsonObject, String pTag,
                                   VolleyJSONObjectResponse pVolleyJSONObjectResponse){
        this.mVolleyJSONObjectResponse = pVolleyJSONObjectResponse;
        this.mTag = pTag;
        mJsonObjectRequest = new JsonObjectRequest(
                pMethod,pUrl,
                pJsonObject,this,this);
    }

    @Override
    public void onResponse(JSONObject pResponse){
        mVolleyJSONObjectResponse.onResponse(pResponse,mTag);
    }

    @Override
    public void onErrorResponse(VolleyError pError){
        mVolleyJSONObjectResponse.onError(pError,mTag);
    }

    public JsonObjectRequest getJsonObjectRequest(){return mJsonObjectRequest;}
}
