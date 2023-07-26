package com.gooproper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.gooproper.util.Preferences;

public class EditAkunActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);

        ImageView back = findViewById(R.id.ivback);
        ImageView save = findViewById(R.id.ivsave);
        EditText username = findViewById(R.id.etusername);
        EditText namalengkap = findViewById(R.id.etnamalengkap);
        EditText telephone = findViewById(R.id.ettelp);
        EditText email = findViewById(R.id.etemail);

        username.setText(Preferences.getKeyUsername(EditAkunActivity.this));
        namalengkap.setText(Preferences.getKeyNamaLengkap(EditAkunActivity.this));
        telephone.setText(Preferences.getKeyNoTelp(EditAkunActivity.this));
        email.setText(Preferences.getKeyEmail(EditAkunActivity.this));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}