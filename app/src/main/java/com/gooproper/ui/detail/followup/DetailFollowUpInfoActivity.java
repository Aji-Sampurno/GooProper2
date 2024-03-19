package com.gooproper.ui.detail.followup;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.adapter.followup.UpdateFlowUpAdapter;
import com.gooproper.model.UpdateFlowUpModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailFollowUpInfoActivity extends AppCompatActivity {

    TextView TVNama, TVAlamat, TVNarahubung, TVWa, TVTanggal, TVJam, TVKeterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_follow_up_info);

        TVNama = findViewById(R.id.TVNamaFollowUpInfo);
        TVAlamat = findViewById(R.id.TVAlamatFollowUpInfo);
        TVNarahubung = findViewById(R.id.TVNarahubungFollowUpInfo);
        TVWa = findViewById(R.id.TVWaFollowUpInfo);
        TVTanggal = findViewById(R.id.TVTanggalFollowUpInfo);
        TVJam = findViewById(R.id.TVJamFollowUpInfo);
        TVKeterangan = findViewById(R.id.TVKeteranganFollowUpInfo);

        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intentIdFlowup = data.getStringExtra("IdFlowup");
        String intentIdInfo = data.getStringExtra("IdInfo");
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentTanggal = data.getStringExtra("Tanggal");
        String intentJam = data.getStringExtra("Jam");
        String intentKeteranganFollowUp = data.getStringExtra("KeteranganFollowUp");
        String intentKeterangan = data.getStringExtra("Keterangan");
        String intentJenisProperty = data.getStringExtra("JenisProperty");
        String intentStatusProperty = data.getStringExtra("StatusProperty");
        String intentNarahubung = data.getStringExtra("Narahubung");
        String intentImgSelfie = data.getStringExtra("ImgSelfie");
        String intentImgProperty = data.getStringExtra("ImgProperty");
        String intentLokasi = data.getStringExtra("Lokasi");
        String intentAlamat = data.getStringExtra("Alamat");
        String intentNoTelp = data.getStringExtra("NoTelp");
        String intentLatitude = data.getStringExtra("Latitude");
        String intentLongitude = data.getStringExtra("Longitude");
        String intentTglInput = data.getStringExtra("TglInput");
        String intentJamInput = data.getStringExtra("JamInput");
        String intentIsListing = data.getStringExtra("IsListing");
        String intentIsAgen = data.getStringExtra("IsAgen");
        String intentLBangunan = data.getStringExtra("LBangunan");
        String intentLTanah = data.getStringExtra("LTanah");
        String intentHarga = data.getStringExtra("Harga");
        String intentHargaSewa = data.getStringExtra("HargaSewa");
        String intentIsSpek = data.getStringExtra("IsSpek");

        TVNama.setText(intentStatusProperty + " " + intentJenisProperty);
        TVAlamat.setText(intentLokasi);
        TVNarahubung.setText(intentNarahubung);
        TVWa.setText(intentNoTelp);
        TVTanggal.setText(intentTanggal);
        TVJam.setText(intentJam);
        TVKeterangan.setText(intentKeteranganFollowUp);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }
}