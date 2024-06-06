package com.gooproper.ui.detail.listing;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.gooproper.R;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.adapter.listing.SusulanPendingAdapter;
import com.gooproper.model.SusulanModel;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailListingPendingActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    ViewPager VPGambarListing;
    ViewPagerAdapter adapter;
    TextView TVPriority, TVNoPjp, TVKondisi, TVNamaListing, TVAlamatListing, TVHargaJualListing, TVHargaSewaListing, TVRangeHargaListing, TVPoinListing, TVNamaAgen1, TVNamaAgen2;
    ImageView IVAlamat;
    String StringIdListing, StringIdCo;
    int FinalPoin;
    CheckBox CBSelfie, CBLokasi, CBMarketable, CBHarga;
    Button BtnApprove;
    RecyclerView RVSusulan;
    SusulanPendingAdapter susulanPendingAdapter;
    List<SusulanModel> mItems;
    ArrayList<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_listing_pending);

        pDialog = new ProgressDialog(this);

        VPGambarListing = findViewById(R.id.VPDetailListingPending);

        RVSusulan = findViewById(R.id.RVSusulanPending);

        IVAlamat = findViewById(R.id.IVAlamatDetailListingPending);

        BtnApprove = findViewById(R.id.BtnApproveSusulanListingPending);

        CBSelfie = findViewById(R.id.CBSelfie);
        CBLokasi = findViewById(R.id.CBLokasi);
        CBMarketable = findViewById(R.id.CBMarketable);
        CBHarga = findViewById(R.id.CBHarga);

        TVPriority = findViewById(R.id.TVPriorityListingPending);
        TVNoPjp = findViewById(R.id.TVNoPjpListingPending);
        TVKondisi = findViewById(R.id.TVKondisiListingPending);
        TVNamaListing = findViewById(R.id.TVNamaDetailListingPendind);
        TVAlamatListing = findViewById(R.id.TVAlamatDetailListingPending);
        TVHargaJualListing = findViewById(R.id.TVHargaDetailListingPending);
        TVHargaSewaListing = findViewById(R.id.TVHargaSewaDetailListingPending);
        TVRangeHargaListing = findViewById(R.id.TVRangeHargaDetailListingPending);
        TVPoinListing = findViewById(R.id.TVPoinListingPending);
        TVNamaAgen1 = findViewById(R.id.TVNamaAgenDetailListing);
        TVNamaAgen2 = findViewById(R.id.TVNamaAgen2DetailListing);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdListing = data.getStringExtra("IdListing");
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentIdAgenCo = data.getStringExtra("IdAgenCo");
        String intentIdInput = data.getStringExtra("IdInput");
        String intentNamaListing = data.getStringExtra("NamaListing");
        String intentAlamat = data.getStringExtra("Alamat");
        String intentLatitude = data.getStringExtra("Latitude");
        String intentLongitude = data.getStringExtra("Longitude");
        String intentLocation = data.getStringExtra("Location");
        String intentWilayah = data.getStringExtra("Wilayah");
        String intentSelfie = data.getStringExtra("Selfie");
        String intentWide = data.getStringExtra("Wide");
        String intentLand = data.getStringExtra("Land");
        String intentDimensi = data.getStringExtra("Dimensi");
        String intentListrik = data.getStringExtra("Listrik");
        String intentLevel = data.getStringExtra("Level");
        String intentBed = data.getStringExtra("Bed");
        String intentBedArt = data.getStringExtra("BedArt");
        String intentBath = data.getStringExtra("Bath");
        String intentBathArt = data.getStringExtra("BathArt");
        String intentGarage = data.getStringExtra("Garage");
        String intentCarpot = data.getStringExtra("Carpot");
        String intentHadap = data.getStringExtra("Hadap");
        String intentSHM = data.getStringExtra("SHM");
        String intentHGB = data.getStringExtra("HGB");
        String intentHSHP = data.getStringExtra("HSHP");
        String intentPPJB = data.getStringExtra("PPJB");
        String intentStratatitle = data.getStringExtra("Stratatitle");
        String intentAJB = data.getStringExtra("AJB");
        String intentPetokD = data.getStringExtra("PetokD");
        String intentPjp = data.getStringExtra("Pjp");
        String intentImgSHM = data.getStringExtra("ImgSHM");
        String intentImgHGB = data.getStringExtra("ImgHGB");
        String intentImgHSHP = data.getStringExtra("ImgHSHP");
        String intentImgPPJB = data.getStringExtra("ImgPPJB");
        String intentImgStratatitle = data.getStringExtra("ImgStratatitle");
        String intentImgAJB = data.getStringExtra("ImgAJB");
        String intentImgPetokD = data.getStringExtra("ImgPetokD");
        String intentImgPjp = data.getStringExtra("ImgPjp");
        String intentImgPjp1 = data.getStringExtra("ImgPjp1");
        String intentNoCertificate = data.getStringExtra("NoCertificate");
        String intentPbb = data.getStringExtra("Pbb");
        String intentJenisProperti = data.getStringExtra("JenisProperti");
        String intentJenisCertificate = data.getStringExtra("JenisCertificate");
        String intentSumberAir = data.getStringExtra("SumberAir");
        String intentKondisi = data.getStringExtra("Kondisi");
        String intentDeskripsi = data.getStringExtra("Deskripsi");
        String intentPrabot = data.getStringExtra("Prabot");
        String intentKetPrabot = data.getStringExtra("KetPrabot");
        String intentPriority = data.getStringExtra("Priority");
        String intentTtd = data.getStringExtra("Ttd");
        String intentBanner = data.getStringExtra("Banner");
        String intentSize = data.getStringExtra("Size");
        String intentHarga = data.getStringExtra("Harga");
        String intentHargaSewa = data.getStringExtra("HargaSewa");
        String intentTglInput = data.getStringExtra("TglInput");
        String intentImg1 = data.getStringExtra("Img1");
        String intentImg2 = data.getStringExtra("Img2");
        String intentImg3 = data.getStringExtra("Img3");
        String intentImg4 = data.getStringExtra("Img4");
        String intentImg5 = data.getStringExtra("Img5");
        String intentImg6 = data.getStringExtra("Img6");
        String intentImg7 = data.getStringExtra("Img7");
        String intentImg8 = data.getStringExtra("Img8");
        String intentImg9 = data.getStringExtra("Img9");
        String intentImg10 = data.getStringExtra("Img10");
        String intentImg11 = data.getStringExtra("Img11");
        String intentImg12 = data.getStringExtra("Img12");
        String intentVideo = data.getStringExtra("Video");
        String intentLinkFacebook = data.getStringExtra("LinkFacebook");
        String intentLinkTiktok = data.getStringExtra("LinkTiktok");
        String intentLinkInstagram = data.getStringExtra("LinkInstagram");
        String intentLinkYoutube = data.getStringExtra("LinkYoutube");
        String intentIsAdmin = data.getStringExtra("IsAdmin");
        String intentIsManager = data.getStringExtra("IsManager");
        String intentIsRejected = data.getStringExtra("IsRejected");
        String intentView = data.getStringExtra("View");
        String intentSold = data.getStringExtra("Sold");
        String intentRented = data.getStringExtra("Rented");
        String intentSoldAgen = data.getStringExtra("SoldAgen");
        String intentRentedAgen = data.getStringExtra("RentedAgen");
        String intentMarketable = data.getStringExtra("Marketable");
        String intentStatusHarga = data.getStringExtra("StatusHarga");
        String intentNama = data.getStringExtra("Nama");
        String intentNoTelp = data.getStringExtra("NoTelp");
        String intentInstagram = data.getStringExtra("Instagram");
        String intentFee = data.getStringExtra("Fee");
        String intentNamaVendor = data.getStringExtra("NamaVendor");
        String intentNoTelpVendor = data.getStringExtra("NoTelpVendor");
        String intentIsSelfie = data.getStringExtra("IsSelfie");
        String intentIsLokasi = data.getStringExtra("IsLokasi");
        String intentIdTemplate = data.getStringExtra("IdTemplate");
        String intentTemplate = data.getStringExtra("Template");
        String intentTemplateBlank = data.getStringExtra("TemplateBlank");

        StringIdListing = intentIdListing;
        StringIdCo = intentIdAgenCo;

        if (intentIdAgenCo.equals("0")) {
            TVNamaAgen2.setVisibility(View.GONE);
        } else if (intentIdAgenCo.equals(intentIdAgen)) {
            TVNamaAgen2.setVisibility(View.GONE);
        } else {
            LoadCo();
            TVNamaAgen2.setVisibility(View.VISIBLE);
        }

        FormatCurrency currency = new FormatCurrency();

        mItems = new ArrayList<>();

        RVSusulan.setLayoutManager(new LinearLayoutManager(DetailListingPendingActivity.this, LinearLayoutManager.VERTICAL, false));
        susulanPendingAdapter = new SusulanPendingAdapter(DetailListingPendingActivity.this, mItems);
        RVSusulan.setAdapter(susulanPendingAdapter);

        LoadSusulan();

        BtnApprove.setOnClickListener(v -> Approve());

        if (update == 1) {
            if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 120;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 120;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 120 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 100;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 100;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 100 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 50;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 50;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 50 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                }
            } else if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 100;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 100;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 100 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 80;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 80;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 80 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 40;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 40;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 40 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                }
            } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 70;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 70;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 70 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 60;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 60;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 60 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 30;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 30;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 30 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                }
            } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 60;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 60;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 60 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 40;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 40;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 40 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 20;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 20;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 20 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                }
            } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 50;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 50;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 50 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 20;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 20;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 20 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 10;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 10;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 10 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                }
            } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 30;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 30;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 30 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 20;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 20;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 20 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 10;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 10;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 10 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                }
            }
            if (intentMarketable.equals("1")) {
                CBMarketable.setChecked(true);
            } else {
                CBMarketable.setChecked(false);
            }
            if (intentStatusHarga.equals("1")) {
                CBHarga.setChecked(true);
            } else {
                CBHarga.setChecked(false);
            }
            if (intentIsSelfie.equals("1")) {
                CBSelfie.setChecked(true);
            } else {
                CBSelfie.setChecked(false);
            }
            if (intentIsLokasi.equals("1")) {
                CBLokasi.setChecked(true);
            } else {
                CBLokasi.setChecked(false);
            }
            if (intentKondisi.isEmpty()) {
                TVKondisi.setText("-");
            } else {
                TVKondisi.setText(intentKondisi);
            }
            if (intentJenisProperti.equals("Rukost")) {
                TVRangeHargaListing.setVisibility(View.GONE);
            } else {
                TVRangeHargaListing.setVisibility(View.GONE);
            }
            if (intentPriority.isEmpty() || intentPriority.equals("open")) {
                TVPriority.setVisibility(View.INVISIBLE);
            } else {
                TVPriority.setText("Exclusive");
            }
            if (intentPjp.isEmpty() || intentPjp.equals("0")) {
                TVNoPjp.setVisibility(View.INVISIBLE);
            } else {
                TVNoPjp.setText(intentPjp);
            }
            if (intentNamaListing.isEmpty()) {
                TVNamaListing.setText("-");
            } else {
                TVNamaListing.setText(intentNamaListing);
            }
            if (intentAlamat.isEmpty()) {
                TVAlamatListing.setText("-");
            } else {
                if (intentWilayah.isEmpty()) {
                    TVAlamatListing.setText(intentAlamat);
                } else {
                    TVAlamatListing.setText(intentAlamat+" ( "+intentWilayah+" )");
                }
            }
            if (intentKondisi.equals("Jual")) {
                if (intentHarga.isEmpty()) {
                    TVHargaJualListing.setText("Rp. -");
                    TVHargaSewaListing.setVisibility(View.GONE);
                } else {
                    TVHargaJualListing.setText(currency.formatRupiah(intentHarga));
                    TVHargaSewaListing.setVisibility(View.GONE);
                }
            } else if (intentKondisi.equals("Sewa")) {
                if (intentHargaSewa.isEmpty()) {
                    TVHargaSewaListing.setText("Rp. -");
                    TVHargaJualListing.setVisibility(View.GONE);
                } else {
                    TVHargaSewaListing.setText(currency.formatRupiah(intentHargaSewa));
                    TVHargaJualListing.setVisibility(View.GONE);
                }
            } else {
                if (intentHarga.isEmpty()) {
                    if (intentHargaSewa.isEmpty()) {
                        TVHargaJualListing.setText("Rp. -");
                        TVHargaSewaListing.setText("Rp. -");
                    } else {
                        TVHargaJualListing.setText("Rp. - /");
                        TVHargaSewaListing.setText(currency.formatRupiah(intentHargaSewa));
                    }
                } else {
                    if (intentHargaSewa.isEmpty()) {
                        TVHargaJualListing.setText(currency.formatRupiah(intentHarga));
                        TVHargaSewaListing.setText("Rp. -");
                    } else {
                        TVHargaJualListing.setText(currency.formatRupiah(intentHarga) + " /");
                        TVHargaSewaListing.setText(currency.formatRupiah(intentHargaSewa));
                    }
                }
            }
            TVNamaAgen1.setText(intentNama);
