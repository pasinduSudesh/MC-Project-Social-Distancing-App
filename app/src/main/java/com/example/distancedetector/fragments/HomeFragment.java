package com.example.distancedetector.fragments;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.distancedetector.Activities.DeviceListAdapter;
import com.example.distancedetector.Models.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.distancedetector.Home.HomeViewPageAdapter;
import com.example.distancedetector.R;
import com.example.distancedetector.Widget.CustomViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private View c_view;

    //Elements in the screen
    private Button scanButton;
    private ListView deviceListVew;

    private boolean scanStarted = false;
    private long scanStartTime;
    private Thread deviceSearchTread = null;
    private ArrayList<Device> detectedDevices = new ArrayList<Device>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Check permission for location
        int permissionCheck = getActivity().checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += getActivity().checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {

            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }

        // Inflate the layout for this fragment
        c_view =  inflater.inflate(R.layout.fragment_home, container, false);


        //Initialize the elements
        scanButton = c_view.findViewById(R.id.button2);
        deviceListVew = c_view.findViewById(R.id.deviceList);
        tabLayout = c_view.findViewById(R.id.home_tab_layout);
        viewPager = c_view.findViewById(R.id.home_viewpager);

        //Initialize the view list
        deviceListVew.setAdapter(new DeviceListAdapter(getActivity(), R.layout.device_list_view, detectedDevices));

        //OnClickListener for scan button
        scanButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(scanStarted){
                    //scan is started
                    scanButton.setText("Scan");
                    detectedDevices = new ArrayList<Device>();
                    scanStarted = false;
                    getActivity().stopService(new Intent(getActivity(), BluetoothScanService.class));
                    if(deviceSearchTread!=null){
                        deviceSearchTread.interrupt();
                    }

                }else{
                    scanStartTime = System.currentTimeMillis();
                    //scan is not started
                    scanButton.setText("Checking Bluetooth");
                    boolean canStartScan = checkBluetooth();

                    if (canStartScan){
                        scanStarted = true;
                        scanButton.setText("Stop Scan");
                        System.out.println("before checckkkkk");
                        ComponentName cn =  getActivity().startService(new Intent(getActivity(), BluetoothScanService.class));
                        System.out.println("after checckkkkk");

                        deviceSearchTread = new Thread(){
                            @Override
                            public void run() {

                                while (true){
                                    detectedDevices = getDetectedDeviceList();

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            deviceListVew.setAdapter(new DeviceListAdapter(getActivity(), R.layout.device_list_view, detectedDevices));
                                        }
                                    });

                                    try {
                                        Thread.sleep(10*1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        };
                        deviceSearchTread.start();


                    }else{
                        scanStarted = false;
                        scanButton.setText("Scan");
                        Toast.makeText(getActivity(), "Please turn on Bluetooth", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });

        HomeViewPageAdapter adapter = new HomeViewPageAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);

        tabLayout.setupWithViewPager(viewPager);
        return c_view;
    }

    //check the bluetooth is turn on
    public boolean checkBluetooth(){
        //TODO check bluetooth is enabled
        return true;
    }

    //Get detected list from local DB
    public ArrayList<Device> getDetectedDeviceList(){
        return  new LocalDB(getActivity()).getDetectedDevices(this.scanStartTime);
    }
}