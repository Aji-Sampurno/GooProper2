package com.gooproper.customer.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.gooproper.EditAkunActivity;
import com.gooproper.LoginActivity;
import com.gooproper.R;
import com.gooproper.SettingActivity;
import com.gooproper.TentangKamiActivity;
import com.gooproper.ui.ListingFavoriteActivity;
import com.gooproper.ui.ListingTerakhirDilihatActivity;
import com.gooproper.util.Preferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class AkunFragment extends Fragment {

    String wa = "";
    String ig = "";
    String tt = "";
    String yt = "";
    String em = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_akun, container, false);

        LinearLayout topbar     = root.findViewById(R.id.lyttopbar);
        LinearLayout favorite   = root.findViewById(R.id.lytfavorite);
        LinearLayout dilihat    = root.findViewById(R.id.lytterakhirdilihat);
        LinearLayout daftar     = root.findViewById(R.id.lytdaftaragent);
        LinearLayout pengaturan = root.findViewById(R.id.lytpengaturan);
        LinearLayout hubungi    = root.findViewById(R.id.lythubungikami);
        LinearLayout tentang    = root.findViewById(R.id.lyttentangkami);
        LinearLayout login      = root.findViewById(R.id.lytlogin);
        CardView cardView1      = root.findViewById(R.id.cv1);
        CardView cardView2      = root.findViewById(R.id.cv2);
        TextView editakun       = root.findViewById(R.id.tveditakun);
        TextView namaakun       = root.findViewById(R.id.tvnamaakun);
        CircleImageView profile = root.findViewById(R.id.cvprofile);
        String status = Preferences.getKeyStatus(getActivity());
        if (status.isEmpty()){
            login.setVisibility(View.VISIBLE);
            topbar.setVisibility(View.GONE);
            cardView1.setVisibility(View.GONE);
            cardView2.setVisibility(View.GONE);
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

        LinearLayout wa = customDialog.findViewById(R.id.lytwa);
        LinearLayout ig = customDialog.findViewById(R.id.lytig);
        LinearLayout tt = customDialog.findViewById(R.id.lyttiktok);
        LinearLayout yt = customDialog.findViewById(R.id.lytyt);
        LinearLayout em = customDialog.findViewById(R.id.lytemail);

        wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone="+wa;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://instagram.com/_u/"+ig;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://instagram.com/_u/"+tt;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        yt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://instagram.com/_u/"+yt;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://instagram.com/_u/"+em;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        customDialog.show();
    }
}