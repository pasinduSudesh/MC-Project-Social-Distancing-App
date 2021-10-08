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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothManager bluetoothManager =  (BluetoothManager) getSystemService(MainActivity.this.BLUETOOTH_SERVICE);
        this.BTAdapter = bluetoothManager.getAdapter();

        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {

            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
        }

        scanButton = (Button) findViewById(R.id.button2);
//        listView = (ListView) findViewById(R.id.listView);

        scanButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                BTAdapter.startDiscovery();
                System.out.println("helollllll");
                Toast.makeText(MainActivity.this, "Scanning Devices", Toast.LENGTH_LONG).show();
            }
        });

        IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
//        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, intentFilter);
        System.out.println("helollllllxasxax vs dsd");
//
//        arrayAdapter= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);
//        listView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    BroadcastReceiver receiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
            // When discovery finds a new device
//            Toast.makeText(MainActivity.this, action, Toast.LENGTH_LONG).show();
//            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
//                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
//                Toast.makeText(MainActivity.this, state, Toast.LENGTH_LONG).show();
//            }
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                float d = 10 ^ ((-69 - rssi)/20);
                Toast.makeText(MainActivity.this, "name: " + device.getName() + " " + device.getAddress() + "distance: "+ d, Toast.LENGTH_LONG).show();
                if (!foundDevices.contains(device)) {
                    foundDevices.add(device);

//                    btArrayAdapter.notifyDataSetChanged();
                }

            }

            // When discovery cycle finished
            if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (foundDevices == null || foundDevices.isEmpty()) {
                    Toast.makeText(MainActivity.this, "No Devices", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
}