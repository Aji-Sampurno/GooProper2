package com.gooproper.guest.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gooproper.R;
import com.gooproper.ui.LoginConditionActivity;

public class ListingGuestFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listing_guest, container, false);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.guest_navigation);
        navController.navigate(R.id.login_nav);
        return view;
    }
}