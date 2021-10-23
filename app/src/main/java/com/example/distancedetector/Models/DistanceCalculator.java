package com.example.distancedetector.Models;

/**
 * For calculate the distance from RSSI
 */

public class DistanceCalculator {

    public double calculateDistance(int rssi){
        double distance = 10 ^ ((-69 - rssi)/20);
        return distance;
    }
}
