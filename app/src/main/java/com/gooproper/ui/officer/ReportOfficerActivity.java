package com.gooproper.ui.officer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.gooproper.R;

public class ReportOfficerActivity extends AppCompatActivity {

    Button BtnTambahReport;
    SwipeRefreshLayout SRReport;
    RecyclerView RVReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_officer);

        BtnTambahReport = findViewById(R.id.BtnTambahReport);
        RVReport = findViewById(R.id.RVReport);
        SRReport = findViewById(R.id.SRReport);

        BtnTambahReport.setOnClickListener(v -> startActivity(new Intent(ReportOfficerActivity.this, TambahReportOfficerActivity.class)));

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}