package com.gooproper.agen.fragment;

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

import com.gooproper.EditAkunActivity;
import com.gooproper.R;
import com.gooproper.SettingActivity;
import com.gooproper.TentangKamiActivity;
import com.gooproper.ui.RewardActivity;
import com.gooproper.ui.TambahListingActivity;
import com.gooproper.util.Preferences;

import de.hdodenhof.circleimageview.CircleImageView;

public class AkunAgenFragment extends Fragment {

    private LinearLayout reward, listing, listingmitra, listingkl, agen, mitra, kl, login, pengaturan, pengaturanmitra, pengaturankl, hubungikami, hubungikamimitra, hubungikamikl, tentangkami, tentangkamimitra, tentangkamikl;
    TextView nama, edit;
    CircleImageView cvagen;
    String imgurl;
    String profile;
    String status;
    String wa = "";
    String ig = "";
    String tt = "";
    String yt = "";
    String em = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_akun_agen, container, false);

        listing          = root.findViewById(R.id.lytlisting);
        listingmitra     = root.findViewById(R.id.lytlistingmitra);
        listingkl        = root.findViewById(R.id.lytlistingkl);
        reward           = root.findViewById(R.id.lytreward);
        agen             = root.findViewById(R.id.lytagen);
        mitra            = root.findViewById(R.id.lytkl);
        kl               = root.findViewById(R.id.lytmitra);
        login            = root.findViewById(R.id.lytlogin);
        pengaturan       = root.findViewById(R.id.lytpengaturan);
        pengaturanmitra  = root.findViewById(R.id.lytpengaturanmitra);
        pengaturankl     = root.findViewById(R.id.lytpengaturankl);
        hubungikami      = root.findViewById(R.id.lythubungikami);
        hubungikamimitra = root.findViewById(R.id.lythubungikamimitra);
        hubungikamikl    = root.findViewById(R.id.lythubungikamikl);
        tentangkami      = root.findViewById(R.id.lyttentangkami);
        tentangkamimitra = root.findViewById(R.id.lyttentangkamimitra);
        tentangkamikl    = root.findViewById(R.id.lyttentangkamikl);
        nama             = root.findViewById(R.id.tvnamaakunagen);
        edit             = root.findViewById(R.id.tveditakunagen);
        cvagen           = root.findViewById(R.id.cvprofileagen);

        nama.setText(Preferences.getKeyUsername(getActivity()));
        edit.setOnClickListener(view -> startActivity(new Intent(getActivity(), EditAkunActivity.class)));

        status  = Preferences.getKeyIsAkses(getActivity());
        profile = Preferences.getKeyPhoto(getActivity());

        if (profile.isEmpty()){
            imgurl = "https://testingadnro.000webhostapp.com/gambar/profile/user%20default.png";
        } else {
            imgurl = profile;
        }

        if (status.equals("1")){
            agen.setVisibility(View.VISIBLE);
            mitra.setVisibility(View.GONE);
            kl.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
        } else if (status.equals("2")) {
            agen.setVisibility(View.GONE);
            mitra.setVisibility(View.VISIBLE);
            kl.setVisibility(View.GONE);
            login.setVisibility(View.GONE);
        } else if (status.equals("3")) {
            agen.setVisibility(View.GONE);
            mitra.setVisibility(View.GONE);
            kl.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
        } else {
            agen.setVisibility(View.GONE);
            mitra.setVisibility(View.GONE);
            kl.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }

        listing.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahListingActivity.class)));
        listingmitra.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahListingActivity.class)));
        listingkl.setOnClickListener(view -> startActivity(new Intent(getContext(), TambahListingActivity.class)));
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
        LinearLayout lyttt = customDialog.findViewById(R.id.lyttiktok);
        LinearLayout lytyt = customDialog.findViewById(R.id.lytyt);
        LinearLayout lytem = customDialog.findViewById(R.id.lytemail);

        lytwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://api.whatsapp.com/send?phone="+wa;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        lytig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://instagram.com/_u/"+ig;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        lyttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://instagram.com/_u/"+tt;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        lytyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://instagram.com/_u/"+yt;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        lytem.setOnClickListener(new View.OnClickListener() {
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