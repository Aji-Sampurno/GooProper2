package com.gooproper.agen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gooproper.R;
import com.gooproper.admin.MainAdminActivity;
import com.gooproper.ui.officer.report.ReportOfficerActivity;
import com.gooproper.ui.officer.survey.SurveyLokasiActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainAgenActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private Runnable runnable;
    private static final int POLLING_INTERVAL = 5000;
    BottomNavigationView bottomNavigationView;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_agen);

        bottomNavigationView = findViewById(R.id.nav_view_agen);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.flowUpAgen_nav, R.id.infoAgen_nav, R.id.homeAgen_nav, R.id.listingAgen_nav, R.id.akunAgen_nav).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_agen);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        ColorStateList colorStateList = ContextCompat.getColorStateList(this, R.color.color_navigation);
        bottomNavigationView.setItemIconTintList(colorStateList);
        bottomNavigationView.setItemTextColor(colorStateList);

        CekOfficer();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void CekOfficer() {
        RequestQueue queuereport = Volley.newRequestQueue(MainAgenActivity.this);
        JsonArrayRequest reqDatareport = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_CEK_OFFICER+ Preferences.getKeyIdAgen(MainAgenActivity.this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                String IsOfficer = data.getString("Officer");
                                if (IsOfficer.equals("1")) {
                                    startBadgePralistingSurvey(bottomNavigationView);
                                } else {
                                    startBadgePralistingAgen(bottomNavigationView);
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

        queuereport.add(reqDatareport);
    }
    private void startBadgePralistingSurvey(BottomNavigationView navigationView) {
        runnable = new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainAgenActivity.this);
                JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_PRALISTING_TERDEKAT, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject data = response.getJSONObject(0);
                                    String count = data.getString("Total");

                                    BadgeDrawable badge = navigationView.getOrCreateBadge(R.id.akunAgen_nav);
                                    if (count.isEmpty() || count.equals("null") || count.equals("0")) {
                                        startBadgePralistingAgen(bottomNavigationView);
                                    } else {
                                        badge.setVisible(true);
                                        badge.setBackgroundColor(ContextCompat.getColor(MainAgenActivity.this, android.R.color.holo_red_dark));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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

                handler.postDelayed(this, POLLING_INTERVAL);
            }
        };

        handler.post(runnable);
    }
    private void startBadgePralistingAgen(BottomNavigationView navigationView) {
        runnable = new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainAgenActivity.this);
                JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_PRALISTING_AGEN + Preferences.getKeyIdAgen(MainAgenActivity.this), null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject data = response.getJSONObject(0);
                                    String count = data.getString("Total");

                                    BadgeDrawable badge = navigationView.getOrCreateBadge(R.id.akunAgen_nav);
                                    if (count.isEmpty() || count.equals("null") || count.equals("0")) {
                                        startBadgePralistingRejectedAgen(bottomNavigationView);
                                    } else {
                                        badge.setVisible(true);
                                        badge.setBackgroundColor(ContextCompat.getColor(MainAgenActivity.this, android.R.color.holo_red_dark));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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

                handler.postDelayed(this, POLLING_INTERVAL);
            }
        };

        handler.post(runnable);
    }
    private void startBadgePralistingRejectedAgen(BottomNavigationView navigationView) {
        runnable = new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainAgenActivity.this);
                JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_PRALISTING_AGEN_REJECTED + Preferences.getKeyIdAgen(MainAgenActivity.this), null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject data = response.getJSONObject(0);
                                    String count = data.getString("Total");

                                    BadgeDrawable badge = navigationView.getOrCreateBadge(R.id.akunAgen_nav);
                                    if (count.isEmpty() || count.equals("null") || count.equals("0")) {
                                        badge.setVisible(false);
                                    } else {
                                        badge.setVisible(true);
                                        badge.setBackgroundColor(ContextCompat.getColor(MainAgenActivity.this, android.R.color.holo_red_dark));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
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

                handler.postDelayed(this, POLLING_INTERVAL);
            }
        };

        handler.post(runnable);
    }
}