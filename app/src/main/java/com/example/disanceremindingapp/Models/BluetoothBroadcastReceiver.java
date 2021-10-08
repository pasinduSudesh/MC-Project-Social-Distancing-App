package com.example.disanceremindingapp.Models;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.disanceremindingapp.Activities.MainActivity;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    ArrayList<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)) {
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
            saveDeviceDetails2LocalStorage(context, device, rssi);
        }

        if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
            // if no devices found in a search
        }
    }

    public void saveDeviceDetails2LocalStorage(Context context, BluetoothDevice device, int rssi){
//        SharedPreferences sharedPreferences = context.getSharedPreferences("devices", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("keyString", "valueString");
//        editor.commit();
        Toast.makeText(context, "name: " + device.getName() + " " + device.getAddress(), Toast.LENGTH_LONG).show();
        /**
         * Have to implement save devices in local storage
         */
    }
}
