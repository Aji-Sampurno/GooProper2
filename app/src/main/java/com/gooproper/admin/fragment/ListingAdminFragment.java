package com.gooproper.admin.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.gooproper.adapter.ListingAdapter;
import com.gooproper.adapter.PraListingAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.ui.SoldActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListingAdminFragment extends Fragment {

    boolean isGridChanged = false;
    ProgressDialog PDListingAdmin;
    ImageView ivgrid;
    SwipeRefreshLayout srlistingadmin;
    RecyclerView rvgrid, rvlist;
    RecyclerView.Adapter adapter;
    List<ListingModel> list;
    String IsAdmin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listing_admin, container, false);

        PDListingAdmin = new ProgressDialog(getActivity());
        rvlist = root.findViewById(R.id.RVListingListAdmin);
        rvgrid = root.findViewById(R.id.RVListingGridAdmin);
        srlistingadmin = root.findViewById(R.id.SRListingAdmin);
        ivgrid = root.findViewById(R.id.IVGridListingAdmin);

        list = new ArrayList<>();

        IsAdmin = Preferences.getKeyStatus(getActivity());

        if (IsAdmin.equals("2")){
            LoadListingAdmin(true);
        } else if (IsAdmin.equals("1")) {
            LoadListingManager(true);
        }

        rvgrid.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new PraListingAdapter(getActivity(), list);
        rvgrid.setAdapter(adapter);

        /*rvlist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new PraListingAdapter(getActivity(), list);
        rvlist.setAdapter(adapter);*/


        /*ivgrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGridChanged) {
                    AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.crossfade);
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
                    AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity(), R.animator.crossfade);
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
        });*/

        srlistingadmin.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (IsAdmin.equals("2")){
                    LoadListingAdmin(true);
                } else if (IsAdmin.equals("1")) {
                    LoadListingManager(true);
                }
            }
        });

        return root;
    }

    private void LoadListingAdmin(boolean showProgressDialog) {
        PDListingAdmin.setMessage("Memuat Listingan Masuk...");
        PDListingAdmin.show();
        if (showProgressDialog) PDListingAdmin.show();
        else PDListingAdmin.cancel();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PRALISTING_ADMIN, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDListingAdmin.cancel();
                        else srlistingadmin.setRefreshing(false);
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ListingModel md = new ListingModel();
                                md.setIdPraListing(data.getString("IdPraListing"));
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
                                md.setIsAdmin(data.getString("IsAdmin"));
                                md.setIsManager(data.getString("IsManager"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                list.add(md);
                                PDListingAdmin.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDListingAdmin.dismiss();

                                Dialog customDialog = new Dialog(getActivity());
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
                                        LoadListingAdmin(true);
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
                        PDListingAdmin.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(getActivity());
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
                                LoadListingAdmin(true);
                            }
                        });
                        customDialog.show();
                    }
                });
        queue.add(reqData);
    }

    private void LoadListingManager(boolean showProgressDialog) {
        PDListingAdmin.setMessage("Memuat Listingan Masuk...");
        PDListingAdmin.show();
        if (showProgressDialog) PDListingAdmin.show();
        else PDListingAdmin.cancel();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PRALISTING_MANAGER, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDListingAdmin.cancel();
                        else srlistingadmin.setRefreshing(false);
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ListingModel md = new ListingModel();
                                md.setIdPraListing(data.getString("IdPraListing"));
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
                                md.setIsAdmin(data.getString("IsAdmin"));
                                md.setIsManager(data.getString("IsManager"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                list.add(md);
                                PDListingAdmin.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDListingAdmin.dismiss();
                                srlistingadmin.setRefreshing(false);

                                Dialog customDialog = new Dialog(getActivity());
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
                                        LoadListingManager(true);
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
                        PDListingAdmin.dismiss();
                        srlistingadmin.setRefreshing(false);
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(getActivity());
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
                                LoadListingManager(true);
                            }
                        });
                        customDialog.show();
                    }
                });
        queue.add(reqData);
    }
}