package com.example.disanceremindingapp.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.disanceremindingapp.Models.*;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
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
import com.example.disanceremindingapp.Models.BluetoothScanService;
import com.example.disanceremindingapp.Models.BluetoothScanner;
import com.example.disanceremindingapp.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button scanButton;

    ArrayList<BluetoothDevice> foundDevices = new ArrayList<BluetoothDevice>();
    ArrayAdapter<String> arrayAdapter;


    BluetoothAdapter BTAdapter;
    BluetoothScanner btScanner;
    private Object BluetoothScanService;

    private boolean scanStarted = false;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check permission for location
        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {

            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }


        scanButton = (Button) findViewById(R.id.button2);
        scanButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(scanStarted){
                    //scan is started
                    scanButton.setText("Scan");
                    scanStarted = false;
                    stopService(new Intent(MainActivity.this, BluetoothScanService.class));

                }else{
                    //scan is not started
                    scanButton.setText("Checking Bluetooth");
                    boolean canStartScan = checkBluetooth();

                    if (canStartScan){
                        scanStarted = true;
                        scanButton.setText("Stop Scan");
                        startService(new Intent(MainActivity.this, BluetoothScanService.class));

                    }else{
                        scanStarted = false;
                        scanButton.setText("Scan");
                        Toast.makeText(MainActivity.this, "Please turn on Bluetooth", Toast.LENGTH_LONG).show();
                    }


                }









//                startService(new Intent(MainActivity.this, BluetoothScanService.class));
//                System.out.println("Scanning Devices");
//                Toast.makeText(MainActivity.this, "Scanning Devices", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        this.btScanner.unregisterBluetoothReceiver();
    }

    public boolean checkBluetooth(){
        return true;
    }

}