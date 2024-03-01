package com.gooproper.ui.tbo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TboAgenActivity extends AppCompatActivity {
    TextView TVPoin, TVPoinListingBulanLalu, TVPoinInfoBulanLalu, TVPoinListingBulanIni, TVPoinInfoBulanIni, TVPoinBulanLalu, TVPoinBulanIni, TVInfoBulanLalu, TVListingBulanLalu, TVExclusiveBulanLalu, TVOpenBulanLalu, TVBannerBulanLalu, TVInfoBulanIni, TVListingBulanIni, TVExclusiveBulanIni, TVOpenBulanIni, TVBannerBulanIni;
    int PoinListingBulanLalu, PoinInfoBulanLalu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tbo_agen);

        TVPoin = findViewById(R.id.TVPoin);
        TVPoinListingBulanLalu = findViewById(R.id.TVPoinListingBulanLalu);
        TVPoinInfoBulanLalu = findViewById(R.id.TVPoinInfoBulanLalu);
        TVPoinListingBulanIni = findViewById(R.id.TVPoinListingBulanIni);
        TVPoinInfoBulanIni = findViewById(R.id.TVPoinInfoBulanIni);
        TVPoinBulanLalu = findViewById(R.id.TVPoinBulanLalu);
        TVPoinBulanIni = findViewById(R.id.TVPoinBulanIni);
        TVInfoBulanLalu = findViewById(R.id.TVInfoBulanLalu);
        TVInfoBulanIni = findViewById(R.id.TVInfoBulanIni);
        TVListingBulanLalu = findViewById(R.id.TVListingBulanLalu);
        TVListingBulanIni = findViewById(R.id.TVListingBulanIni);
        TVExclusiveBulanLalu = findViewById(R.id.TVExclusiveBulanLalu);
        TVExclusiveBulanIni = findViewById(R.id.TVExclusiveBulanIni);
        TVOpenBulanLalu = findViewById(R.id.TVOpenBulanLalu);
        TVOpenBulanIni = findViewById(R.id.TVOpenBulanIni);
        TVBannerBulanLalu = findViewById(R.id.TVBannerBulanLalu);
        TVBannerBulanIni = findViewById(R.id.TVBannerBulanIni);

//        CountPoin();
        CountPoinBulanLalu();
        CountPoinBulanIni();
        CountPoinInfoBulanLalu();
        CountPoinInfoBulanIni();
        CountTotalPoinBulanLalu();
        CountTotalPoinBulanIni();
        CountInfoBulanLalu();
        CountInfoBulanIni();
        CountListingBulanLalu();
        CountListingBulanIni();
        CountExclusiveBulanLalu();
        CountExclusiveBulanIni();
        CountOpenBulanLalu();
        CountOpenBulanIni();
        CountBannerBulanLalu();
        CountBannerBulanIni();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void CountPoinBulanLalu() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_SUM_POIN_LISTING_AGEN_BULAN_LALU + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                if (count.isEmpty() || count == null || count.equals("null")) {
                                    TVPoinListingBulanLalu.setText("0");
                                } else {
                                    TVPoinListingBulanLalu.setText(count);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountPoinBulanIni() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_SUM_POIN_LISTING_AGEN_BULAN_INI + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                if (count.isEmpty() || count == null || count.equals("null")) {
                                    TVPoinListingBulanIni.setText("0");
                                } else {
                                    TVPoinListingBulanIni.setText("0");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountPoinInfoBulanLalu() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_SUM_POIN_INFO_AGEN_BULAN_LALU + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                if (count.isEmpty() || count == null || count.equals("null")) {
                                    TVPoinInfoBulanLalu.setText("0");
                                } else {
                                    TVPoinInfoBulanLalu.setText(count);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountPoinInfoBulanIni() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_SUM_POIN_INFO_AGEN_BULAN_INI + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                if (count.isEmpty() || count == null || count.equals("null")) {
                                    TVPoinInfoBulanIni.setText("0");
                                } else {
                                    TVPoinInfoBulanIni.setText("0");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountTotalPoinBulanLalu() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_SUM_TOTAL_POIN_AGEN_BULAN_LALU + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                if (count.isEmpty() || count == null || count.equals("null")) {
                                    TVPoinBulanLalu.setText("0");
                                } else {
                                    TVPoinBulanLalu.setText(count);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountTotalPoinBulanIni() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_SUM_TOTAL_POIN_AGEN_BULAN_INI + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                if (count.isEmpty() || count == null || count.equals("null")) {
                                    TVPoinBulanIni.setText("0");
                                } else {
                                    TVPoinBulanIni.setText("0");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountInfoBulanLalu() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_COUNT_INFO_AGEN_BULAN_LALU + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                TVInfoBulanLalu.setText(count);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountInfoBulanIni() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_COUNT_INFO_AGEN_BULAN_INI + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                TVInfoBulanIni.setText(count);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountListingBulanLalu() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_COUNT_LISTING_AGEN_BULAN_LALU + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                TVListingBulanLalu.setText(count);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountListingBulanIni() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_COUNT_LISTING_AGEN_BULAN_INI + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                TVListingBulanIni.setText(count);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountExclusiveBulanLalu() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_COUNT_EXCLUSIVE_AGEN_BULAN_LALU + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                TVExclusiveBulanLalu.setText(count);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountExclusiveBulanIni() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_COUNT_EXCLUSIVE_AGEN_BULAN_INI + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                TVExclusiveBulanIni.setText(count);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountOpenBulanLalu() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_COUNT_OPEN_AGEN_BULAN_LALU + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                TVOpenBulanLalu.setText(count);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountOpenBulanIni() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_COUNT_OPEN_AGEN_BULAN_INI + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                TVOpenBulanIni.setText(count);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountBannerBulanLalu() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_COUNT_BANNER_AGEN_BULAN_LALU + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                TVBannerBulanLalu.setText(count);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
    private void CountBannerBulanIni() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_COUNT_BANNER_AGEN_BULAN_INI + Preferences.getKeyIdAgen(this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String count = data.getString("TotalPoin");

                                TVBannerBulanIni.setText(count);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
}