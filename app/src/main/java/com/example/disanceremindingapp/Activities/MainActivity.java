package com.example.disanceremindingapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.disanceremindingapp.Models.BluetoothBroadcastReceiver;
import com.example.disanceremindingapp.Models.BluetoothScanner;
import com.example.disanceremindingapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button scanButton;
//    ListView listView;
    ArrayList<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    ArrayAdapter<String> arrayAdapter;

//
//    BluetoothManager bluetoothManager =  (BluetoothManager) ;
//    BluetoothAdapter BTAdapteqr = bluetoothManager.getAdapter();
    BluetoothAdapter BTAdapter ;
    BluetoothScanner btSacnner;
    BroadcastReceiver receiver;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btSacnner = new BluetoothScanner(MainActivity.this);
        this.BTAdapter = this.btSacnner.getBluetoothAdapter();



        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {

            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }

        scanButton = (Button) findViewById(R.id.button2);

        scanButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                BTAdapter.startDiscovery();
                System.out.println("Scanning Devices");
                Toast.makeText(MainActivity.this, "Scanning Devices", Toast.LENGTH_LONG).show();
            }
        });

        this.btSacnner.registerBluetoothReceiver();

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        this.btSacnner.unregisterBluetoothReceiver();
    }

}