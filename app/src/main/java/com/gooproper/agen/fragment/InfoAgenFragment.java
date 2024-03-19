package com.gooproper.agen.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.listing.InfoAdapter;
import com.gooproper.model.InfoModel;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InfoAgenFragment extends Fragment {

    ProgressDialog PDInfoAgen;
    SwipeRefreshLayout SRInfoAgen;
    RecyclerView RVList;
    InfoAdapter adapter;
    List<InfoModel> list;
    String id;
    private AlertDialog alertDialog;
    private EditText searchView;
    private boolean applyFilters = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_info_agen, container, false);

        PDInfoAgen = new ProgressDialog(getActivity());
        RVList = root.findViewById(R.id.RVInfoListAgen);
        SRInfoAgen = root.findViewById(R.id.SRInfoAgen);
        searchView = root.findViewById(R.id.etsearchView);

        searchView.clearFocus();
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                filterList(newText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        list = new ArrayList<>();

        LoadListing(true);

        RVList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new InfoAdapter(getActivity(), list);
        RVList.setAdapter(adapter);

        SRInfoAgen.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadListing(true);
            }
        });

        return root;
    }

    //searchView
    private void filterList(String text) {
        List<InfoModel> filteredList = new ArrayList<>();
        for (InfoModel item : list) {
            if (item.getJenisProperty().toLowerCase().contains(text.toLowerCase())
                    || item.getAlamat().toLowerCase().contains(text.toLowerCase())
                    || item.getStatusProperty().toLowerCase().contains(text.toLowerCase())
                    || item.getLokasi().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "Tidak Ditemukan", Toast.LENGTH_SHORT).show();
        }
        adapter.setFilteredlist(filteredList);
    }

    private void LoadListing(boolean showProgressDialog) {
        PDInfoAgen.setMessage("Memuat Listingan...");
        PDInfoAgen.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_INFO, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDInfoAgen.cancel();
                        SRInfoAgen.setRefreshing(false);
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                InfoModel md = new InfoModel();
                                md.setIdInfo(data.getString("IdInfo"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setJenisProperty(data.getString("JenisProperty"));
                                md.setStatusProperty(data.getString("StatusProperty"));
                                md.setNarahubung(data.getString("Narahubung"));
                                md.setImgSelfie(data.getString("ImgSelfie"));
                                md.setImgProperty(data.getString("ImgProperty"));
                                md.setLokasi(data.getString("Lokasi"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setTglInput(data.getString("TglInput"));
                                md.setJamInput(data.getString("JamInput"));
                                md.setIsListing(data.getString("IsListing"));
                                md.setLBangunan(data.getString("LBangunan"));
                                md.setLTanah(data.getString("LTanah"));
                                md.setHarga(data.getString("Harga"));
                                md.setHargaSewa(data.getString("HargaSewa"));
                                md.setKeterangan(data.getString("Keterangan"));
                                md.setIsSpek(data.getString("IsSpek"));
                                list.add(md);
                                PDInfoAgen.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDInfoAgen.dismiss();
                                SRInfoAgen.setRefreshing(false);

                                Dialog customDialog = new Dialog(getActivity());
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.alert_eror);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                Button ok = customDialog.findViewById(R.id.BTNOkEror);
                                Button batal = customDialog.findViewById(R.id.BTNCloseEror);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                        LoadListing(true);
                                    }
                                });

                                batal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
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
                        PDInfoAgen.dismiss();
                        SRInfoAgen.setRefreshing(false);
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(getActivity());
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.alert_eror);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BTNOkEror);
                        Button batal = customDialog.findViewById(R.id.BTNCloseEror);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                                LoadListing(true);
                            }
                        });

                        batal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });
                        customDialog.show();
                    }
                });
        queue.add(reqData);
    }
}