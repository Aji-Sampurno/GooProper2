package com.gooproper.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AgenManager {

    public void fetchDataFromApi(final Context context, final ApiCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_AGEN, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Proses data JSON di sini
                            List<DataItem> dataList = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String Idagen = jsonObject.getString("IdAgen");
                                String Nama = jsonObject.getString("Nama");
                                dataList.add(new DataItem(Idagen, Nama));
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

    // Contoh kelas untuk menyimpan data ID dan nama
    public class DataItem {
        private String IdAgen;
        private String Nama;

        public DataItem(String IdAgen, String Nama) {
            this.IdAgen = IdAgen;
            this.Nama = Nama;
        }

        public String getId() {
            return IdAgen;
        }

        public String getName() {
            return Nama;
        }
    }

}
