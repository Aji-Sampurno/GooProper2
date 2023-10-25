package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.util.ServerApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ClosingActivity extends AppCompatActivity {

    private ProgressDialog PDClosing;
    private static final int PICK_PDF_REQUEST_1 = 1;
    private static final int PICK_PDF_REQUEST_2 = 2;
    private static final int PICK_PDF_REQUEST_3 = 3;
    private static final int PICK_PDF_REQUEST_4 = 4;
    private Uri suratkesepakatan, tandajadi, pengikatannotaris, pelunasan;
    private String FileName1, FileName2, FileName3, FileName4, File1, File2, File3, File4;
    Button BtnSuratKeterangan, BtnTandaJadi, BtnPengikatanNotaris, BtnPelunasan, BtnBatal, BtnSubmit;
    EditText EtSuratKeterangan, EtTandaJadi, EtPengikatanNotaris, EtPelunasan;
    ImageView IVBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closing);

        PDClosing = new ProgressDialog(ClosingActivity.this);
        IVBack = findViewById(R.id.IVBackClosing);
        BtnSuratKeterangan = findViewById(R.id.BtnUploadSuratkesepakatan);
        BtnTandaJadi = findViewById(R.id.BtnUploadTandaJadi);
        BtnPengikatanNotaris = findViewById(R.id.BtnUploadPengikatanNotaris);
        BtnPelunasan = findViewById(R.id.BtnUploadPelunasan);
        BtnSubmit = findViewById(R.id.BtnSubmitClosing);
        BtnBatal = findViewById(R.id.BtnBatalClosing);
        EtSuratKeterangan = findViewById(R.id.EtSuratKesepakatan);
        EtTandaJadi = findViewById(R.id.EtTandaJadi);
        EtPengikatanNotaris = findViewById(R.id.EtPengikatanNotaris);
        EtPelunasan = findViewById(R.id.EtPelunasan);

        BtnSuratKeterangan.setOnClickListener(v -> pickPDF(PICK_PDF_REQUEST_1));
        BtnTandaJadi.setOnClickListener(v -> pickPDF(PICK_PDF_REQUEST_2));
        BtnPengikatanNotaris.setOnClickListener(v -> pickPDF(PICK_PDF_REQUEST_3));
        BtnPelunasan.setOnClickListener(v -> pickPDF(PICK_PDF_REQUEST_4));
        BtnSubmit.setOnClickListener(v -> Closing());
        BtnBatal.setOnClickListener(v -> finish());
        IVBack.setOnClickListener(v -> finish());
    }

    private void pickPDF(int requestCode) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, requestCode);
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickPDF(requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_PDF_REQUEST_1) {
                suratkesepakatan = data.getData();
            } else if (requestCode == PICK_PDF_REQUEST_2) {
                tandajadi = data.getData();
            } else if (requestCode == PICK_PDF_REQUEST_3) {
                pengikatannotaris = data.getData();
            } else if (requestCode == PICK_PDF_REQUEST_4) {
                pelunasan = data.getData();
            }
        }
    }

    private void Closing() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_CLOSING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle successful response from server
                        Toast.makeText(ClosingActivity.this, "Upload Success: " + response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(ClosingActivity.this, "Upload Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                if (suratkesepakatan == null){
                    File1 = "0";
                } else {
                    File1 = getBase64FromUri(suratkesepakatan);
                }
                if (tandajadi == null){
                    File2 = "0";
                } else {
                    File2 = getBase64FromUri(tandajadi);
                }
                if (pengikatannotaris == null){
                    File3 = "0";
                } else {
                    File3 = getBase64FromUri(pengikatannotaris);
                }
                params.put("File1", getBase64FromUri(suratkesepakatan));
                params.put("File2", getBase64FromUri(suratkesepakatan));
                params.put("File3", getBase64FromUri(suratkesepakatan));


                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private String getBase64FromUri(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            byte[] fileBytes = getBytesFromInputStream(inputStream);
            return Base64.encodeToString(fileBytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private byte[] getBytesFromInputStream(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        if (uri != null) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    fileName = cursor.getString(nameIndex);
                }
            }
        }
        return fileName;
    }
}