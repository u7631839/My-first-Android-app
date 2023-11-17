package com.example.myapplication.Fragment;

import static com.example.myapplication.Just_test_UI.Goodtest.cartUIS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;

import com.example.myapplication.Just_test_UI.CartUI;
import com.example.myapplication.R;
import com.example.myapplication.addGoods;
import com.example.myapplication.entity.Cart;
import com.example.myapplication.entity.Good;
import com.example.myapplication.entity.User;
import com.example.myapplication.tools.CartAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The first page fragment
 * @Author Xuejie Dong
 * to convert the good list and ExpandableListView
 */
public class page1Fragment extends Fragment {

    static ExpandableListView expandableListView;
    TextView title;
    private static List<Cart> cartgood;

    private static Context context;
    private static CartAdapter cartAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_page1, container, false);
        context=getContext();
       // title=rootview.findViewById(R.id.textView);
        expandableListView=rootview.findViewById(R.id.elv_shopping_car);
        initExpandableListView();
        initgoodlist();
        return rootview;
    }
    // Initialize the list of goods
    public static void initgoodlist(){
        if(LoginFragment.loginUser==null){
            // Display a message if the user is not logged in
            Toast.makeText(context, "please log in first", Toast.LENGTH_SHORT).show();
            System.out.println("loginUser是null");
        }else {
            // Get the user's cart and initialize the ExpandableListView data
        cartgood = LoginFragment.loginUser.getCartList();
       // System.out.println("initgoodlist");
        initExpandableListViewData(cartgood); }
    }

    // Initialize the ExpandableListView
    public static void initExpandableListView(){
        cartAdapter=new CartAdapter(context);
        expandableListView.setAdapter(cartAdapter);
    }

    // Initialize the data for the ExpandableListView
     private static void initExpandableListViewData(List<Cart>cartgood){
        if (cartgood!=null&&cartgood.size()>0){
            cartAdapter.setData(cartgood);
            for (int i=0;i<cartAdapter.getGroupCount();i++){
                expandableListView.expandGroup(i);
            }
            expandableListView.setVisibility(View.VISIBLE);
            Log.d("CartAdapter","expandableListView可以显示出来");
        }
        else {
            expandableListView.setVisibility(View.GONE);
            Log.d("CartAdapter","expandableListView不让显示");

        }
    }
}

