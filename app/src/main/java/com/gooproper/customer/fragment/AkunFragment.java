package com.gooproper.customer.fragment;

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

import com.gooproper.ui.edit.EditAkunActivity;
import com.gooproper.R;
import com.gooproper.ui.user.SettingActivity;
import com.gooproper.ui.user.TentangKamiActivity;
import com.gooproper.ui.listing.ListingFavoriteActivity;
import com.gooproper.ui.listing.ListingTerakhirDilihatActivity;
import com.gooproper.ui.LoginConditionActivity;
import com.gooproper.ui.registrasi.RegistrasiAgenActivity;
import com.gooproper.util.Preferences;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AkunFragment extends Fragment {

    LinearLayout topbar,favorite,dilihat,daftar,pengaturan,hubungi,tentang,login;
    CardView cardView1,cardView2;
    TextView editakun,namaakun;
    CircleImageView cvcustomer;
    String imgurl;
    String profile;
    String id;
    String wa = "811 333 8838";
    String fb = "100085562741346";
    String ig = "gooproper.oficial";
    String tt = "@gooproper";
    String yt = "@gooproperofficial";
    String em = "goopropermalang09@gmail.com";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_akun, container, false);

        topbar     = root.findViewById(R.id.lyttopbar);
        favorite   = root.findViewById(R.id.lytfavorite);
        dilihat    = root.findViewById(R.id.lytterakhirdilihat);
        daftar     = root.findViewById(R.id.lytdaftaragent);
        pengaturan = root.findViewById(R.id.lytpengaturan);
        hubungi    = root.findViewById(R.id.lythubungikami);
        tentang    = root.findViewById(R.id.lyttentangkami);
        login      = root.findViewById(R.id.lytlogin);
        cardView1  = root.findViewById(R.id.cv1);
        cardView2  = root.findViewById(R.id.cv2);
        editakun   = root.findViewById(R.id.tveditakun);
        namaakun   = root.findViewById(R.id.tvnamaakun);
        cvcustomer = root.findViewById(R.id.cvprofile);

        id      = Preferences.getKeyIdCustomer(getActivity());
        profile = Preferences.getKeyPhoto(getActivity());

        if (profile.isEmpty()){
            imgurl = "https://testingadnro.000webhostapp.com/gambar/profile/user%20default.png";
        } else {
            imgurl = profile;
        }

        Picasso.get()
                .load(imgurl)
                .into(cvcustomer);

        if (id.isEmpty()){
            startActivity(new Intent(getActivity(), LoginConditionActivity.class));
            getActivity().finish();
        }

        namaakun.setText(Preferences.getKeyUsername(getActivity()));

        editakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditAkunActivity.class));
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListingFavoriteActivity.class));
            }
        });

        dilihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListingTerakhirDilihatActivity.class));
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegistrasiAgenActivity.class));
            }
        });

        pengaturan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        hubungi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomAlertDialog(view);
            }
        });

        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TentangKamiActivity.class));
            }
        });

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