package com.gooproper.admin.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gooproper.EditAkunActivity;
import com.gooproper.R;
import com.gooproper.SettingActivity;
import com.gooproper.TentangKamiActivity;
import com.gooproper.ui.AddListingActivity;
import com.gooproper.ui.AgenActivity;
import com.gooproper.ui.LamarActivity;
import com.gooproper.ui.PelamarAgenActivity;
import com.gooproper.ui.PralistingActivity;
import com.gooproper.ui.RewardActivity;
import com.gooproper.ui.TambahKaryawanActivity;
import com.gooproper.ui.TambahListingActivity;
import com.gooproper.util.Preferences;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AkunAdminFragment extends Fragment {

    private LinearLayout pelamar, agen, listing, karyawan, pengaturan, hubungikami, tentangkami;
    TextView nama, edit;
    CircleImageView cvadmin;
    View view;
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
        karyawan = root.findViewById(R.id.LytTambahKaryawan);
        pelamar = root.findViewById(R.id.LytPelamarAdmin);
        agen = root.findViewById(R.id.LytAgenAdmin);
        pengaturan = root.findViewById(R.id.LytPengaturanAdmin);
        hubungikami = root.findViewById(R.id.LytHubungiKamiAdmin);
        tentangkami = root.findViewById(R.id.LytTentangKamiAdmin);
        nama = root.findViewById(R.id.TVNamaAkunAdmin);
        edit = root.findViewById(R.id.TVEditAkunAdmin);
        cvadmin = root.findViewById(R.id.CIVAkunAdmin);
        view = root.findViewById(R.id.V1);

        nama.setText(Preferences.getKeyUsername(getActivity()));
        edit.setOnClickListener(view -> startActivity(new Intent(getActivity(), EditAkunActivity.class)));

        profile = Preferences.getKeyPhoto(getActivity());
        status = Preferences.getKeyStatus(getActivity());

        if (status.equals("1")) {
            listing.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
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
        pelamar.setOnClickListener(view -> startActivity(new Intent(getContext(), PelamarAgenActivity.class)));
        agen.setOnClickListener(view -> startActivity(new Intent(getContext(), AgenActivity.class)));
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