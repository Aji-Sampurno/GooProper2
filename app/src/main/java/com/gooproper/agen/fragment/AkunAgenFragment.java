package com.gooproper.agen.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.gooproper.guest.MainGuestActivity;
import com.gooproper.model.ListingModel;
import com.gooproper.ui.AgenActivity;
import com.gooproper.ui.edit.EditAkunActivity;
import com.gooproper.R;
import com.gooproper.ui.SettingActivity;
import com.gooproper.ui.TentangKamiActivity;
import com.gooproper.ui.listing.InfoPropertyKuActivity;
import com.gooproper.ui.listing.InfoPropertySpekActivity;
import com.gooproper.ui.listing.ListListingSementaraActivity;
import com.gooproper.ui.listing.ListingFavoriteActivity;
import com.gooproper.ui.listing.ListingTerakhirDilihatActivity;
import com.gooproper.ui.listing.ListingkuActivity;
import com.gooproper.ui.listing.PraListingAgenActivity;
import com.gooproper.ui.RewardActivity;
import com.gooproper.ui.tambah.TambahListingActivity;
import com.gooproper.ui.listing.PraListingRejectedActivity;
import com.gooproper.ui.tambah.TambahInfoActivity;
import com.gooproper.ui.tambah.TambahListingSementaraActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AkunAgenFragment extends Fragment {

    private LinearLayout reward, listing, agengoo, listingsementara, daftarsementara, listingmitra, listingkl, agen, mitra, kl, listingku, favorite, favoritemitra, favoritekl, seen, seenmitra, seenkl, pengaturan, pengaturanmitra, pengaturankl, hubungikami, hubungikamimitra, hubungikamikl, tentangkami, tentangkamimitra, tentangkamikl, pralising, pralistingreject, info, infoku, prainfo;
    TextView nama, edit;
    CircleImageView cvagen;
    String imgurl, IsAktif;
    String profile;
    String status;
    String wa = "811 333 8838";
    String fb = "100085562741346";
    String ig = "gooproper.oficial";
    String tt = "@gooproper";
    String yt = "@gooproperofficial";
    String em = "goopropermalang09@gmail.com";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_akun_agen, container, false);

        agen = root.findViewById(R.id.lytagen);
        mitra = root.findViewById(R.id.lytkl);
        kl = root.findViewById(R.id.lytmitra);
        reward = root.findViewById(R.id.lytreward);
        agengoo = root.findViewById(R.id.lytagengoo);
        listingku = root.findViewById(R.id.lytlistingku);
        favorite = root.findViewById(R.id.lytfavorite);
        favoritemitra = root.findViewById(R.id.lytfavoritemitra);
        favoritekl = root.findViewById(R.id.lytfavoritekl);
        seen = root.findViewById(R.id.lytterakhirdilihat);
        seenmitra = root.findViewById(R.id.lytterakhirdilihatmitra);
        seenkl = root.findViewById(R.id.lytterakhirdilihatkl);
        listing = root.findViewById(R.id.lytlisting);
        listingsementara = root.findViewById(R.id.lytsementara);
        daftarsementara = root.findViewById(R.id.lytdaftarsementara);
        listingmitra = root.findViewById(R.id.lytlistingmitra);
        listingkl = root.findViewById(R.id.lytlistingkl);
        info = root.findViewById(R.id.lytinfo);
        infoku = root.findViewById(R.id.lytinfoagen);
        prainfo = root.findViewById(R.id.lytinfospek);
        pengaturan = root.findViewById(R.id.lytpengaturan);
        pengaturanmitra = root.findViewById(R.id.lytpengaturanmitra);
        pengaturankl = root.findViewById(R.id.lytpengaturankl);
        hubungikami = root.findViewById(R.id.lythubungikami);
        hubungikamimitra = root.findViewById(R.id.lythubungikamimitra);
        hubungikamikl = root.findViewById(R.id.lythubungikamikl);
        tentangkami = root.findViewById(R.id.lyttentangkami);
        tentangkamimitra = root.findViewById(R.id.lyttentangkamimitra);
        tentangkamikl = root.findViewById(R.id.lyttentangkamikl);
        pralising = root.findViewById(R.id.lytpralisting);
        pralistingreject = root.findViewById(R.id.lytpralistingrejected);
        nama = root.findViewById(R.id.tvnamaakunagen);
        edit = root.findViewById(R.id.tveditakunagen);
        cvagen = root.findViewById(R.id.cvprofileagen);

        nama.setText(Preferences.getKeyUsername(getActivity()));
        edit.setOnClickListener(view -> startActivity(new Intent(getActivity(), EditAkunActivity.class)));

        status = Preferences.getKeyIsAkses(getActivity());
        profile = Preferences.getKeyPhoto(getActivity());

        if (profile.isEmpty()) {
            imgurl = "https://testingadnro.000webhostapp.com/gambar/profile/user%20default.png";
        } else {
            imgurl = profile;
        }

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_CEK_AKTIF+ Preferences.getKeyIdAgen(getContext()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                IsAktif = data.getString("IsAktif");
                                if (IsAktif.equals("1")) {
                                    listing.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahListingActivity.class)));
                                    listingsementara.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahListingSementaraActivity.class)));
                                    daftarsementara.setOnClickListener(view -> startActivity(new Intent(getContext(), ListListingSementaraActivity.class)));
                                    listingmitra.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahListingActivity.class)));
                                    listingkl.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahListingActivity.class)));
                                    info.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahInfoActivity.class)));

                                } else {
                                    listing.setOnClickListener(view -> showAktifAlertDialog(view));
                                    listingsementara.setOnClickListener(view -> showAktifAlertDialog(view));
                                    daftarsementara.setOnClickListener(view -> showAktifAlertDialog(view));
                                    listingmitra.setOnClickListener(view -> showAktifAlertDialog(view));
                                    listingkl.setOnClickListener(view -> showAktifAlertDialog(view));
                                    info.setOnClickListener(view -> showAktifAlertDialog(view));

                                }
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

        Picasso.get()
                .load(imgurl)
                .into(cvagen);

        if (status.equals("1")) {
            agen.setVisibility(View.VISIBLE);
            mitra.setVisibility(View.GONE);
            kl.setVisibility(View.GONE);
        } else if (status.equals("2")) {
            agen.setVisibility(View.GONE);
            mitra.setVisibility(View.VISIBLE);
            kl.setVisibility(View.GONE);
        } else if (status.equals("3")) {
            agen.setVisibility(View.GONE);
            mitra.setVisibility(View.GONE);
            kl.setVisibility(View.VISIBLE);
        } else {
            agen.setVisibility(View.GONE);
            mitra.setVisibility(View.GONE);
            kl.setVisibility(View.GONE);
        }

        agengoo.setOnClickListener(view -> startActivity(new Intent(getContext(), AgenActivity.class)));
        listingku.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingkuActivity.class)));
        favorite.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingFavoriteActivity.class)));
        favoritemitra.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingFavoriteActivity.class)));
        favoritekl.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingFavoriteActivity.class)));
        seen.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingTerakhirDilihatActivity.class)));
        seenmitra.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingTerakhirDilihatActivity.class)));
        seenkl.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingTerakhirDilihatActivity.class)));

        infoku.setOnClickListener(view -> startActivity(new Intent(getContext(), InfoPropertyKuActivity.class)));
        prainfo.setOnClickListener(view -> startActivity(new Intent(getContext(), InfoPropertySpekActivity.class)));
        pengaturan.setOnClickListener(view -> startActivity(new Intent(getContext(), SettingActivity.class)));
        pengaturanmitra.setOnClickListener(view -> startActivity(new Intent(getContext(), SettingActivity.class)));
        pengaturankl.setOnClickListener(view -> startActivity(new Intent(getContext(), SettingActivity.class)));
        tentangkami.setOnClickListener(view -> startActivity(new Intent(getContext(), TentangKamiActivity.class)));
        tentangkamimitra.setOnClickListener(view -> startActivity(new Intent(getContext(), TentangKamiActivity.class)));
        tentangkamikl.setOnClickListener(view -> startActivity(new Intent(getContext(), TentangKamiActivity.class)));
        hubungikami.setOnClickListener(view -> showCustomAlertDialog(view));
        hubungikamimitra.setOnClickListener(view -> showCustomAlertDialog(view));
        hubungikamikl.setOnClickListener(view -> showCustomAlertDialog(view));
        reward.setOnClickListener(view -> startActivity(new Intent(getContext(), RewardActivity.class)));
        pralising.setOnClickListener(v -> startActivity(new Intent(getContext(), PraListingAgenActivity.class)));
        pralistingreject.setOnClickListener(v -> startActivity(new Intent(getContext(), PraListingRejectedActivity.class)));

        return root;
    }

    public void showCustomAlertDialog(View view) {
        Dialog customDialog = new Dialog(getActivity());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.custom_dialog);

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
        dialogTitle.setText("Hubungi Kami");

        LinearLayout lytwa = customDialog.findViewById(R.id.lytwa);
        LinearLayout lytig = customDialog.findViewById(R.id.lytig);
        LinearLayout lytfb = customDialog.findViewById(R.id.lytfacebook);
        LinearLayout lyttt = customDialog.findViewById(R.id.lyttiktok);
        LinearLayout lytyt = customDialog.findViewById(R.id.lytyt);
        LinearLayout lytem = customDialog.findViewById(R.id.lytemail);

        lytwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone=" + wa;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        lytfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/profile.php?id=" + fb;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        lytig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://instagram.com/_u/" + ig;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        lyttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.tiktok.com/" + tt;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        lytyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.youtube.com/" + yt;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        lytem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {em});
                intent.setPackage("com.google.android.gm");

                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                }
            }
        });

        customDialog.show();
    }

    public void showAktifAlertDialog(View view) {
        Dialog customDialog = new Dialog(getContext());
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.custom_dialog_akun);

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
        Button ya = customDialog.findViewById(R.id.btnya);

        ya.setText("Hubungi Admin");

        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });

        dialogTitle.setText("Akun Anda Sedang Ditangguhkan");

        ImageView gifImageView = customDialog.findViewById(R.id.ivdialog);

        Glide.with(this)
                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(gifImageView);

        customDialog.show();
    }

    private void CekAktif() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_CEK_AKTIF+ Preferences.getKeyIdAgen(getContext()),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);

                                IsAktif = data.getString("IsAktif");
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
}