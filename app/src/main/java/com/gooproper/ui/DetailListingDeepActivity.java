package com.gooproper.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gooproper.R;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.util.AgenManager;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailListingDeepActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog PDDetailListingDeep;
    TextView TVNamaDetailListing, TVAlamatDetailListing, TVHargaDetailListing, TVViewsDetailListing, TVBedDetailListing, TVNamaAgen, TVBathDetailListing, TVWideDetailListing, TVLandDetailListing, TVTipeDetailListing, TVStatusDetailListing, TVSertifikatDetailListing, TVLuasDetailListing, TVKamarTidurDetailListing, TVKamarMandiDetailListing, TVLantaiDetailListing, TVGarasiDetailListing, TVCarpotDetailListing, TVListrikDetailListing, TVSumberAirDetailListing, TVDeskripsiDetailListing;
    ImageView IVFlowUp, IVWhatsapp, IVInstagram, IVFavorite, IVFavoriteOn, IVShare;
    ScrollView scrollView;
    CardView agen;
    String status, idpralisting, idagen, idlisting, agenid, idpengguna;
    String BuyerNama, BuyerTelp, BuyerKeterangan, BuyerTanggal, BuyerIdAgen, BuyerIdListing, BuyerIdInput, BuyerJam;
    String NamaMaps;
    String imageUrl, namaAgen, telpAgen;
    Intent intent;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    ArrayList<String> images = new ArrayList<>();
    private MapView mapView;
    private GoogleMap googleMap;
    double lat, lng;
    String productId, path, StringNamaBuyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_listing_deep);

        PDDetailListingDeep = new ProgressDialog(DetailListingDeepActivity.this);
        viewPager = findViewById(R.id.VPDetailListingDeep);
        agen = findViewById(R.id.LytAgenDetailListingDeep);
        scrollView = findViewById(R.id.SVDetailListingDeep);

        TVNamaDetailListing = findViewById(R.id.TVNamaDetailListingDeep);
        TVAlamatDetailListing = findViewById(R.id.TVAlamatDetailListingDeep);
        TVHargaDetailListing = findViewById(R.id.TVHargaDetailListingDeep);
        TVViewsDetailListing = findViewById(R.id.TVViewsDetailListingDeep);
        TVBedDetailListing = findViewById(R.id.TVBedDetailListingDeep);
        TVBathDetailListing = findViewById(R.id.TVBathDetailListingDeep);
        TVWideDetailListing = findViewById(R.id.TVWideDetailListingDeep);
        TVLandDetailListing = findViewById(R.id.TVLandDetailListingDeep);
        TVTipeDetailListing = findViewById(R.id.TVTipeHunianDetailListingDeep);
        TVStatusDetailListing = findViewById(R.id.TVStatusHunianDetailListingDeep);
        TVSertifikatDetailListing = findViewById(R.id.TVSertifikatDetailListingDeep);
        TVLuasDetailListing = findViewById(R.id.TVLuasHunianDetailListingDeep);
        TVKamarTidurDetailListing = findViewById(R.id.TVKamarTidurHunianDetailListingDeep);
        TVKamarMandiDetailListing = findViewById(R.id.TVKamarMandiHunianDetailListingDeep);
        TVLantaiDetailListing = findViewById(R.id.TVLevelDetailListingDeep);
        TVGarasiDetailListing = findViewById(R.id.TVGarasiDetailListingDeep);
        TVCarpotDetailListing = findViewById(R.id.TVCarportDetailListingDeep);
        TVListrikDetailListing = findViewById(R.id.TVListrikDetailListingDeep);
        TVSumberAirDetailListing = findViewById(R.id.TVSumberAirDetailListingDeep);
        TVDeskripsiDetailListing = findViewById(R.id.TVDeskripsiDetailListingDeep);
        TVNamaAgen = findViewById(R.id.TVNamaAgenDetailListingDeep);

        IVFlowUp = findViewById(R.id.IVFlowUpAgenDetailListingDeep);
        IVWhatsapp = findViewById(R.id.IVNoTelpAgenDetailListingDeep);
        IVInstagram = findViewById(R.id.IVInstagramAgenDetailListingDeep);
        IVFavorite = findViewById(R.id.IVFavoriteDetailListingDeep);
        IVFavoriteOn = findViewById(R.id.IVFavoriteOnDetailListingDeep);
        IVShare = findViewById(R.id.IVShareDetailListingDeep);

        mapView = findViewById(R.id.MVDetailListingDeep);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        IVShare.setVisibility(View.GONE);
        IVFavorite.setVisibility(View.GONE);
        IVFavoriteOn.setVisibility(View.GONE);

        status = Preferences.getKeyStatus(this);

        if (status.equals("1")){
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
        } else if (status.equals("2")) {
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
        } else if (status.equals("3")){
            StringNamaBuyer = Preferences.getKeyNama(this);
        } else {
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            IVFlowUp.setVisibility(View.INVISIBLE);
        }

        intent = getIntent();
        Uri data = intent.getData();
        path = data.getPath();
        productId = path.substring(path.lastIndexOf('/') + 1);
        displayProductDetails(productId);
        
        IVShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String deepLinkUrl = "https://gooproper.com/listing/"+productId;
                String shareMessage = "Lihat listingan kami \n hubungi : \n "+namaAgen+" - "+telpAgen+"\n" + deepLinkUrl;

                Picasso.get().load(imageUrl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        // Mengatur gambar ke dalam Clipboard
                        String imagePath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image Title", null);
                        Uri imageUri = Uri.parse(imagePath);

                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("image/*");

                        // Mengatur pesan, tautan, dan gambar pada Intent
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                        // Memulai aktivitas berbagi dengan pilihan aplikasi
                        startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"));
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        // Tidak ada yang perlu dilakukan di sini
                    }
                });
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void displayProductDetails(String productId) {
        PDDetailListingDeep.setMessage("Memuat Data...");
        PDDetailListingDeep.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_DEEP+productId,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDDetailListingDeep.cancel();
                        PDDetailListingDeep.dismiss();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                FormatCurrency currency = new FormatCurrency();
                                String intentIdListing = data.getString("IdListing");
                                String intentIdAgen = data.getString("IdAgen");
                                String intentIdInput = data.getString("IdInput");
                                String intentNamaListing = data.getString("NamaListing");
                                String intentAlamat = data.getString("Alamat");
                                String intentLatitude = data.getString("Latitude");
                                String intentLongitude = data.getString("Longitude");
                                String intentLocation = data.getString("Location");
                                String intentWide = data.getString("Wide");
                                String intentLand = data.getString("Land");
                                String intentListrik = data.getString("Listrik");
                                String intentLevel = data.getString("Level");
                                String intentBed = data.getString("Bed");
                                String intentBedArt = data.getString("BedArt");
                                String intentBath = data.getString("Bath");
                                String intentBathArt = data.getString("BathArt");
                                String intentGarage = data.getString("Garage");
                                String intentCarpot = data.getString("Carpot");
                                String intentHadap = data.getString("Hadap");
                                String intentSHM = data.getString("SHM");
                                String intentHGB = data.getString("HGB");
                                String intentHSHP = data.getString("HSHP");
                                String intentPPJB = data.getString("PPJB");
                                String intentStratatitle = data.getString("Stratatitle");
                                String intentNoSHM = data.getString("NoSHM");
                                String intentNoHGB = data.getString("NoHGB");
                                String intentNoHSHP = data.getString("NoHSHP");
                                String intentNoPPJB = data.getString("NoPPJB");
                                String intentNoStratatitle = data.getString("NoStratatitle");
                                String intentNoCertificate = data.getString("NoCertificate");
                                String intentPbb = data.getString("Pbb");
                                String intentJenisProperti = data.getString("JenisProperti");
                                String intentJenisCertificate = data.getString("JenisCertificate");
                                String intentSumberAir = data.getString("SumberAir");
                                String intentKondisi = data.getString("Kondisi");
                                String intentDeskripsi = data.getString("Deskripsi");
                                String intentPrabot = data.getString("Prabot");
                                String intentKetPrabot = data.getString("KetPrabot");
                                String intentPriority = data.getString("Priority");
                                String intentTtd = data.getString("Ttd");
                                String intentBanner = data.getString("Banner");
                                String intentHarga = data.getString("Harga");
                                String intentTglInput = data.getString("TglInput");
                                String intentImg1 = data.getString("Img1");
                                String intentImg2 = data.getString("Img2");
                                String intentImg3 = data.getString("Img3");
                                String intentImg4 = data.getString("Img4");
                                String intentImg5 = data.getString("Img5");
                                String intentImg6 = data.getString("Img6");
                                String intentImg7 = data.getString("Img7");
                                String intentImg8 = data.getString("Img8");
                                String intentVideo = data.getString("Video");
                                String intentLinkFacebook = data.getString("LinkFacebook");
                                String intentLinkTiktok = data.getString("LinkTiktok");
                                String intentLinkInstagram = data.getString("LinkInstagram");
                                String intentLinkYoutube = data.getString("LinkYoutube");
                                String intentIsAdmin = data.getString("IsAdmin");
                                String intentIsManager = data.getString("IsManager");
                                String intentSold = data.getString("Sold");
                                String intentView = data.getString("View");
                                String intentNama = data.getString("Nama");
                                String intentNoTelp = data.getString("NoTelp");
                                String intentInstagram = data.getString("Instagram");

                                if (intentNamaListing.isEmpty()){
                                    TVNamaDetailListing.setText(": -");
                                } else {
                                    TVNamaDetailListing.setText(intentNamaListing);
                                }

                                if (intentAlamat.isEmpty()){
                                    TVAlamatDetailListing.setText(intentAlamat);
                                } else {
                                    TVAlamatDetailListing.setText(intentAlamat);
                                }

                                if (intentHarga.isEmpty()){
                                    TVHargaDetailListing.setText("Rp. -");
                                } else {
                                    TVHargaDetailListing.setText(currency.formatRupiah(intentHarga));
                                }

                                if (intentView.isEmpty()){
                                    TVViewsDetailListing.setText("0 Views");
                                } else {
                                    TVViewsDetailListing.setText(intentView+" Views");
                                }

                                if (intentBed.isEmpty()){
                                    if (intentBedArt.isEmpty()){
                                        TVBedDetailListing.setText("0 + 0");
                                    } else {
                                        TVBedDetailListing.setText("0 + " + intentBedArt);
                                    }
                                } else {
                                    if (intentBedArt.isEmpty()){
                                        TVBedDetailListing.setText(intentBed + " + 0");
                                    } else {
                                        TVBedDetailListing.setText(intentBed + " + " + intentBedArt);
                                    }
                                }

                                if (intentBath.isEmpty()){
                                    if (intentBathArt.isEmpty()){
                                        TVBathDetailListing.setText("0 + 0");
                                    } else {
                                        TVBathDetailListing.setText("0 + " + intentBathArt);
                                    }
                                } else {
                                    if (intentBathArt.isEmpty()){
                                        TVBathDetailListing.setText(intentBathArt + " + " + intentBathArt);
                                    } else {
                                        TVBathDetailListing.setText(intentBathArt + " + " + intentBathArt);
                                    }
                                }

                                if (intentWide.isEmpty()){
                                    TVWideDetailListing.setText("-");
                                } else {
                                    TVWideDetailListing.setText(intentWide + " m2");
                                }

                                if (intentLand.isEmpty()){
                                    TVLandDetailListing.setText("-");
                                } else {
                                    TVLandDetailListing.setText(intentLand + " m2");
                                }

                                if (intentJenisProperti.isEmpty()){
                                    TVTipeDetailListing.setText(": -");
                                } else {
                                    TVTipeDetailListing.setText(": "+intentJenisProperti);
                                }

                                if (intentKondisi.isEmpty()){
                                    TVStatusDetailListing.setText(": -");
                                } else {
                                    TVStatusDetailListing.setText(": "+intentKondisi);
                                }

                                if (intentJenisCertificate.isEmpty()){
                                    TVSertifikatDetailListing.setText(": -");
                                } else {
                                    TVSertifikatDetailListing.setText(": "+intentJenisCertificate);
                                }

                                if (intentWide.isEmpty()){
                                    if (intentLand.isEmpty()){
                                        TVLuasDetailListing.setText(": - m2/- m2");
                                    } else {
                                        TVLuasDetailListing.setText(": - m2/" + intentLand + " m2");
                                    }
                                } else {
                                    if (intentLand.isEmpty()){
                                        TVLuasDetailListing.setText(": "+intentWide + " m2/- m2");
                                    } else {
                                        TVLuasDetailListing.setText(": "+intentWide + " m2/" + intentLand + " m2");

                                    }
                                }
                                TVKamarTidurDetailListing.setText(": "+intentBed + " + " + intentBedArt);
                                TVKamarMandiDetailListing.setText(": "+intentBath + " + " + intentBathArt);

                                if (intentLevel.isEmpty()){
                                    TVLantaiDetailListing.setText(": -");
                                } else {
                                    TVLantaiDetailListing.setText(": "+intentLevel);
                                }

                                if (intentGarage.isEmpty()){
                                    TVGarasiDetailListing.setText(": -");
                                } else {
                                    TVGarasiDetailListing.setText(": "+intentGarage);
                                }

                                if (intentCarpot.isEmpty()){
                                    TVCarpotDetailListing.setText(": -");
                                } else {
                                    TVCarpotDetailListing.setText(": "+intentCarpot);
                                }

                                if (intentListrik.isEmpty()){
                                    TVListrikDetailListing.setText(": -");
                                } else {
                                    TVListrikDetailListing.setText(": "+intentListrik + " Watt");

                                }

                                if (intentSumberAir.isEmpty()){
                                    TVSumberAirDetailListing.setText(": -");
                                } else {
                                    TVSumberAirDetailListing.setText(": "+intentSumberAir);
                                }

                                if (intentDeskripsi.isEmpty()){
                                    TVDeskripsiDetailListing.setText(": -");
                                } else {
                                    TVDeskripsiDetailListing.setText(": "+intentDeskripsi);
                                }

                                TVNamaAgen.setText(intentNama);
                                NamaMaps = intentNamaListing;

                                lat = Double.parseDouble(intentLatitude);
                                lng = Double.parseDouble(intentLongitude);

                                if (status.equals("1")) {
                                    IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String deepLinkUrl = "https://gooproper.com/listing/" + intentIdListing;
                                            String message = "Halo! Saya Manager, ingin menanyakan update pada listingan " + intentNamaListing + " yang beralamat di " + intentAlamat + ".\nDetail Listingan :\n" + deepLinkUrl;
                                            String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else if (status.equals("2")) {
                                    IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String deepLinkUrl = "https://gooproper.com/listing/" + intentIdListing;
                                            String message = "Halo! Saya Admin, ingin menanyakan update pada listingan " + intentNamaListing + " yang beralamat di " + intentAlamat + ".\nDetail Listingan :\n" + deepLinkUrl;
                                            String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else if (status.equals("3")) {
                                    IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String deepLinkUrl = "https://gooproper.com/listing/" + intentIdListing;
                                            String message = "Halo! Saya " + StringNamaBuyer + ", ingin melakukan cobroke pada listingan " + intentNamaListing + " yang beralamat di " + intentAlamat + ".\nApakah bersedia? \nDetail Listingan :\n" + deepLinkUrl;
                                            String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else {
                                    IVFlowUp.setVisibility(View.INVISIBLE);
                                    IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String deepLinkUrl = "https://gooproper.com/listing/" + intentIdListing;
                                            String message = "Halo! Saya " + StringNamaBuyer + ", ingin menanyakan informasi mengenai listingan " + intentNamaListing + " yang beralamat di " + intentAlamat + ".\nApakah masih ada? atau ada update terbaru?\nDetail Listingan :\n" + deepLinkUrl;
                                            String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                }

                                IVInstagram.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String url = "http://instagram.com/_u/" + intentInstagram;
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                    }
                                });

                                if (intentImg1.equals("0")) {
                                } else {
                                    images.add(intentImg1);
                                }

                                if (intentImg2.equals("0")) {
                                } else {
                                    images.add(intentImg2);
                                }

                                if (intentImg3.equals("0")) {
                                } else {
                                    images.add(intentImg3);
                                }

                                if (intentImg4.equals("0")) {
                                } else {
                                    images.add(intentImg4);
                                }

                                if (intentImg5.equals("0")) {
                                } else {
                                    images.add(intentImg5);
                                }

                                if (intentImg6.equals("0")) {
                                } else {
                                    images.add(intentImg6);
                                }

                                if (intentImg7.equals("0")) {
                                } else {
                                    images.add(intentImg7);
                                }

                                if (intentImg8.equals("0")) {
                                } else {
                                    images.add(intentImg8);
                                }
                                adapter = new ViewPagerAdapter(DetailListingDeepActivity.this, images);
                                viewPager.setPadding(0, 0, 0, 0);
                                viewPager.setAdapter(adapter);
                            } catch (JSONException e) {
                                PDDetailListingDeep.dismiss();
                                e.printStackTrace();

                                Dialog customDialog = new Dialog(DetailListingDeepActivity.this);
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
                                        displayProductDetails(productId);
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PDDetailListingDeep.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(DetailListingDeepActivity.this);
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
                                displayProductDetails(productId);
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

    private void shareDeepLink(String productId) {
        String deepLinkUrl = "https://gooproper.com/listing/"+productId;
        String shareMessage = "Lihat listingan kami \n hubungi : \n "+namaAgen+" - "+telpAgen+"\n" + deepLinkUrl;

        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // Mengatur gambar ke dalam Clipboard
                String imagePath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image Title", null);
                Uri imageUri = Uri.parse(imagePath);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");

                // Mengatur pesan, tautan, dan gambar pada Intent
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                // Memulai aktivitas berbagi dengan pilihan aplikasi
                startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                e.printStackTrace();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                // Tidak ada yang perlu dilakukan di sini
            }
        });
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
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng currentLocation = new LatLng(lat, lng);
                    googleMap.addMarker(new MarkerOptions()
                            .position(currentLocation)
                            .title(NamaMaps));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
                }
            });

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    double latitude = lat;
                    double longitude = lng;

                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }
            });
        }
    }
}