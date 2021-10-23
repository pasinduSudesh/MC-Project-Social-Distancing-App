package com.example.distancedetector.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class LocalDB extends SQLiteOpenHelper  {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "device_storage";
    private static final String TABLE_DEVICES= "devices";
    private static final String ID = "id";
    private static final String DEVICE_NAME = "device_name";
    private static final String IS_SAFE = "is_safe";
    private static final String LAST_DETECTED_AT = "last_detected_time";
    private static final String DISTANCE = "distance";

    public LocalDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION );
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createDB = "CREATE TABLE " + TABLE_DEVICES + "("
                + ID + " TEXT PRIMARY KEY," + DEVICE_NAME + " TEXT,"
                + IS_SAFE + " TEXT,"+ LAST_DETECTED_AT+ " INTEGER,"
                + DISTANCE+ " TEXT "+ ")";
        db.execSQL(createDB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEVICES);
        // Create tables again
        onCreate(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public void addDevices(Device device){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID, device.getDeviceId());
        values.put(DEVICE_NAME, device.getDeviceName());
        values.put(IS_SAFE, device.isSafeDevice());
        values.put(LAST_DETECTED_AT, device.getLastDetected());
        values.put(DISTANCE, device.getDistance());
        db.insertOrThrow(TABLE_DEVICES, null, values);

        db.close();

    }

    public Device getDevice(String id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DEVICES, new String[] { ID,
                        DEVICE_NAME, IS_SAFE, LAST_DETECTED_AT, DISTANCE }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
//        db.close();
        if (cursor != null){
            cursor.moveToFirst();
            System.out.println("get device time " + cursor.getLong(3));
            Device device = new Device(
                                        cursor.getString(0),
                                        cursor.getString(1),
                                        Integer.parseInt(cursor.getString(2)),
                                        cursor.getLong(3),
                                        Double.parseDouble(cursor.getString(4))
                                        );
            return device;
        }else{
            return new Device(null, null, 0,0);
        }





    }

    public List<Device> getAllDevices(){

        List<Device> contactList = new ArrayList<Device>();

        String selectQuery = "SELECT  * FROM " + TABLE_DEVICES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
//        db.close();

        if (cursor.moveToFirst()) {
            do {
                Device device = new Device(
                                            cursor.getString(0),
                                            cursor.getString(1),
                                            Integer.parseInt(cursor.getString(2)),
                                            cursor.getLong(3),
                                            Double.parseDouble(cursor.getString(4))
                                            );

                // Adding contact to list
                contactList.add(device);
            } while (cursor.moveToNext());
        }

        return contactList;


    }

    public int updateDeviceIsSafeState(Device device){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int isSafe = 0;
        if(device.isSafeDevice()){
            isSafe = 1;
        }
        values.put(IS_SAFE, isSafe);

        return db.update(TABLE_DEVICES, values, ID + " = ?",
                new String[] { String.valueOf(device.getDeviceId()) });

    }

    public int updateLastDetectedTime(Device device){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(LAST_DETECTED_AT, device.getLastDetected());

        int state = db.update(TABLE_DEVICES, values, ID + " = ?",
                new String[] { String.valueOf(device.getDeviceId()) });
//        db.close();

        return state;

    }

    public ArrayList<Device> getDetectedDevices(long time){

        ArrayList<Device> deviceList = new ArrayList<Device>();

        String selectQuery = "SELECT  * FROM " + TABLE_DEVICES + " WHERE " + LAST_DETECTED_AT + " > "+ time;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
//        db.close();

        if (cursor.moveToFirst()) {
            do {
                Device device = new Device(
                                            cursor.getString(0),
                                            cursor.getString(1),
                                            Integer.parseInt(cursor.getString(2)),
                                            cursor.getLong(3),
                                            Double.parseDouble(cursor.getString(4))
                                            );

                // Adding contact to list
                deviceList.add(device);
            } while (cursor.moveToNext());
        }

        return deviceList;
    }
}
