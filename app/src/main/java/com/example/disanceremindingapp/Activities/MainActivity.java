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
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.disanceremindingapp.Models.BluetoothBroadcastReceiver;
import com.example.disanceremindingapp.Models.BluetoothScanService;
import com.example.disanceremindingapp.Models.BluetoothScanner;
import com.example.disanceremindingapp.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Elements in the screen
    Button scanButton;
    Button searchButton;
    ListView deviceListVew;

    private boolean scanStarted = false;
    private long scanStartTime;
    private Thread deviceSearchTread = null;
    private ArrayList<Device>  detectedDevices = new ArrayList<Device>();


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

        //Initialize the elements
        scanButton = (Button) findViewById(R.id.button2);
        searchButton = (Button) findViewById(R.id.search);
        deviceListVew = findViewById(R.id.deviceList);

        //Initialize the view list
        deviceListVew.setAdapter(new DeviceListAdapter(MainActivity.this, R.layout.device_list_view, detectedDevices));

        //OnClickListener for scan button
        scanButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(scanStarted){
                    //scan is started
                    scanButton.setText("Scan");
                    detectedDevices = new ArrayList<Device>();
                    scanStarted = false;
                    stopService(new Intent(MainActivity.this, BluetoothScanService.class));
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
                        ComponentName cn =  startService(new Intent(MainActivity.this, BluetoothScanService.class));

                        deviceSearchTread = new Thread(){
                            @Override
                            public void run() {

                                while (true){
                                    detectedDevices = getDetectedDeviceList();

                                    MainActivity.this.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            deviceListVew.setAdapter(new DeviceListAdapter(MainActivity.this, R.layout.device_list_view, detectedDevices));
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
                        Toast.makeText(MainActivity.this, "Please turn on Bluetooth", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deviceListVew.setAdapter(new DeviceListAdapter(MainActivity.this, R.layout.device_list_view, detectedDevices));
            }
        });


    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    //check the bluetooth is turn on
    public boolean checkBluetooth(){
        //TODO check bluetooth is enabled
        return true;
    }

    //Get detected list from local DB
    public ArrayList<Device> getDetectedDeviceList(){
        return  new LocalDB(MainActivity.this).getDetectedDevices(this.scanStartTime);
    }






}