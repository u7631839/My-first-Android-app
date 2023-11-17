package com.example.myapplication.Location_tool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;



public class LocationTool {
    private static final double EARTH_RADIUS = 6371;

    /**
     * 计算两个点之间的距离（通过其经纬度）
     *
     * @param lat1 lat of the first point
     * @param lon1 lon of the firest point
     * @param lat2 lat of the second point
     * @param lon2 lon of the second point
     * @return the distance between 2 point
     * @author Shiyu Pan
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // convert lat and lon into Radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // compute the difference
        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;

        // Haversine formula
        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // distance between the point
        return EARTH_RADIUS * c;
    }
}

