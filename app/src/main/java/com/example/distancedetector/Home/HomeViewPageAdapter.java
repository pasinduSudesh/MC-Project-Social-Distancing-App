package com.example.distancedetector.Home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class HomeViewPageAdapter extends FragmentPagerAdapter  {
    public HomeViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                return new SearchFragment();
            case 1 :
                return new TrustedDevicesFragment();
            default:
                return new SearchFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Search";
            case 1:
                return "Trusted Devices";
            default:
                return "Search";
        }
    }
}
