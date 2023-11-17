package com.example.myapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.Fragment.HomePage;
import com.example.myapplication.Fragment.LoginFragment;
import com.example.myapplication.Fragment.MyFragment;
import com.example.myapplication.Fragment.page1Fragment;
import com.example.myapplication.Fragment.page2Fragment;
import com.example.myapplication.entity.User;
/**@author Xuejie Dong u7602840
 * manage the fragment pages
 * */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {//Control 4 fragments on the home page
    private  int PAGER_COUNT = 4;
    private HomePage homePage = null;
    private page1Fragment page1_Fragment= null;
    private page2Fragment page2_Fragment = null;
    private LoginFragment loginFragment = null;
    private MyFragment myFragment=null;
    public MyFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        homePage = new HomePage();
        page1_Fragment=new page1Fragment();
        page2_Fragment=new page2Fragment();
        loginFragment=new LoginFragment();
        myFragment=new MyFragment();

    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }//Other pages are released when switching pages

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = homePage;
                break;
            case MainActivity.PAGE_TWO:
                fragment = page1_Fragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = page2_Fragment;
                break;
            case MainActivity.PAGE_FOUR:
               fragment=loginFragment;
                break;
        }
        return fragment;
    }


}
