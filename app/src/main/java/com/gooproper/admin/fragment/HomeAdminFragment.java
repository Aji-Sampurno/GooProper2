package com.gooproper.admin.fragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gooproper.R;
import com.gooproper.adapter.ListingAdapter;
import com.gooproper.adapter.ListingPopulerAdapter;
import com.gooproper.adapter.ListingSoldAdapter;
import com.gooproper.adapter.PrimaryAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.model.PrimaryModel;
import com.gooproper.ui.MapsActivity;
import com.gooproper.ui.MapsFragment;
import com.gooproper.ui.NewActivity;
import com.gooproper.ui.PopularActivity;
import com.gooproper.ui.PrimaryActivity;
import com.gooproper.ui.SoldActivity;
import com.gooproper.ui.TambahListingActivity;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.PreferencesDevice;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HomeAdminFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final int MY_PERMISSIONS_REQUEST_VIBRATE = 123;
    private static final int REQUEST_CODE_UPDATE = 2;
    private int currentPosition = 0;
    private MapView mapView;
    private GoogleMap googleMap;
    private TextView SeeAllPrimary, SeeAllNew, SeeAllPopular, SeeAllSold, SeeAllAgentOM;
    private RecyclerView recycleListingSold, recycleListingNew, recycleListingPopular, recycleListingPrimary, recycleAgent;
    private RecyclerView.Adapter adapterSold, adapterNew, adapterPopular, adapterPrimary, adapterAgentOM;
    String Token, Status, IdAdmin;
    List<PrimaryModel> mItemsPrimary;
    List<ListingModel> mItemsSold;
    List<ListingModel> mItemsHot;
    List<ListingModel> mItemsNew;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_admin, container, false);

        checkForUpdate();

        IdAdmin = Preferences.getKeyIdAdmin(getContext());
        Status = Preferences.getKeyStatus(getContext());

        requestNotificationPermission();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("1","notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = requireContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        return;
                    }
                    Token = task.getResult();
                    Toast.makeText(getContext(), "Selamat Datang" + Token, Toast.LENGTH_SHORT);
                    simpanDevice();
                });

        recycleListingPrimary = root.findViewById(R.id.ListingPrimary);
        recycleListingSold = root.findViewById(R.id.ListingSold);
        recycleListingNew = root.findViewById(R.id.ListingNew);
        recycleListingPopular = root.findViewById(R.id.ListingPopular);
        recycleAgent = root.findViewById(R.id.ListingAgentOM);
        SeeAllPrimary = root.findViewById(R.id.SeeAllPrimary);
        SeeAllSold = root.findViewById(R.id.SeeAllSold);
        SeeAllNew = root.findViewById(R.id.SeeAllNew);
        SeeAllPopular = root.findViewById(R.id.SeeAllPopular);
        mapView = root.findViewById(R.id.MVHomeAdmin);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        SeeAllPrimary.setOnClickListener(view -> startActivity(new Intent(getContext(), PrimaryActivity.class)));
        SeeAllSold.setOnClickListener(view -> startActivity(new Intent(getContext(), SoldActivity.class)));
        SeeAllNew.setOnClickListener(view -> startActivity(new Intent(getContext(), NewActivity.class)));
        SeeAllPopular.setOnClickListener(view -> startActivity(new Intent(getContext(), PopularActivity.class)));

        mItemsPrimary = new ArrayList<>();
        mItemsSold = new ArrayList<>();
        mItemsHot = new ArrayList<>();
        mItemsNew = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleListingPrimary.setLayoutManager(layoutManager);
        adapterPrimary = new PrimaryAdapter(getActivity(), mItemsPrimary);
        recycleListingPrimary.setAdapter(adapterPrimary);

        recycleListingSold.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterSold = new ListingSoldAdapter(getActivity(), mItemsSold);
        recycleListingSold.setAdapter(adapterSold);

        recycleListingPopular.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterPopular = new ListingPopulerAdapter(getActivity(), mItemsHot);
        recycleListingPopular.setAdapter(adapterPopular);

        recycleListingNew.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        adapterNew = new ListingAdapter(getActivity(), mItemsNew);
        recycleListingNew.setAdapter(adapterNew);

        LoadListingPrimary();
        LoadListingSold();
        LoadListingPopuler();
        LoadListing();
        LoadDevice();

        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recycleListingPrimary);

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPosition == mItemsPrimary.size()) {
                    currentPosition = 0;
                } else {
                    currentPosition++;
                }
                recycleListingPrimary.smoothScrollToPosition(currentPosition);
                recycleListingPrimary.postDelayed(this, 3000); // Post the runnable again to keep scrolling
            }
        };

        recycleListingPrimary.post(runnable);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        return root;
    }
    private void checkForUpdate() {
        AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(getActivity());

        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new com.google.android.play.core.tasks.OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                    // Ada pembaruan tersedia, tampilkan dialog pembaruan
                    requestUpdate(appUpdateManager, appUpdateInfo);
                }
            }
        });

        appUpdateInfoTask.addOnFailureListener(new com.google.android.play.core.tasks.OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Gagal memeriksa pembaruan", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void requestUpdate(AppUpdateManager appUpdateManager, AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, getActivity(), REQUEST_CODE_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_UPDATE) {
            if (resultCode != RESULT_OK) {
                Toast.makeText(getActivity(), "Pembaruan dibatalkan atau gagal", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void requestNotificationPermission() {
        if (!NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()) {
            Dialog customDialog = new Dialog(getContext());
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_sukses);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
            Button ok = customDialog.findViewById(R.id.btnya);
            Button cobalagi = customDialog.findViewById(R.id.btntidak);
            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

            dialogTitle.setText("Berikan Izin Notifikasi");
            cobalagi.setText("Tidak");
            ok.setText("Ya");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openNotificationSettings();
                }
            });

            cobalagi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            Glide.with(getActivity())
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifimage);

            customDialog.show();
        } else { }
    }
    private void openNotificationSettings() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().getPackageName());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", requireContext().getPackageName());
            intent.putExtra("app_uid", requireContext().getApplicationInfo().uid);
        } else {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.setData(Uri.parse("package:" + requireContext().getPackageName()));
        }
        startActivity(intent);
    }
    private void showNotification() {

    }
    private void simpanDevice() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_ADD_DEVICE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("IdAdmin", IdAdmin);
                map.put("Status", Status);
                map.put("Token", Token);
                System.out.println(map);

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void LoadDevice() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DEVICE, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                PreferencesDevice.setKeyToken(getContext(), data.getString("Token"));
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
    private void LoadListingPrimary() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PRIMARY, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItemsPrimary.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                PrimaryModel md = new PrimaryModel();
                                md.setIdListingPrimary(data.getString("IdListingPrimary"));
                                md.setJudulListingPrimary(data.getString("JudulListingPrimary"));
                                md.setHargaListingPrimary(data.getString("HargaListingPrimary"));
                                md.setDeskripsiListingPrimary(data.getString("DeskripsiListingPrimary"));
                                md.setAlamatListingPrimary(data.getString("AlamatListingPrimary"));
                                md.setLatitudeListingPrimary(data.getString("LatitudeListingPrimary"));
                                md.setLongitudeListingPrimary(data.getString("LongitudeListingPrimary"));
                                md.setLocationListingPrimary(data.getString("LocationListingPrimary"));
                                md.setKontakPerson1(data.getString("KontakPerson1"));
                                md.setKontakPerson2(data.getString("KontakPerson2"));
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
                                mItemsPrimary.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapterPrimary.notifyDataSetChanged();
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
    private void LoadListingSold() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_SOLD, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItemsSold.clear();
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
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLocation(data.getString("Location"));
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
                                md.setHarga(data.getString("Harga"));
                                md.setHargaSewa(data.getString("HargaSewa"));
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
                                md.setRented(data.getString("Rented"));
                                md.setView(data.getString("View"));
                                md.setMarketable(data.getString("Marketable"));
                                md.setStatusHarga(data.getString("StatusHarga"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                md.setFee(data.getString("Fee"));
                                md.setNamaVendor(data.getString("NamaVendor"));
                                md.setNoTelpVendor(data.getString("NoTelpVendor"));
                                mItemsSold.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapterSold.notifyDataSetChanged();
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
    private void LoadListingPopuler() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_HOT, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItemsHot.clear();
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
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLocation(data.getString("Location"));
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
                                md.setHarga(data.getString("Harga"));
                                md.setHargaSewa(data.getString("HargaSewa"));
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
                                md.setRented(data.getString("Rented"));
                                md.setView(data.getString("View"));
                                md.setMarketable(data.getString("Marketable"));
                                md.setStatusHarga(data.getString("StatusHarga"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                md.setFee(data.getString("Fee"));
                                md.setNamaVendor(data.getString("NamaVendor"));
                                md.setNoTelpVendor(data.getString("NoTelpVendor"));
                                mItemsHot.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapterPopular.notifyDataSetChanged();
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
    private void LoadListing() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItemsNew.clear();
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
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLocation(data.getString("Location"));
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
                                md.setHarga(data.getString("Harga"));
                                md.setHargaSewa(data.getString("HargaSewa"));
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
                                md.setRented(data.getString("Rented"));
                                md.setView(data.getString("View"));
                                md.setMarketable(data.getString("Marketable"));
                                md.setStatusHarga(data.getString("StatusHarga"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                md.setFee(data.getString("Fee"));
                                md.setNamaVendor(data.getString("NamaVendor"));
                                md.setNoTelpVendor(data.getString("NoTelpVendor"));
                                mItemsNew.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapterNew.notifyDataSetChanged();
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
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
            } else {
                Toast.makeText(getActivity(), "Izin lokasi ditolak", Toast.LENGTH_SHORT).show();
            }
        }
        if (requestCode == MY_PERMISSIONS_REQUEST_VIBRATE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showNotification();
            } else {
            }
        }
    }
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.addMarker(new MarkerOptions()
                                    .position(currentLocation)
                                    .title("Lokasi Saya"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            googleMap.clear();

                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject markerObject = response.getJSONObject(i);
                                    double lat = markerObject.getDouble("Latitude");
                                    double lng = markerObject.getDouble("Longitude");
                                    String title = markerObject.getString("NamaListing");
                                    String harga = markerObject.getString("Harga");

                                    if (!harga.isEmpty()){
                                        double hargaDouble = Double.parseDouble(harga);

                                        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
                                        String formattedHarga = numberFormat.format(hargaDouble);

                                        LatLng position = new LatLng(lat, lng);

                                        int width = 50;
                                        int height = 70;
                                        Bitmap smallMarker = Bitmap.createScaledBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.markerlocation)).getBitmap(), width, height, false);
                                        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                                        MarkerOptions markerOptions = new MarkerOptions()
                                                .position(position)
                                                .title(title)
                                                .snippet(formattedHarga)
                                                .icon(smallMarkerIcon);

                                        googleMap.addMarker(markerOptions);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Tangani kesalahan Volley
                        }
                    }
            );

            requestQueue.add(request);

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    startActivity(new Intent(getActivity(), MapsActivity.class));
                }
            });
        }
    }
}