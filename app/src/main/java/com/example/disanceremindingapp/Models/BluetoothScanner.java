package com.example.disanceremindingapp.Models;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import static android.content.Context.BLUETOOTH_SERVICE;

public class BluetoothScanner {
    Context context;
    BluetoothAdapter bluetoothAdapter;
    BroadcastReceiver broadcastReceiver;
    LocalBroadcastManager localBroadcastManager;

    BluetoothScanner() {
        this.bluetoothAdapter = null;
        this.context = null;
    }

    public BluetoothScanner(Context context) {
        this.context = context;
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
        this.broadcastReceiver = new BluetoothBroadcastReceiver();
        this.context.registerReceiver(this.broadcastReceiver, intentFilter);
    }

    public void unregisterBluetoothReceiver(){
        System.out.println("************Unregistered**");
        if (this.broadcastReceiver != null){
            this.context.unregisterReceiver(this.broadcastReceiver);
        }
    }


}
