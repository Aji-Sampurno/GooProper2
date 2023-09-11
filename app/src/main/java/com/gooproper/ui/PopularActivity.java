package com.gooproper.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.ListingPopulerAdapter;
import com.gooproper.adapter.ListingSoldAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PopularActivity extends AppCompatActivity {

    ProgressDialog PDPopuler;
    SwipeRefreshLayout srpopuler;
    RecyclerView rvgrid;
    ListingPopulerAdapter adapter;
    List<ListingModel> list;
    private AlertDialog alertDialog;
    private SearchView searchView;
    private boolean applyFilters = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        searchView  = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

        PDPopuler = new ProgressDialog(PopularActivity.this);
        srpopuler = findViewById(R.id.SRPopuler);
        rvgrid = findViewById(R.id.RVListingGridPopuler);

        list = new ArrayList<>();

        rvgrid.setLayoutManager(new GridLayoutManager(PopularActivity.this,2));
        adapter = new ListingPopulerAdapter(this,list);
        rvgrid.setAdapter(adapter);

        LoadListing(true);

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

    //searchView
    private void filterList(String text) {
        List<ListingModel> filteredList = new ArrayList<>();
        for (ListingModel item : list) {
            if (item.getAlamat().toLowerCase().contains(text.toLowerCase())
                    || item.getJenisProperti().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        }
        adapter.setFilteredlist(filteredList);
    }

    //filter
    public void showFilterDialog (View view) {
        androidx.appcompat.app.AlertDialog.Builder dialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.filter, null);
        dialogBuilder.setView(dialogView);

        EditText editTextMinPrice               = dialogView.findViewById(R.id.editTextMinPrice);
        EditText editTextMaxPrice               = dialogView.findViewById(R.id.editTextMaxPrice);
        EditText bedSearchEditText              = dialogView.findViewById(R.id.BedSearch);
        EditText bathSearchEditText             = dialogView.findViewById(R.id.BathSearch);
        EditText landWideSearchEditText         = dialogView.findViewById(R.id.LandWideSearch);
        EditText buildingWideSearchEditText     = dialogView.findViewById(R.id.BuildingWideSearch);
        EditText garageSearchEditText           = dialogView.findViewById(R.id.garageSearch);
        EditText carpotSearchEditText           = dialogView.findViewById(R.id.carpotSearch);
        EditText levelSearchEditText            = dialogView.findViewById(R.id.levelSearch);
        EditText textViewPropertyTypeText       = dialogView.findViewById(R.id.textViewPropertyType);
        EditText textViewSpec                   = dialogView.findViewById(R.id.textViewSpec);

        RadioGroup kondisiRadioGroup = alertDialog.findViewById(R.id.kondisi);
        int selectedRadioButtonId = kondisiRadioGroup.getCheckedRadioButtonId();

        String selectedKondisi = ""; // Initialize the selected kondisi

        if (selectedRadioButtonId == R.id.jual) {
            selectedKondisi = "Jual";
        } else if (selectedRadioButtonId == R.id.sewa) {
            selectedKondisi = "Sewa";
        }

        textViewSpec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpecPopUp(v);
            }
        });

        textViewPropertyTypeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPropertyTypePopup(v);
            }
        });

        Button btnApply = dialogView.findViewById(R.id.btnApply);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minPriceStr              = editTextMinPrice.getText().toString();
                String maxPriceStr              = editTextMaxPrice.getText().toString();
                String bedSearchStr             = bedSearchEditText.getText().toString();
                String bathSearchStr            = bathSearchEditText.getText().toString();
                String landWideSearchStr        = landWideSearchEditText.getText().toString();
                String buildingWideSearchStr    = buildingWideSearchEditText.getText().toString();
                String garageSearchStr          = garageSearchEditText.getText().toString();
                String carpotSearchStr          = carpotSearchEditText.getText().toString();
                String levelSearchStr           = levelSearchEditText.getText().toString();
                String specStr                  = textViewSpec.getText().toString();
                String typeStr                  = textViewPropertyTypeText.getText().toString();

                RadioGroup kondisiRadioGroup = alertDialog.findViewById(R.id.kondisi);
                int selectedRadioButtonId = kondisiRadioGroup.getCheckedRadioButtonId();

                String selectedKondisi = ""; // Initialize the selected kondisi

                if (selectedRadioButtonId == R.id.jual) {
                    selectedKondisi = "Jual";
                } else if (selectedRadioButtonId == R.id.sewa) {
                    selectedKondisi = "Sewa";
                }

                long minPrice = 0;
                if (!minPriceStr.isEmpty()) {
                    minPrice = Long.parseLong(minPriceStr.replace(".", "").trim());
                }

                long maxPrice = 0;
                if (!maxPriceStr.isEmpty()) {
                    maxPrice = Long.parseLong(maxPriceStr.replace(".", "").trim());
                }

                String bedSearch = String.valueOf(0);
                if (!bedSearchStr.isEmpty()) {
                    bedSearch = bedSearchStr;
                }

                String bathSearch = String.valueOf(0);
                if (!bathSearchStr.isEmpty()) {
                    bathSearch = bathSearchStr;
                }

                String landWideSearch = String.valueOf(0);
                if (!landWideSearchStr.isEmpty()) {
                    landWideSearch = landWideSearchStr;
                }

                String buildingWideSearch = String.valueOf(0);
                if (!buildingWideSearchStr.isEmpty()) {
                    buildingWideSearch = buildingWideSearchStr;
                }

                String garageSearch = String.valueOf(0);
                if (!garageSearchStr.isEmpty()){
                    garageSearch = garageSearchStr;
                }

                String carpotSearch = String.valueOf(0);
                if (!carpotSearchStr.isEmpty()){
                    carpotSearch = carpotSearchStr;
                }

                String levelSearch = String.valueOf(0);
                if (!levelSearchStr.isEmpty()){
                    levelSearch = levelSearchStr;
                }

                String viewSpec = String.valueOf(0);
                if (!specStr.isEmpty()){
                    viewSpec = specStr;
                }

                String viewType = String.valueOf(0);
                if (!typeStr.isEmpty()){
                    viewType = typeStr;
                }

                if (!minPriceStr.isEmpty() && !maxPriceStr.isEmpty()) {
                    if (minPrice <= maxPrice) {
                        applyCustomFilter(v, minPrice, maxPrice, true, bedSearchStr, bathSearchStr, landWideSearchStr, buildingWideSearchStr, garageSearchStr, carpotSearchStr, levelSearchStr, specStr, typeStr, selectedKondisi);
                    } else {
                        // Handle invalid input, show a message or toast if needed
                        Toast.makeText(PopularActivity.this, "Invalid price range", Toast.LENGTH_SHORT).show();
                    }
                } else if (!minPriceStr.isEmpty()) {
                    applyCustomFilter(v, minPrice, maxPrice, true, bedSearchStr, bathSearchStr, landWideSearchStr, buildingWideSearchStr, garageSearchStr, carpotSearchStr, levelSearchStr, specStr, typeStr, selectedKondisi);
                } else if (!maxPriceStr.isEmpty()) {
                    applyCustomFilter(v, minPrice, maxPrice, true, bedSearchStr, bathSearchStr, landWideSearchStr, buildingWideSearchStr, garageSearchStr, carpotSearchStr, levelSearchStr, specStr, typeStr, selectedKondisi);
                } else if (!bedSearchStr.isEmpty()) {
                    applyCustomFilter(v, 0, 0, true, bedSearchStr, bathSearchStr, landWideSearchStr, buildingWideSearchStr, garageSearchStr, carpotSearchStr, levelSearchStr, specStr, typeStr, selectedKondisi);
                } else if (!bathSearchStr.isEmpty()) {
                    applyCustomFilter(v, 0, 0, true, bedSearchStr, bathSearchStr, landWideSearchStr, buildingWideSearchStr, garageSearchStr, carpotSearchStr, levelSearchStr, specStr, typeStr, selectedKondisi);
                } else if(!landWideSearchStr.isEmpty()){
                    applyCustomFilter(v, 0, 0, true, bedSearchStr, bathSearchStr, landWideSearchStr, buildingWideSearchStr, garageSearchStr, carpotSearchStr, levelSearchStr, specStr, typeStr, selectedKondisi);
                } else if (!buildingWideSearchStr.isEmpty()) {
                    applyCustomFilter(v, 0, 0, true, bedSearchStr, bathSearchStr, landWideSearchStr, buildingWideSearchStr, garageSearchStr, carpotSearchStr, levelSearchStr, specStr, typeStr, selectedKondisi);
                } else if (!specStr.isEmpty()) {
                    applyCustomFilter(v, 0, 0, true, bedSearchStr, bathSearchStr, landWideSearchStr, buildingWideSearchStr, garageSearchStr, carpotSearchStr, levelSearchStr, specStr, typeStr, selectedKondisi);
                } else if (!typeStr.isEmpty()) {
                    applyCustomFilter(v, 0, 0, true, bedSearchStr, bathSearchStr, landWideSearchStr, buildingWideSearchStr, garageSearchStr, carpotSearchStr, levelSearchStr, specStr, typeStr, selectedKondisi);
                } else {
                    applyFilters = true; // Apply filters
                    adapter.setFilteredlist(list);
                }
            }
        });

        Button btnClear = dialogView.findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter.resetFilter();

                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    public void showSpecPopUp(View view) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Jenis Properti");

        final CharSequence[] spec = {"Rumah", "Ruko", "Tanah", "Gudang", "Ruang Usaha", "Villa", "Apartemen", "Pabrik", "Kantor", "Hotel", "Kondohotel"};
        final int[] selectedSpecIndex = {0}; // to store the index of the selected property type

        builder.setSingleChoiceItems(spec, selectedSpecIndex[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedSpecIndex[0] = which; // update the selected property type index
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the TextView with the selected property type
                TextView textViewSpec = alertDialog.findViewById(R.id.textViewSpec);
                if (textViewSpec != null) {
                    textViewSpec.setText(spec[selectedSpecIndex[0]]);
                }
            }
        });
        // Create and show the AlertDialog
        builder.create().show();
    }

    public void showPropertyTypePopup(View view) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Select Property Type");

        final CharSequence[] options = {"SHM", "HGB", "Lainnya"};
        final int[] selectedOptionIndex = {0};

        builder.setSingleChoiceItems(options, selectedOptionIndex[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedOptionIndex[0] = which; // Assign the selected index to the array
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TextView textViewPropertyType = alertDialog.findViewById(R.id.textViewPropertyType);
                if (textViewPropertyType != null) {
                    textViewPropertyType.setText(options[selectedOptionIndex[0]]);
                }
            }
        });

        androidx.appcompat.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void applyCustomFilter(View view,
                                  long minPrice,
                                  long maxPrice,
                                  boolean isAbove,
                                  String bedSearch,
                                  String bathSearch,
                                  String landWideSearch,
                                  String buildingWideSearch,
                                  String garageSearch,
                                  String carpotSearch,
                                  String levelSearch,
                                  String viewSpec,
                                  String viewType,
                                  String kondisi) {
        //applyFilters = true; // Set the boolean to true before applying filters
        ArrayList<ListingModel> filteredList = new ArrayList<>();
        for (ListingModel product : list) {
            long productPrice = Long.parseLong(product.getHarga().replace(".", "").trim());
            boolean isPriceMatched          = false;
            boolean isBedMatched            = true; // Assume bed is matched unless bedSearch is not empty and not equal to the listing's bed value
            boolean isBathMatched           = true; // Assume bath is matched unless bathSearch is not empty and not equal to the listing's bath value
            boolean isLandWideMatched       = true; // Assume landwide is matched unless landWideSearch is not empty and not equal to the listing's landwide value
            boolean isBuildingWideMatched   = true; // Assume buildingwide is matched unless buildingWideSearch is not empty and not equal to the listing's buildingwide value
            boolean isGarageMatched         = true;
            boolean isCarpotMatched         = true;
            boolean isLevelMatched          = true;
            boolean isViewSpecMatched       = true; //tipe hunian
            boolean isViewTypeMatched       = true;
            boolean isKondisiMatched        = true; //kondisi

            if (isAbove) {
                if (productPrice >= minPrice) {
                    isPriceMatched = true;
                }
            } else {
                if (productPrice <= maxPrice) {
                    isPriceMatched = true;
                }
            }

            // Check if bedSearch is not empty and does not match the listing's bed value
            if (!bedSearch.isEmpty() && !bedSearch.equals(product.getBed())) {
                isBedMatched = false;
            }

            // Check if bathSearch is not empty and does not match the listing's bath value
            if (!bathSearch.isEmpty() && !bathSearch.equals(product.getBath())) {
                isBathMatched = false;
            }

            // Check if landWideSearch is not empty and does not match the listing's landwide value
            if (!landWideSearch.isEmpty() && !landWideSearch.equalsIgnoreCase(product.getWide())) {
                isLandWideMatched = false;
            }

            // Check if buildingWideSearch is not empty and does not match the listing's buildingwide value
            if (!buildingWideSearch.isEmpty() && !buildingWideSearch.equalsIgnoreCase(product.getLand())) {
                isBuildingWideMatched = false;
            }

            if (!garageSearch.isEmpty() && !garageSearch.equals(product.getGarage())) {
                isGarageMatched = false;
            }

            if (!carpotSearch.isEmpty() && !carpotSearch.equals(product.getCarpot())){
                isCarpotMatched = false;
            }

            if (!levelSearch.isEmpty() && !levelSearch.equals(product.getLevel())){
                isLevelMatched = false;
            }

            if (!viewSpec.isEmpty() && !viewSpec.equalsIgnoreCase(product.getJenisProperti())) {
                isViewSpecMatched = false;
            }

            if (!viewType.isEmpty() && !viewType.equalsIgnoreCase(product.getJenisCertificate())) {
                isViewTypeMatched = false;
            }

            if (!kondisi.isEmpty() && !kondisi.equals(product.getKondisi())) {
                isKondisiMatched = false;
            }

            // Check other filter criteria and include the listing if all conditions are met
            if (isPriceMatched && isBedMatched && isBathMatched && isLandWideMatched && isBuildingWideMatched && isGarageMatched && isCarpotMatched && isLevelMatched && isViewSpecMatched && isViewTypeMatched && isKondisiMatched) {
                filteredList.add(product);
            }
        }

        adapter.setFilteredlist(filteredList);

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }

        applyFilters = false; // Clear the filter flag after applying filters
    }

    private void LoadListing(boolean showProgressDialog) {
        PDPopuler.setMessage("Memuat Data...");
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
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLocation(data.getString("Location"));
                                md.setWide(data.getString("Wide"));
                                md.setLand(data.getString("Land"));
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
                                md.setImgSHM(data.getString("ImgSHM"));
                                md.setImgHGB(data.getString("ImgHGB"));
                                md.setImgHSHP(data.getString("ImgHSHP"));
                                md.setImgPPJB(data.getString("ImgPPJB"));
                                md.setImgStratatitle(data.getString("ImgStratatitle"));
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
                                md.setSold(data.getString("Sold"));
                                md.setView(data.getString("View"));
                                md.setMarketable(data.getString("Marketable"));
                                md.setStatusHarga(data.getString("StatusHarga"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
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
                                Button batal = customDialog.findViewById(R.id.BTNCloseEror);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LoadListing(true);
                                    }
                                });

                                batal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
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
                        PDPopuler.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(PopularActivity.this);
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
                                LoadListing(true);
                            }
                        });

                        batal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                            }
                        });

                        customDialog.show();
                    }
                });

        queue.add(reqData);
    }
}