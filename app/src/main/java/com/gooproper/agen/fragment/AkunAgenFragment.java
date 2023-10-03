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
import com.gooproper.ui.ListingFavoriteActivity;
import com.gooproper.ui.ListingTerakhirDilihatActivity;
import com.gooproper.ui.ListingkuActivity;
import com.gooproper.ui.PraListingAgenActivity;
import com.gooproper.ui.RewardActivity;
import com.gooproper.ui.TambahListingActivity;
import com.gooproper.util.Preferences;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AkunAgenFragment extends Fragment {

    private LinearLayout reward, listing, listingmitra, listingkl, agen, mitra, kl, listingku, favorite, favoritemitra, favoritekl, seen, seenmitra, seenkl, pengaturan, pengaturanmitra, pengaturankl, hubungikami, hubungikamimitra, hubungikamikl, tentangkami, tentangkamimitra, tentangkamikl, pralising;
    TextView nama, edit;
    CircleImageView cvagen;
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
        View root = inflater.inflate(R.layout.fragment_akun_agen, container, false);

        agen = root.findViewById(R.id.lytagen);
        mitra = root.findViewById(R.id.lytkl);
        kl = root.findViewById(R.id.lytmitra);
        reward = root.findViewById(R.id.lytreward);
        listingku = root.findViewById(R.id.lytlistingku);
        favorite = root.findViewById(R.id.lytfavorite);
        favoritemitra = root.findViewById(R.id.lytfavoritemitra);
        favoritekl = root.findViewById(R.id.lytfavoritekl);
        seen = root.findViewById(R.id.lytterakhirdilihat);
        seenmitra = root.findViewById(R.id.lytterakhirdilihatmitra);
        seenkl = root.findViewById(R.id.lytterakhirdilihatkl);
        listing = root.findViewById(R.id.lytlisting);
        listingmitra = root.findViewById(R.id.lytlistingmitra);
        listingkl = root.findViewById(R.id.lytlistingkl);
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

        listingku.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingkuActivity.class)));
        favorite.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingFavoriteActivity.class)));
        favoritemitra.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingFavoriteActivity.class)));
        favoritekl.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingFavoriteActivity.class)));
        seen.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingTerakhirDilihatActivity.class)));
        seenmitra.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingTerakhirDilihatActivity.class)));
        seenkl.setOnClickListener(view -> startActivity(new Intent(getContext(), ListingTerakhirDilihatActivity.class)));
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
        pralising.setOnClickListener(v -> startActivity(new Intent(getContext(), PraListingAgenActivity.class)));

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