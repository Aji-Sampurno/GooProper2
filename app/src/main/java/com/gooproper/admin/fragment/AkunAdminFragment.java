package com.gooproper.admin.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.ui.CobaPesanActivity;
import com.gooproper.ui.ReportAgenActivity;
import com.gooproper.ui.edit.EditAkunActivity;
import com.gooproper.R;
import com.gooproper.ui.SettingActivity;
import com.gooproper.ui.TentangKamiActivity;
import com.gooproper.ui.AgenActivity;
import com.gooproper.ui.LaporanListingActivity;
import com.gooproper.ui.PelamarAgenActivity;
import com.gooproper.ui.followup.FollowUpInfoListActivity;
import com.gooproper.ui.listing.InfoPropertyActivity;
import com.gooproper.ui.listing.ListListingSementaraActivity;
import com.gooproper.ui.listing.ListingPendingActivity;
import com.gooproper.ui.listing.PraListingRejectedAdminActivity;
import com.gooproper.ui.tambah.TambahInfoActivity;
import com.gooproper.ui.tambah.TambahKaryawanActivity;
import com.gooproper.ui.tambah.TambahListingActivity;
import com.gooproper.ui.tambah.TambahListingPrimaryActivity;
import com.gooproper.ui.tambah.TambahListingSementaraActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;

import de.hdodenhof.circleimageview.CircleImageView;

public class AkunAdminFragment extends Fragment {

    private LinearLayout pelamar, agen, reportagen, tambahpenghargaan, listing, listingpending, info, followupinfo, pralistingrejected, primary, listprimary, karyawan, laporan, pengaturan, hubungikami, tentangkami, kirimpesan;
    TextView nama, edit, agenultah;
    CardView CVUltah;
    CircleImageView cvadmin;
    View view, view1, view2, view3, view4;
    String imgurl;
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
        View root = inflater.inflate(R.layout.fragment_akun_admin, container, false);

        listing = root.findViewById(R.id.LytListingAdmin);
        listingpending = root.findViewById(R.id.LytListingPendingAdmin);
        info = root.findViewById(R.id.LytInfoAdmin);
        followupinfo = root.findViewById(R.id.LytFollowUpAdmin);
        pralistingrejected = root.findViewById(R.id.LytPraListingRejectedAdmin);
        primary = root.findViewById(R.id.LytListingPrimaryAdmin);
        listprimary = root.findViewById(R.id.LytListinganPrimary);
        karyawan = root.findViewById(R.id.LytTambahKaryawan);
        laporan = root.findViewById(R.id.LytLaporanListingan);
        pelamar = root.findViewById(R.id.LytPelamarAdmin);
        agen = root.findViewById(R.id.LytAgenAdmin);
        reportagen = root.findViewById(R.id.LytreportAgen);
        tambahpenghargaan = root.findViewById(R.id.LytTambahPenghargaan);
        pengaturan = root.findViewById(R.id.LytPengaturanAdmin);
        hubungikami = root.findViewById(R.id.LytHubungiKamiAdmin);
        tentangkami = root.findViewById(R.id.LytTentangKamiAdmin);
        kirimpesan = root.findViewById(R.id.LytKirimPesan);
        nama = root.findViewById(R.id.TVNamaAkunAdmin);
        edit = root.findViewById(R.id.TVEditAkunAdmin);
        agenultah = root.findViewById(R.id.TVNamaAgenUltah);
        cvadmin = root.findViewById(R.id.CIVAkunAdmin);
        CVUltah = root.findViewById(R.id.CVUltah);
        view = root.findViewById(R.id.V1);
        view1 = root.findViewById(R.id.V2);
        view2 = root.findViewById(R.id.V3);
        view3 = root.findViewById(R.id.V4);
        view4 = root.findViewById(R.id.V5);

        nama.setText(Preferences.getKeyUsername(getActivity()));
        edit.setOnClickListener(view -> startActivity(new Intent(getActivity(), EditAkunActivity.class)));

        profile = Preferences.getKeyPhoto(getActivity());
        status = Preferences.getKeyStatus(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_ULTAH_AGEN,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        StringBuilder sb = new StringBuilder();
                        for(int i = 0 ; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String Nama = data.getString("Nama");
                                sb.append(Nama).append("\n");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                sb.append("-").append("\n");
                            }
                        }
                        agenultah.setText(sb.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);

        if (status.equals("1")) {
            listing.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
            laporan.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            primary.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            listprimary.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            CVUltah.setVisibility(View.GONE);
            view4.setVisibility(View.GONE);
            listingpending.setVisibility(View.GONE);
        } else {
            listing.setVisibility(View.VISIBLE);
        }

        if (profile.isEmpty()) {
            imgurl = "https://testingadnro.000webhostapp.com/gambar/profile/user%20default.png";
        } else {
            imgurl = profile;
        }

        edit.setOnClickListener(view -> startActivity(new Intent(getContext(), EditAkunActivity.class)));
        listing.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahListingActivity.class)));
        listingpending.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingPendingActivity.class)));
        info.setOnClickListener(view -> startActivity(new Intent(getContext(), InfoPropertyActivity.class)));
        followupinfo.setOnClickListener(view -> startActivity(new Intent(getContext(), FollowUpInfoListActivity.class)));
        pralistingrejected.setOnClickListener(view -> startActivity(new Intent(getContext(), PraListingRejectedAdminActivity.class)));
        primary.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahListingPrimaryActivity.class)));
        pelamar.setOnClickListener(view -> startActivity(new Intent(getContext(), PelamarAgenActivity.class)));
        agen.setOnClickListener(view -> startActivity(new Intent(getContext(), AgenActivity.class)));
        reportagen.setOnClickListener(view -> startActivity(new Intent(getContext(), ReportAgenActivity.class)));
        tambahpenghargaan.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahListingActivity.class)));
        laporan.setOnClickListener(view -> startActivity(new Intent(getContext(), LaporanListingActivity.class)));
        kirimpesan.setOnClickListener(view -> startActivity(new Intent(getContext(), CobaPesanActivity.class)));
        karyawan.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahKaryawanActivity.class)));
        pengaturan.setOnClickListener(view -> startActivity(new Intent(getContext(), SettingActivity.class)));
        tentangkami.setOnClickListener(view -> startActivity(new Intent(getContext(), TentangKamiActivity.class)));
        hubungikami.setOnClickListener(view -> showCustomAlertDialog(view));

        Picasso.get()
                .load(imgurl)
                .into(cvadmin);

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
}