package com.gooproper.customer.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.gooproper.adapter.listing.ListingAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.ui.LoginConditionActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListingFragment extends Fragment {

    ProgressDialog PDListingCustomer;
    ImageView IVSortAsc, IVSortDesc, IVFilter;
    SwipeRefreshLayout srlistingcustomer;
    RecyclerView rvlist;
    ListingAdapter adapter;
    List<ListingModel> list;
    String id;
    private AlertDialog alertDialog;
    private EditText searchView;
    private boolean iskondisisewa = false;
    private boolean applyFilters = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listing, container, false);

        id = Preferences.getKeyIdCustomer(getActivity());

        PDListingCustomer = new ProgressDialog(getActivity());
        rvlist = root.findViewById(R.id.RVListingListCustomer);
        srlistingcustomer = root.findViewById(R.id.SRListingCustomer);
        IVSortAsc = root.findViewById(R.id.sortAscendingBtn);
        IVSortDesc = root.findViewById(R.id.sortDescendingBtn);
        IVFilter = root.findViewById(R.id.filterBtn);
        searchView  = root.findViewById(R.id.etsearchView);

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
        IVSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iskondisisewa) {
                    adapter.sortAscendingSewa();
                    IVSortDesc.setVisibility(View.VISIBLE);
                    IVSortAsc.setVisibility(View.GONE);
                } else {
                    adapter.sortAscending();
                    IVSortDesc.setVisibility(View.VISIBLE);
                    IVSortAsc.setVisibility(View.GONE);
                }
            }
        });
        IVSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iskondisisewa) {
                    adapter.sortDescendingSewa();
                    IVSortDesc.setVisibility(View.GONE);
                    IVSortAsc.setVisibility(View.VISIBLE);
                } else {
                    adapter.sortDescending();
                    IVSortDesc.setVisibility(View.GONE);
                    IVSortAsc.setVisibility(View.VISIBLE);
                }
            }
        });
        IVFilter.setOnClickListener(view -> showFilterDialog());

        list = new ArrayList<>();

        LoadListing(true);

        rvlist.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter = new ListingAdapter(getActivity(), list);
        rvlist.setAdapter(adapter);

        srlistingcustomer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadListing(true);
            }
        });

        if (id.isEmpty()){
            startActivity(new Intent(getActivity(), LoginConditionActivity.class));
            getActivity().finish();
        }

        return root;
    }

    private void filterList(String text) {
        List<ListingModel> filteredList = new ArrayList<>();
        for (ListingModel item : list) {
            if (item.getNamaListing().toLowerCase().contains(text.toLowerCase())
                    || item.getAlamat().toLowerCase().contains(text.toLowerCase())
                    || item.getJenisProperti().toLowerCase().contains(text.toLowerCase())
                    || item.getWilayah().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
        }
        adapter.setFilteredlist(filteredList);
    }

    public void showFilterDialog () {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
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

        RadioGroup kondisiRadioGroup = dialogView.findViewById(R.id.kondisi);
        int selectedRadioButtonId = kondisiRadioGroup.getCheckedRadioButtonId();

        String selectedKondisi = "";

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

                String selectedKondisi = "";

                if (selectedRadioButtonId == R.id.jual) {
                    selectedKondisi = "Jual";
                    iskondisisewa = false;
                    Log.d("Debug", "iskondisisewa: " + iskondisisewa);
                } else if (selectedRadioButtonId == R.id.sewa) {
                    selectedKondisi = "Sewa";
                    iskondisisewa = true;
                    Log.d("Debug", "iskondisisewa: " + iskondisisewa);
                } else if (selectedRadioButtonId == R.id.jualsewa) {
                    selectedKondisi = "Jual/Sewa";
                    iskondisisewa = false;
                    Log.d("Debug", "iskondisisewa: " + iskondisisewa);
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
                        Toast.makeText(getActivity(), "Invalid price range", Toast.LENGTH_SHORT).show();
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
                    applyFilters = true;
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
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Jenis Properti");

        final CharSequence[] spec = {"Rumah", "Ruko", "Tanah", "Gudang", "Ruang Usaha", "Villa", "Apartemen", "Pabrik", "Kantor", "Hotel", "Kondotel"};
        final int[] selectedSpecIndex = {0};

        builder.setSingleChoiceItems(spec, selectedSpecIndex[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedSpecIndex[0] = which;
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                TextView textViewSpec = alertDialog.findViewById(R.id.textViewSpec);
                if (textViewSpec != null) {
                    textViewSpec.setText(spec[selectedSpecIndex[0]]);
                }
            }
        });
        builder.create().show();
    }

    public void showPropertyTypePopup(View view) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle("Select Property Type");

        final CharSequence[] options = {"SHM", "HGB", "Lainnya"};
        final int[] selectedOptionIndex = {0};

        builder.setSingleChoiceItems(options, selectedOptionIndex[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedOptionIndex[0] = which;
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
        ArrayList<ListingModel> filteredList = new ArrayList<>();
        for (ListingModel product : list) {
            long productPrice = Long.parseLong(product.getHarga().replace(".", "").trim());
            boolean isPriceMatched          = false;
            boolean isBedMatched            = true;
            boolean isBathMatched           = true;
            boolean isLandWideMatched       = true;
            boolean isBuildingWideMatched   = true;
            boolean isGarageMatched         = true;
            boolean isCarpotMatched         = true;
            boolean isLevelMatched          = true;
            boolean isViewSpecMatched       = true;
            boolean isViewTypeMatched       = true;
            boolean isKondisiMatched        = true;

            if (isAbove) {
                if (productPrice >= minPrice) {
                    isPriceMatched = true;
                }
            } else {
                if (productPrice <= maxPrice) {
                    isPriceMatched = true;
                }
            }

            if (!bedSearch.isEmpty() && !bedSearch.equals(product.getBed())) {
                isBedMatched = false;
            }

            if (!bathSearch.isEmpty() && !bathSearch.equals(product.getBath())) {
                isBathMatched = false;
            }

            if (!landWideSearch.isEmpty() && !landWideSearch.equals(product.getWide())) {
                isLandWideMatched = false;
            }

            if (!buildingWideSearch.isEmpty() && !buildingWideSearch.equals(product.getLand())) {
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

            if (isPriceMatched && isBedMatched && isBathMatched && isLandWideMatched && isBuildingWideMatched && isGarageMatched && isCarpotMatched && isLevelMatched && isViewSpecMatched && isViewTypeMatched && isKondisiMatched) {
                filteredList.add(product);
            }
        }

        adapter.setFilteredlist(filteredList);

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }

        applyFilters = false;
    }

    private void LoadListing(boolean showProgressDialog) {
        PDListingCustomer.setMessage("Memuat Listingan...");
        PDListingCustomer.show();
        if (showProgressDialog) PDListingCustomer.show();
        else PDListingCustomer.cancel();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDListingCustomer.cancel();
                        else srlistingcustomer.setRefreshing(false);
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ListingModel md = new ListingModel();
                                md.setIdListing(data.getString("IdListing"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setIdAgenCo(data.getString("IdAgenCo"));
                                md.setIdInput(data.getString("IdInput"));
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
                                PDListingCustomer.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDListingCustomer.dismiss();

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
                        PDListingCustomer.dismiss();
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