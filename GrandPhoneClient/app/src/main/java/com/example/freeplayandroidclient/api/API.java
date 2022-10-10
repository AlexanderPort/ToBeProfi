package com.example.freeplayandroidclient.api;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.freeplayandroidclient.VolleyMultipartRequest;
import com.example.freeplayandroidclient.dataClasses.User;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class API {
    private Context context;
    private final RequestQueue requestQueue;
    private static final String API = "http://10.0.2.2:8000";
    public static final int REQUEST_PERMISSIONS = 100;

    public API(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.start(); this.context = context;
    }

    public void postUser(User user, OnResponseListener<String> listener) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                String.format("%s/api/users", API),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("userName", user.getName());
                params.put("userEmail", user.getEmail());
                params.put("userPassword", user.getPassword());
                params.put("userTelephone", user.getTelephone());
                params.put("userStatus", user.getStatus() ? "1" : "0");
                return params;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                listener.onNetworkResponse(response);
                return super.parseNetworkResponse(response);
            }
        };
        requestQueue.add(stringRequest);
    }

    public void searchUserByEmailAndPassword(String email, String password,
                                  Response.Listener<JSONObject> listener) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                String.format("%s/api/users/search/%s/%s", API, email, password), null, listener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){};

        requestQueue.add(jsonRequest);
    }

    public void postUserWithImage(final User user, final Bitmap bitmap,
                                  OnResponseListener<NetworkResponse> listener) {
        String url = String.format("%s/api/users", API);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(
                Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError","" + error.getMessage());
                    }
                })
        {
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long name = System.currentTimeMillis();
                params.put("image", new DataPart(name + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("userName", user.getName());
                params.put("userEmail", user.getEmail());
                params.put("userPassword", user.getPassword());
                params.put("userTelephone", user.getTelephone());
                params.put("userStatus", user.getStatus() ? "1" : "0");
                return params;
            }

        };

        requestQueue.add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public interface OnResponseListener<T> {
        public void onResponse(T response);
        public void onNetworkResponse(NetworkResponse response);
    }

}
