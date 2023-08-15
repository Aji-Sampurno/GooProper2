package com.gooproper.admin.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gooproper.R;
import com.gooproper.SettingActivity;
import com.gooproper.customer.MainCustomerActivity;
import com.gooproper.ui.ClosingActivity;
import com.gooproper.util.Preferences;

public class ClosingAdminFragment extends Fragment {

    FloatingActionButton fabClosing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_closing_admin, container, false);

        fabClosing = root.findViewById(R.id.FABClosingAdmin);

        fabClosing.setOnClickListener(v -> startActivity(new Intent(getActivity(), ClosingActivity.class)));

        return root;
    }
}