//            TVNamaAgen.setText(intentNama);
            if (!intentTemplate.equals("null")) {
                images.add(intentTemplate);
            }
            if (!intentTemplateBlank.equals("null")) {
                images.add(intentTemplateBlank);
            }
            if (!intentImg1.equals("0")) {
                images.add(intentImg1);
            }
            if (!intentImg2.equals("0")) {
                images.add(intentImg2);
            }
            if (!intentImg3.equals("0")) {
                images.add(intentImg3);
            }
            if (!intentImg4.equals("0")) {
                images.add(intentImg4);
            }
            if (!intentImg5.equals("0")) {
                images.add(intentImg5);
            }
            if (!intentImg6.equals("0")) {
                images.add(intentImg6);
            }
            if (!intentImg7.equals("0")) {
                images.add(intentImg7);
            }
            if (!intentImg8.equals("0")) {
                images.add(intentImg8);
            }
            if (!intentImg9.equals("0")) {
                images.add(intentImg9);
            }
            if (!intentImg10.equals("0")) {
                images.add(intentImg10);
            }
            if (!intentImg11.equals("0")) {
                images.add(intentImg11);
            }
            if (!intentImg12.equals("0")) {
                images.add(intentImg12);
            }
            if (!intentVideo.equals("0")) {
                images.add(intentVideo);
            }

            adapter = new ViewPagerAdapter(this, images);
            VPGambarListing.setPadding(0, 0, 0, 0);
            VPGambarListing.setAdapter(adapter);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void LoadSusulan(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_SUSULAN + StringIdListing, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItems.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                SusulanModel susulanModel = new SusulanModel();
                                susulanModel.setKeterangan(data.getString("Keterangan"));
                                susulanModel.setTglInput(data.getString("TglInput"));
                                susulanModel.setPoinTambahan(data.getString("PoinTambahan"));
                                susulanModel.setPoinBerkurang(data.getString("PoinBerkurang"));
                                mItems.add(susulanModel);
                            } catch (JSONException e) {
                                e.printStackTrace();

                                Dialog customDialog = new Dialog(DetailListingPendingActivity.this);
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
                                        LoadSusulan();
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

                        susulanPendingAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(DetailListingPendingActivity.this);
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
                                LoadSusulan();
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
    private void LoadCo() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_CO_LISTING + StringIdCo, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String NamaCo = data.getString("Nama");

                                TVNamaAgen2.setText(NamaCo);

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
    private void Approve() {
        String itemId = StringIdListing;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_APPROVE_PENDING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);

                            Toast.makeText(DetailListingPendingActivity.this, "Berhasil Approve Listing ", Toast.LENGTH_SHORT).show();
                            Dialog customDialog = new Dialog(DetailListingPendingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Berhasil Approve Listingan");
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(DetailListingPendingActivity.this)
                                    .load(R.mipmap.ic_yes)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailListingPendingActivity.this, "Gagal Approve Pending. Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("IdListing",itemId);
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(DetailListingPendingActivity.this);
        requestQueue.add(stringRequest);
    }
}