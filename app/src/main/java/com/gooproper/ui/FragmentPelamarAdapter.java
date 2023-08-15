package com.gooproper.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.gooproper.ui.pelamar.PelamarAgenFragment;
import com.gooproper.ui.pelamar.PelamarKantorLainFragment;
import com.gooproper.ui.pelamar.PelamarMitraFragment;

public class FragmentPelamarAdapter  extends FragmentStateAdapter {
    public FragmentPelamarAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new PelamarMitraFragment();
            case 2:
                return new PelamarKantorLainFragment();
        }

        return new PelamarAgenFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
