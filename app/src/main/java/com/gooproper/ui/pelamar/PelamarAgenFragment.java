package com.gooproper.ui.pelamar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.list.ListPelamarAgenAdapter;
import com.gooproper.model.AgenModel;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PelamarAgenFragment extends Fragment {

    ProgressDialog PDPelamarAgen;
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<AgenModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_pelamar_agen, container, false);

        recyclerView = root.findViewById(R.id.RVPelamarAgenList);
        refreshLayout = root.findViewById(R.id.SRLPelamarAgen);

        PDPelamarAgen = new ProgressDialog(getActivity());
        list = new ArrayList<>();
        adapter = new ListPelamarAgenAdapter(getActivity(), list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        LoadPelamarAgen(true);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadPelamarAgen(true);
            }
        });

        return root;
    }

    private void LoadPelamarAgen(boolean showProgressDialog) {
        PDPelamarAgen.setMessage("Memuat Data Pelamar...");
        PDPelamarAgen.show();
        if (showProgressDialog) PDPelamarAgen.show();
        else PDPelamarAgen.cancel();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PELAMAR_AGEN, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDPelamarAgen.cancel();
                        else refreshLayout.setRefreshing(false);
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                AgenModel agenModel = new AgenModel();
                                agenModel.setIdAgen(data.getString("IdAgen"));
                                agenModel.setUsername(data.getString("Username"));
                                agenModel.setPassword(data.getString("Password"));
                                agenModel.setNama(data.getString("Nama"));
                                agenModel.setNoTelp(data.getString("NoTelp"));
                                agenModel.setEmail(data.getString("Email"));
                                agenModel.setTglLahir(data.getString("TglLahir"));
                                agenModel.setKotaKelahiran(data.getString("KotaKelahiran"));
                                agenModel.setPendidikan(data.getString("Pendidikan"));
                                agenModel.setNamaSekolah(data.getString("NamaSekolah"));
                                agenModel.setMasaKerja(data.getString("MasaKerja"));
                                agenModel.setJabatan(data.getString("Jabatan"));
                                agenModel.setStatus(data.getString("Status"));
                                agenModel.setAlamatDomisili(data.getString("AlamatDomisili"));
                                agenModel.setFacebook(data.getString("Facebook"));
                                agenModel.setInstagram (data.getString("Instagram"));
                                agenModel.setNoKtp(data.getString("NoKtp"));
                                agenModel.setImgKtp(data.getString("ImgKtp"));
                                agenModel.setImgTtd(data.getString("ImgTtd"));
                                agenModel.setNpwp (data.getString("Npwp "));
                                agenModel.setPhoto(data.getString("Photo"));
                                agenModel.setPoin(data.getString("Poin "));
                                agenModel.setIsAkses(data.getString("IsAkses"));
                                list.add(agenModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDPelamarAgen.dismiss();

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
                                        LoadPelamarAgen(true);
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
                        PDPelamarAgen.dismiss();
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
                                LoadPelamarAgen(true);
                            }
                        });
                        customDialog.show();
                    }
                });
        queue.add(request);
    }

}