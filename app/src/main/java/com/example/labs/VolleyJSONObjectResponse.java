package com.example.labs;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public interface VolleyJSONObjectResponse {

    void onResponse(JSONObject pObject, String pTag);

    void onError(VolleyError pError, String pTag);
}
