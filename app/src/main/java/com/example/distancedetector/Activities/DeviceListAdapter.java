package com.example.distancedetector.Activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.distancedetector.Models.*;
import com.example.distancedetector.R;

import java.util.ArrayList;

/**
 * Device List View
 * Show device list which are detected
 */

public class DeviceListAdapter extends ArrayAdapter {
    ArrayList<Device> devices;
    public DeviceListAdapter(Context context, int layout, ArrayList<Device> devices) {
        super(context, layout);
        this.devices = devices;
    }

    public class ViewHolder{
        TextView deviceName;
        TextView deviceAddress;
        TextView distance;
        Button action;
    }

    @Override
    public int getCount() {
        return this.devices.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       View row;
       row = convertView;
       ViewHolder viewHolder;

       if(row==null){
           row = LayoutInflater.from(getContext()).inflate(R.layout.device_list_view, parent,false);
           viewHolder = new ViewHolder();
           viewHolder.deviceName = row.findViewById(R.id.deviceName);
           viewHolder.deviceAddress = row.findViewById(R.id.deviceAddress);
           viewHolder.distance = row.findViewById(R.id.distance);
           viewHolder.action = row.findViewById(R.id.action);
           row.setTag(viewHolder);
       }else{
           viewHolder = (ViewHolder)row.getTag();
       }

       viewHolder.deviceName.setText(devices.get(position).getDeviceName());
       viewHolder.deviceAddress.setText(devices.get(position).getDeviceId());
       viewHolder.distance.setText("1");
       if(devices.get(position).isSafeDevice()){
           viewHolder.action.setText("Safe");
       }else{
           viewHolder.action.setText("not");
       }

       return row;
    }
}
