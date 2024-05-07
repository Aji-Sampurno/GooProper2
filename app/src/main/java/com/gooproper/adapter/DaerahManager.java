package com.gooproper.adapter;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaerahManager {

    public void fetchDataFromApi(final Context context, final ApiCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DAERAH, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<DataItem> dataList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String IdDaerah = jsonObject.getString("IdDaerah");
                                String NamaDaerah = jsonObject.getString("NamaDaerah");
                                dataList.add(new DataItem(IdDaerah, NamaDaerah));
                            }
                            callback.onSuccess(dataList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onError("Error parsing data");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callback.onError("Error fetching data");
            }
        });

        queue.add(request);
    }

    public interface ApiCallback {
        void onSuccess(List<DataItem> dataList);
        void onError(String errorMessage);
    }

    public class DataItem {
        private String IdDaerah;
        private String NamaDaerah;

        public DataItem(String IdDaerah, String NamaDaerah) {
            this.IdDaerah = IdDaerah;
            this.NamaDaerah = NamaDaerah;
        }

        public String getIdDaerah() {
            return IdDaerah;
        }

        public String getNamaDaerah() {
            return NamaDaerah;
        }
    }

}
