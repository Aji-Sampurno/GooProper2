package com.gooproper.ui.listing;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.listing.ListingAdapter;
import com.gooproper.adapter.listing.ListingPendingAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListingPendingActivity extends AppCompatActivity {

    ProgressDialog PDPending;
    SwipeRefreshLayout SRPending;
    RecyclerView RVPending;
    ListingPendingAdapter adapter;
    List<ListingModel> list;
    String StringStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_pending);

        PDPending = new ProgressDialog(ListingPendingActivity.this);
        RVPending = findViewById(R.id.RVPending);
        SRPending = findViewById(R.id.SRPending);

        list = new ArrayList<>();
        StringStatus = Preferences.getKeyStatus(ListingPendingActivity.this);

        LoadListing(true);

        RVPending.setLayoutManager(new LinearLayoutManager(ListingPendingActivity.this,LinearLayoutManager.VERTICAL,false));
        adapter = new ListingPendingAdapter(ListingPendingActivity.this, list);
        RVPending.setAdapter(adapter);

        SRPending.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadListing(true);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void LoadListing(boolean showProgressDialog) {
        PDPending.setMessage("Memuat Listingan...");
        PDPending.show();
        if (showProgressDialog) PDPending.show();
        else PDPending.cancel();

        RequestQueue queue = Volley.newRequestQueue(ListingPendingActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_PENDING, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDPending.cancel();
                        SRPending.setRefreshing(false);
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ListingModel md = new ListingModel();
                                md.setIdListing(data.getString("IdListing"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setIdAgenCo(data.getString("IdAgenCo"));
                                md.setIdInput(data.getString("IdInput"));
                                md.setNoArsip(data.getString("NoArsip"));
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setAlamatTemplate(data.getString("AlamatTemplate"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLocation(data.getString("Location"));
                                md.setWilayah(data.getString("Wilayah"));
                                md.setSelfie(data.getString("Selfie"));
                                md.setWide(data.getString("Wide"));
                                md.setLand(data.getString("Land"));
                                md.setDimensi(data.getString("Dimensi"));
                                md.setListrik(data.getString("Listrik"));
                                md.setLevel(data.getString("Level"));
                                md.setBed(data.getString("Bed"));
                                md.setBath(data.getString("Bath"));
                                md.setBedArt(data.getString("BedArt"));
                                md.setBathArt(data.getString("BathArt"));
                                md.setGarage(data.getString("Garage"));
                                md.setCarpot(data.getString("Carpot"));
                                md.setHadap(data.getString("Hadap"));
                                md.setSHM(data.getString("SHM"));
                                md.setHGB(data.getString("HGB"));
                                md.setHSHP(data.getString("HSHP"));
                                md.setPPJB(data.getString("PPJB"));
                                md.setStratatitle(data.getString("Stratatitle"));
                                md.setAJB(data.getString("AJB"));
                                md.setPetokD(data.getString("PetokD"));
                                md.setPjp(data.getString("Pjp"));
                                md.setImgSHM(data.getString("ImgSHM"));
                                md.setImgHGB(data.getString("ImgHGB"));
                                md.setImgHSHP(data.getString("ImgHSHP"));
                                md.setImgPPJB(data.getString("ImgPPJB"));
                                md.setImgStratatitle(data.getString("ImgStratatitle"));
                                md.setImgAJB(data.getString("ImgAJB"));
                                md.setImgPetokD(data.getString("ImgPetokD"));
                                md.setImgPjp(data.getString("ImgPjp"));
                                md.setImgPjp1(data.getString("ImgPjp1"));
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
                                md.setSize(data.getString("Size"));
                                String HargaJualData = (data.getString("Harga"));
                                if (HargaJualData.isEmpty()) {
                                    md.setHarga("0");
                                } else {
                                    md.setHarga(HargaJualData);
                                }
                                String HargaSewaData = (data.getString("HargaSewa"));
                                if (HargaSewaData.isEmpty()) {
                                    md.setHargaSewa("0");
                                } else {
                                    md.setHargaSewa(HargaSewaData);
                                }
                                md.setRangeHarga(data.getString("RangeHarga"));
                                md.setTglInput(data.getString("TglInput"));
                                md.setImg1(data.getString("Img1"));
                                md.setImg2(data.getString("Img2"));
                                md.setImg3(data.getString("Img3"));
                                md.setImg4(data.getString("Img4"));
                                md.setImg5(data.getString("Img5"));
                                md.setImg6(data.getString("Img6"));
                                md.setImg7(data.getString("Img7"));
                                md.setImg8(data.getString("Img8"));
                                md.setImg9(data.getString("Img9"));
                                md.setImg10(data.getString("Img10"));
                                md.setImg11(data.getString("Img11"));
                                md.setImg12(data.getString("Img12"));
                                md.setVideo(data.getString("Video"));
                                md.setLinkFacebook(data.getString("LinkFacebook"));
                                md.setLinkTiktok(data.getString("LinkTiktok"));
                                md.setLinkInstagram(data.getString("LinkInstagram"));
                                md.setLinkYoutube(data.getString("LinkYoutube"));
                                md.setIsAdmin(data.getString("IsAdmin"));
                                md.setIsManager(data.getString("IsManager"));
                                md.setIsRejected(data.getString("IsRejected"));
                                md.setSold(data.getString("Sold"));
                                md.setRented(data.getString("Rented"));
                                md.setSoldAgen(data.getString("SoldAgen"));
                                md.setRentedAgen(data.getString("RentedAgen"));
                                md.setView(data.getString("View"));
                                md.setMarketable(data.getString("Marketable"));
                                md.setStatusHarga(data.getString("StatusHarga"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                md.setFee(data.getString("Fee"));
                                md.setNamaVendor(data.getString("NamaVendor"));
                                md.setNoTelpVendor(data.getString("NoTelpVendor"));
                                md.setIsSelfie(data.getString("IsSelfie"));
                                md.setIsLokasi(data.getString("IsLokasi"));
                                md.setIdTemplate(data.getString("IdTemplate"));
                                md.setTemplate(data.getString("Template"));
                                md.setTemplateBlank(data.getString("TemplateBlank"));
                                list.add(md);
                                PDPending.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDPending.dismiss();
                                SRPending.setRefreshing(false);

                                Dialog customDialog = new Dialog(ListingPendingActivity.this);
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
                        PDPending.dismiss();
                        SRPending.setRefreshing(false);
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(ListingPendingActivity.this);
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