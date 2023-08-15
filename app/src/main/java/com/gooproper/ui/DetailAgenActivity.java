package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.gooproper.R;
import com.gooproper.util.Preferences;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailAgenActivity extends AppCompatActivity {

    TextView nama, telp, instagram, listing, jual, sewa;
    CircleImageView cvagen;
    RecyclerView rvbadge, rvlisting;
    String imgurl, profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agen);

        nama      = findViewById(R.id.tvnamaagen);
        telp      = findViewById(R.id.tvtelpagen);
        instagram = findViewById(R.id.tvinstagramagen);
        listing   = findViewById(R.id.tvlisting);
        jual      = findViewById(R.id.tvjual);
        sewa      = findViewById(R.id.tvsewa);
        cvagen    = findViewById(R.id.cvagen);
        rvbadge   = findViewById(R.id.rvbadge);
        rvlisting = findViewById(R.id.rvlistingagen);

        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentUsername = data.getStringExtra("Username");
        String intentPassword = data.getStringExtra("Password");
        String intentNama = data.getStringExtra("Nama");
        String intentNoTelp = data.getStringExtra("NoTelp");
        String intentEmail = data.getStringExtra("Email");
        String intentTglLahir = data.getStringExtra("TglLahir");
        String intentKotaKelahiran = data.getStringExtra("KotaKelahiran");
        String intentPendidikan = data.getStringExtra("Pendidikan");
        String intentNamaSekolah = data.getStringExtra("NamaSekolah");
        String intentMasaKerja = data.getStringExtra("MasaKerja");
        String intentJabatan = data.getStringExtra("Jabatan");
        String intentStatus = data.getStringExtra("Status");
        String intentAlamatDomisili = data.getStringExtra("AlamatDomisili");
        String intentFacebook = data.getStringExtra("Facebook");
        String intentInstagram  = data.getStringExtra("Instagram");
        String intentNoKtp = data.getStringExtra("NoKtp");
        String intentImgKtp = data.getStringExtra("ImgKtp");
        String intentImgTtd = data.getStringExtra("ImgTtd");
        String intentNpwp  = data.getStringExtra("Npwp ");
        String intentPhoto = data.getStringExtra("Photo");
        String intentPoin = data.getStringExtra("Poin ");
        String intentIsAkses = data.getStringExtra("IsAkses");

        profile = intentPhoto;

        if (profile.isEmpty()){
            imgurl = "https://testingadnro.000webhostapp.com/gambar/profile/user%20default.png";
        } else {
            imgurl = profile;
        }

        if(update == 1){
            nama.setText(intentNama);
            telp.setText(intentNoTelp);
            instagram.setText(intentInstagram);
            Picasso.get()
                    .load(imgurl)
                    .into(cvagen);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
}