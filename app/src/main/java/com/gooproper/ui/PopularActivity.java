package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.ListingSoldAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PopularActivity extends AppCompatActivity {

    boolean isGridChanged = false;
    ProgressDialog PDPopuler;
    ImageView ivgrid;
    SwipeRefreshLayout srpopuler;
    RecyclerView rvgrid, rvlist;
    RecyclerView.Adapter adapter;
    List<ListingModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        PDPopuler = new ProgressDialog(PopularActivity.this);
        ivgrid = findViewById(R.id.IVGridPopuler);
        srpopuler = findViewById(R.id.SRPopuler);
        rvgrid = findViewById(R.id.RVListingGridPopuler);
        rvlist = findViewById(R.id.RVListingListPopuler);

        list = new ArrayList<>();

        rvgrid.setLayoutManager(new GridLayoutManager(PopularActivity.this,2));
        adapter = new ListingSoldAdapter(this,list);
        rvgrid.setAdapter(adapter);

        rvlist.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new ListingSoldAdapter(this,list);
        rvlist.setAdapter(adapter);

        LoadListing(true);

        ivgrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGridChanged) {
                    AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(PopularActivity.this, R.animator.crossfade);
                    animatorSet.setTarget(ivgrid);
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            ivgrid.setImageResource(R.drawable.ic_menu_grid);
                            rvgrid.setVisibility(View.VISIBLE);
                            rvlist.setVisibility(View.GONE);
                        }
                    });
                    animatorSet.start();
                    isGridChanged = false;
                } else {
                    AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(PopularActivity.this, R.animator.crossfade);
                    animatorSet.setTarget(ivgrid);
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            ivgrid.setImageResource(R.drawable.ic_menu_list);
                            rvlist.setVisibility(View.VISIBLE);
                            rvgrid.setVisibility(View.GONE);
                        }
                    });
                    animatorSet.start();
                    isGridChanged = true;
                }
            }
        });

        srpopuler.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadListing(false);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void LoadListing(boolean showProgressDialog) {
        PDPopuler.setMessage("Memuat Data Listig Sold...");
        PDPopuler.show();
        if (showProgressDialog) PDPopuler.show();
        else PDPopuler.cancel();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_HOT,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDPopuler.cancel();
                        else srpopuler.setRefreshing(false);
                        list.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ListingModel md = new ListingModel();
                                md.setIdListing(data.getString("IdListing"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setIdInput(data.getString("IdInput"));
                                md.setIdVendor(data.getString("IdVendor"));
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setLocation(data.getString("Location"));
                                md.setWide(data.getString("Wide"));
                                md.setLevel(data.getString("Level"));
                                md.setBed(data.getString("Bed"));
                                md.setBath(data.getString("Bath"));
                                md.setBedArt(data.getString("BedArt"));
                                md.setBathArt(data.getString("BathArt"));
                                md.setGarage(data.getString("Garage"));
                                md.setCarpot(data.getString("Carpot"));
                                md.setNoCertificate(data.getString("NoCertificate"));
                                md.setPbb(data.getString("Pbb"));
                                md.setJenisProperti(data.getString("JenisProperti"));
                                md.setJenisCertificate(data.getString("JenisCertificate"));
                                md.setSumberAir(data.getString("SumberAir"));
                                md.setKondisi(data.getString("Kondisi"));
                                md.setDeskripsi(data.getString("Deskripsi"));
                                md.setPrabot(data.getString("Prabot"));
                                md.setKetPrabot(data.getString("KetPrabot"));
                                md.setPriority(data.getString("Priority"));
                                md.setTtd(data.getString("Ttd"));
                                md.setBanner(data.getString("Banner"));
                                md.setHarga(data.getString("Harga"));
                                md.setTglInput(data.getString("TglInput"));
                                md.setImg1(data.getString("Img1"));
                                md.setImg2(data.getString("Img2"));
                                md.setImg3(data.getString("Img3"));
                                md.setImg4(data.getString("Img4"));
                                md.setImg5(data.getString("Img5"));
                                md.setImg6(data.getString("Img6"));
                                md.setImg7(data.getString("Img7"));
                                md.setImg8(data.getString("Img8"));
                                md.setVideo(data.getString("Video"));
                                md.setLinkFacebook(data.getString("LinkFacebook"));
                                md.setLinkTiktok(data.getString("LinkTiktok"));
                                md.setLinkInstagram(data.getString("LinkInstagram"));
                                md.setLinkYoutube(data.getString("LinkYoutube"));
                                md.setView(data.getString("View"));
                                md.setSold(data.getString("Sold"));
                                list.add(md);
                                PDPopuler.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDPopuler.dismiss();

                                Dialog customDialog = new Dialog(PopularActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.alert_eror);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                Button ok = customDialog.findViewById(R.id.BTNOkEror);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                        LoadListing(true);
                                    }
                                });

                                customDialog.show();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PDPopuler.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(PopularActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.alert_eror);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BTNOkEror);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                                LoadListing(true);
                            }
                        });

                        customDialog.show();
                    }
                });

        queue.add(reqData);
    }
}