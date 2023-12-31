package com.gooproper.agen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.res.ColorStateList;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gooproper.R;

public class MainAgenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_agen);

        BottomNavigationView navigationView = findViewById(R.id.nav_view_agen);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.flowUpAgen_nav, R.id.infoAgen_nav, R.id.homeAgen_nav, R.id.listingAgen_nav, R.id.akunAgen_nav).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_agen);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        ColorStateList colorStateList = ContextCompat.getColorStateList(this, R.color.color_navigation);
        navigationView.setItemIconTintList(colorStateList);
        navigationView.setItemTextColor(colorStateList);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}