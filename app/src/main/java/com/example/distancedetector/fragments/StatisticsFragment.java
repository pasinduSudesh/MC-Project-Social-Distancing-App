package com.example.distancedetector.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.distancedetector.R;
import com.example.distancedetector.Statistics.StatisticsViewPageAdapter;
import com.example.distancedetector.Widget.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

public class StatisticsFragment extends Fragment {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private View c_view;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        c_view = inflater.inflate(R.layout.fragment_statistics, container, false);

        tabLayout = c_view.findViewById(R.id.tab_layout);
        viewPager = c_view.findViewById(R.id.statistics_viewpager);

        StatisticsViewPageAdapter adapter = new StatisticsViewPageAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);

        tabLayout.setupWithViewPager(viewPager);
        return c_view;
    }
}