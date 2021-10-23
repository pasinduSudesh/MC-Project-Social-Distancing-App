package com.example.distancedetector.Models;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.List;

import static android.content.Context.BLUETOOTH_SERVICE;

public class BluetoothScanner {
    Context context;
    BluetoothAdapter bluetoothAdapter;
    BluetoothBroadcastReceiver  broadcastReceiver;
    LocalBroadcastManager localBroadcastManager;
    LocalDB localDB;


    BluetoothScanner() {
        this.bluetoothAdapter = null;
        this.context = null;
    }

    public BluetoothScanner(Context context) {
        this.context = context;
        this.localDB = new LocalDB(this.context);
    }

    public BluetoothAdapter getBluetoothAdapter(Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(BLUETOOTH_SERVICE);
        this.bluetoothAdapter = bluetoothManager.getAdapter();
        return this.bluetoothAdapter;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        BluetoothManager bluetoothManager = (BluetoothManager) this.context.getSystemService(BLUETOOTH_SERVICE);
        this.bluetoothAdapter = bluetoothManager.getAdapter();
        return this.bluetoothAdapter;
    }

    public void registerBluetoothReceiver() {
        System.out.println("************Registered*********");
        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.broadcastReceiver = new BluetoothBroadcastReceiver(localDB);
        this.context.registerReceiver(this.broadcastReceiver, intentFilter);
    }

    public void unregisterBluetoothReceiver(){
        System.out.println("************Unregistered**");
        if (this.broadcastReceiver != null){
            this.context.unregisterReceiver(this.broadcastReceiver);
        }
        this.localDB.close();
    }

    public boolean getIsFinishedDiscovery(){
        return this.broadcastReceiver.getIsFinishedDiscovery();
    }

    public List<Device> getAvailableDevices(){
        return this.broadcastReceiver.getAvailableDevices();
    }


}
