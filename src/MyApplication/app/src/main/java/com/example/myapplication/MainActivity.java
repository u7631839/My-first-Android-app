package com.example.myapplication;


import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
/**@author all
 * this activity is the mainactivity and we initialize some parameters at here
 * */
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener{
    //fragment button
    private TextView txt_topbar;
    private RadioGroup tab;
    private RadioButton home_button;
    private RadioButton page1_button;
    private RadioButton page2_button;
    private RadioButton my_button;
    private ViewPager viewpageer;

    private MyFragmentPagerAdapter mAdapter;

    //fragment

    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    //location permission code
    public static final int REQUEST_LOCATION_PERMISSION_CODE = 1;
    public static DatabaseController mDatabase = DatabaseController.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestLocationPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        my_button.setChecked(true);//Default on button
    }

    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        tab = (RadioGroup) findViewById(R.id.tab);
        home_button = (RadioButton) findViewById(R.id.home_button);
        page1_button = (RadioButton) findViewById(R.id.page1_button);
        page2_button = (RadioButton) findViewById(R.id.page2_button);
        my_button = (RadioButton) findViewById(R.id.my_button);
        tab.setOnCheckedChangeListener(this);

        viewpageer = (ViewPager) findViewById(R.id.viewpageer);
        viewpageer.setAdapter(mAdapter);
        viewpageer.setCurrentItem(3);//open it,  see the login page.
        viewpageer.addOnPageChangeListener(this);

    }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.home_button) {
            viewpageer.setCurrentItem(PAGE_ONE);
        } else if (checkedId == R.id.page1_button) {
            viewpageer.setCurrentItem(PAGE_TWO);
        } else if (checkedId == R.id.page2_button) {
            viewpageer.setCurrentItem(PAGE_THREE);
        } else if (checkedId == R.id.my_button) {
            viewpageer.setCurrentItem(PAGE_FOUR);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 2) {
            switch (viewpageer.getCurrentItem()) {
                case PAGE_ONE:
                    home_button.setChecked(true);
                    break;
                case PAGE_TWO:
                    page1_button.setChecked(true);
                    break;
                case PAGE_THREE:
                    page2_button.setChecked(true);
                    break;
                case PAGE_FOUR:
                    my_button.setChecked(true);
                    break;
            }
        }
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                //request for permitted
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION_CODE);
        } else {
            getLocation();
        }
    }

    //ask for the permission if we get the permission, into getlocation()
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                // Permission denied, handle appropriately by informing the user.
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //using device's gps, update all my lat and lon
    private void getLocation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            // You might want to use LOCATION_PROVIDER_NETWORK for non-GPS location
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    MyApplication.my_lat = location.getLatitude();
                    MyApplication.my_lon = location.getLongitude();
                    // Do something with the coordinates
                }
            }, null);
        }
    }
}


