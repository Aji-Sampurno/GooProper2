package com.gooproper.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gooproper.R;
import com.gooproper.admin.fragment.ListingAdminFragment;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainAdminActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private Runnable runnable;
    private static final int POLLING_INTERVAL = 5000;
    String StrStatus;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        if (getIntent().hasExtra("fragment_to_open")) {
            String fragmentToOpen = getIntent().getStringExtra("fragment_to_open");
            if ("pralisting".equals(fragmentToOpen)) {
                openFragment();
            }
        }

        bottomNavigationView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.closingAdmin_nav, R.id.flowUpAdmin_nav, R.id.homeAdmin_nav, R.id.listingAdmin_nav, R.id.akunAdmin_nav).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        ColorStateList colorStateList = ContextCompat.getColorStateList(this, R.color.color_navigation);
        bottomNavigationView.setItemIconTintList(colorStateList);
        bottomNavigationView.setItemTextColor(colorStateList);

        StrStatus = Preferences.getKeyStatus(this);

        if (StrStatus.equals("1")) {
            startBadgeManager(bottomNavigationView);
        } else if (StrStatus.equals("2")) {
            startBadgeAdmin(bottomNavigationView);
            startBadgeAkunAdmin(bottomNavigationView);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void startBadgeAdmin(BottomNavigationView navigationView) {
        runnable = new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainAdminActivity.this);
                JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_PRALISTING_ADMIN, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject data = response.getJSONObject(0);
                                    String count = data.getString("Total");

                                    BadgeDrawable badge = navigationView.getOrCreateBadge(R.id.listingAdmin_nav);
                                    if (count.isEmpty() || count.equals("null") || count.equals("0")) {
                                        badge.setVisible(false);
                                    } else {
                                        badge.setVisible(true);
                                        badge.setNumber(Integer.parseInt(count));
                                        badge.setBadgeTextColor(ContextCompat.getColor(MainAdminActivity.this, android.R.color.white));
                                        badge.setBackgroundColor(ContextCompat.getColor(MainAdminActivity.this, android.R.color.holo_red_dark));
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

                // Repeat the request after a specified interval
                handler.postDelayed(this, POLLING_INTERVAL);
            }
        };

        // Start the first run
        handler.post(runnable);
    }
    private void startBadgeAkunAdmin(BottomNavigationView navigationView) {
        runnable = new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainAdminActivity.this);
                JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_PELAMAR, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject data = response.getJSONObject(0);
                                    String count = data.getString("Total");

                                    BadgeDrawable badge = navigationView.getOrCreateBadge(R.id.akunAdmin_nav);
                                    if (count.isEmpty() || count.equals("null") || count.equals("0")) {
                                        startBadgePendingAdmin(bottomNavigationView);
                                    } else {
                                        badge.setVisible(true);
                                        badge.setBackgroundColor(ContextCompat.getColor(MainAdminActivity.this, android.R.color.holo_red_dark));
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
    private void startBadgePendingAdmin(BottomNavigationView navigationView) {
        runnable = new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainAdminActivity.this);
                JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING_PENDING, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject data = response.getJSONObject(0);
                                    String count = data.getString("Total");

                                    BadgeDrawable badge = navigationView.getOrCreateBadge(R.id.akunAdmin_nav);
                                    if (count.isEmpty() || count.equals("null") || count.equals("0")) {
                                        startBadgeRejectedAdmin(bottomNavigationView);
                                    } else {
                                        badge.setVisible(true);
                                        badge.setBackgroundColor(ContextCompat.getColor(MainAdminActivity.this, android.R.color.holo_red_dark));
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
    private void startBadgeRejectedAdmin(BottomNavigationView navigationView) {
        runnable = new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainAdminActivity.this);
                JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_PRALISTING_REJECTED, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject data = response.getJSONObject(0);
                                    String count = data.getString("Total");

                                    BadgeDrawable badge = navigationView.getOrCreateBadge(R.id.akunAdmin_nav);
                                    if (count.isEmpty() || count.equals("null") || count.equals("0")) {
                                        badge.setVisible(false);
                                    } else {
                                        badge.setVisible(true);
                                        badge.setBackgroundColor(ContextCompat.getColor(MainAdminActivity.this, android.R.color.holo_red_dark));
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
    private void startBadgeManager(BottomNavigationView navigationView) {
        runnable = new Runnable() {
            @Override
            public void run() {
                RequestQueue queue = Volley.newRequestQueue(MainAdminActivity.this);
                JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_PRALISTING_MANAGER, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONObject data = response.getJSONObject(0);
                                    String count = data.getString("Total");

                                    BadgeDrawable badge = navigationView.getOrCreateBadge(R.id.listingAdmin_nav);
                                    if (count.isEmpty() || count.equals("null") || count.equals("0")) {
                                        badge.setVisible(false);
                                    } else {
                                        badge.setVisible(true);
                                        badge.setNumber(Integer.parseInt(count));
                                        badge.setBadgeTextColor(ContextCompat.getColor(MainAdminActivity.this, android.R.color.white));
                                        badge.setBackgroundColor(ContextCompat.getColor(MainAdminActivity.this, android.R.color.holo_red_dark));
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

                // Repeat the request after a specified interval
                handler.postDelayed(this, POLLING_INTERVAL);
            }
        };

        // Start the first run
        handler.post(runnable);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
    private void openFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host, new ListingAdminFragment())
                .commit();
    }
}