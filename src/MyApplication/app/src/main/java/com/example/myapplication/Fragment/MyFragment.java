package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.LauchGoodActivity;
import com.example.myapplication.R;
import com.example.myapplication.addGoods;
import com.example.myapplication.entity.User;
import androidx.viewpager.widget.ViewPager;

/**
 * @Author Xuejie Dong u7602840
 * The MyFragment
 */
public class MyFragment extends Fragment {
    private TextView useremail;
    private TextView logOut;
    private Button goodsButton;
    private Button add_button;

    // Create and return the view hierarchy's UI
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my, container, false);
        useremail = rootView.findViewById(R.id.emailTextView);
        // Set the user's email address if provided in arguments
        if (getArguments() != null) {
            String e = getArguments().getString("email");
            useremail.setText(e);
        }

        // Set up a click listener for the cart button
        Button cartButton = rootView.findViewById(R.id.cart);
        cartButton.setOnClickListener(v -> {
            ViewPager viewPager = getActivity().findViewById(R.id.viewpageer);
            if (viewPager != null) {
                viewPager.setCurrentItem(1);
            }
        });
        goodsButton = rootView.findViewById(R.id.goods);

        goodsButton.setOnClickListener(v -> {
            // Navigate to the "LauchGoodActivity" when the "Goods" button is clicked
            Intent launchGoodActivityIntent = new Intent(getActivity(), LauchGoodActivity.class);
            startActivity(launchGoodActivityIntent);
        });

        logOut = rootView.findViewById(R.id.log_out);
        logOut.setOnClickListener(v -> {
            // Pop the current fragment from the back stack
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        });
        add_button= rootView.findViewById(R.id.add_good);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the "addGoods" activity when the "Add Good" button is clicked
                Intent intent = new Intent(requireContext(), addGoods.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    // Handle the onResume event
    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                return true;
            }
            return false;
        });
    }
    // Set the user's information in the fragment
    public void setUser(User user) {
        if (user != null && useremail != null) {
            String userInfo = user.getEmail();
            useremail.setText(userInfo);
        }
    }

}