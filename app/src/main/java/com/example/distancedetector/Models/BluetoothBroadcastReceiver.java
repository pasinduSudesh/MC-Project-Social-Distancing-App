package com.example.distancedetector.Models;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {

    ArrayList<Device> foundDevices = new ArrayList<Device>();
    ArrayList<String> foundDevicesIds = new ArrayList<String>();
    boolean isFinishedDiscovery = true;
    com.example.distancedetector.Models.LocalDB db;
    com.example.distancedetector.Models.DistanceCalculator distanceCalculator;

    public BluetoothBroadcastReceiver(com.example.distancedetector.Models.LocalDB db){

        this.db = db;
        distanceCalculator = new com.example.distancedetector.Models.DistanceCalculator();

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Get action type
        String action = intent.getAction();

        this.isFinishedDiscovery = false;
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            //Get detected bluetooth device
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            //Get RSSI value
            int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);

            //Save detected Device
            Device savedDevice = saveDeviceDetails2LocalStorage(context, device, rssi);
            //Notify users
            notifyUser(savedDevice);
        }

        if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            // if no devices found in a search
            System.out.println("Closed Discovery");
            this.isFinishedDiscovery = true;
        }
    }

    public Device saveDeviceDetails2LocalStorage(Context context, BluetoothDevice BTdevice, int rssi){

        /**
         * Save Device to LocalDB
         * Return Device object
         */

        long stimestamp = System.currentTimeMillis();
        System.out.println("Time Stamp: " + stimestamp);

        boolean hadDevice = false;
        boolean isSafeDevice = false;


        Device device = new Device(BTdevice.getAddress(), BTdevice.getName(), 0, stimestamp);
        double distance = distanceCalculator.calculateDistance(rssi);
        device.setDistance(distance);

        try{
            db.addDevices(device);
            Toast.makeText(context, "name: " + BTdevice.getName() + " " + BTdevice.getAddress() + " "+rssi, Toast.LENGTH_LONG).show();
        }catch (SQLiteConstraintException ce){
            hadDevice = true;
            db.updateLastDetectedTime(device);
            System.out.println("Same Device");
        }

        System.out.println("Saved Time: "+device.getLastDetected());

        if(hadDevice){
            long lastDetect = device.getLastDetected();
            device = db.getDevice(device.getDeviceId());
            device.setLastDetected(lastDetect);
        }

        return device;
    }

    public void notifyUser(Device device){

        //TODO notify user

    }

    public boolean getIsFinishedDiscovery(){
        return this.isFinishedDiscovery;
    }

    public List<Device> getAvailableDevices(){
        return  foundDevices;
    }
}
