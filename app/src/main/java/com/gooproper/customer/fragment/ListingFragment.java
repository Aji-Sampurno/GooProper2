package com.gooproper.customer.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gooproper.LoginActivity;
import com.gooproper.R;
import com.gooproper.ui.LoginConditionActivity;
import com.gooproper.util.Preferences;

public class ListingFragment extends Fragment {

    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_listing, container, false);

        id = Preferences.getKeyIdCustomer(getActivity());

        if (id.isEmpty()){
            startActivity(new Intent(getActivity(), LoginConditionActivity.class));
            getActivity().finish();
        }

        return root;
    }
}