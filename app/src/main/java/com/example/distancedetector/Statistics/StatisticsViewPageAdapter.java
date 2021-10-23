package com.example.distancedetector.Statistics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class StatisticsViewPageAdapter extends FragmentPagerAdapter  {
    public StatisticsViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                return new InfectedAreasFragment();
            case 1 :
                return new WorldStatusFragment();
            default:
                return new InfectedAreasFragment();
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
                return "Infected Areas";
            case 1:
                return "World Status";
            default:
                return "Infected Areas";
        }
    }
}
