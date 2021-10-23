package com.example.distancedetector.Models;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BluetoothScanService extends Service {

    BluetoothAdapter BTAdapter ;
    BluetoothScanner btScanner;
    Thread btScannerThread;

//    public void BluetoothScanService(){
//        System.out.println("constructor workinggggg");
//    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.btScanner = new BluetoothScanner(this);
        this.BTAdapter = this.btScanner.getBluetoothAdapter();
        this.btScanner.registerBluetoothReceiver();


        this.btScannerThread = new Thread(){
            @Override
            public void run() {
                try {
                    scann();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        this.btScannerThread.start();

        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.btScanner.unregisterBluetoothReceiver();
        this.btScannerThread.interrupt();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void scann() throws InterruptedException {
        while (true){
            if (this.btScanner.getIsFinishedDiscovery()){
                this.BTAdapter.startDiscovery();
                System.out.println("Start Discovery");
            }
            Thread.sleep(10*1000);

        }
    }

    public void aaa(){

    }
}
