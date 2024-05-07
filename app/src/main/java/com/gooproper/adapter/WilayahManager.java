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

public class WilayahManager {

    public void fetchDataFromApi(final Context context, final String IdDaerah, final ApiCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_WILAYAH + IdDaerah, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            List<DataItem> dataList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String IdWilayah = jsonObject.getString("IdWilayah");
                                String NamaWilayah = jsonObject.getString("NamaWilayah");
                                String IdDaerah = jsonObject.getString("NamaWilayah");
                                dataList.add(new DataItem(IdWilayah, NamaWilayah, IdDaerah));
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
        private String IdWilayah;
        private String NamaWilayah;
        private String IdDaerah;

        public DataItem(String IdWilayah, String NamaWilayah, String IdDaerah) {
            this.IdWilayah = IdWilayah;
            this.NamaWilayah = NamaWilayah;
            this.IdDaerah = IdDaerah;
        }

        public String getIdWilayah() {
            return IdWilayah;
        }

        public String getNamaWilayah() {
            return NamaWilayah;
        }

        public String getIdDaerah() {
            return IdDaerah;
        }
    }

}
