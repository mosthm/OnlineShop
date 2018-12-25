package com.example.alifatemeh.onlineshop;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    String[] titles = {"Profile","Cart","Products"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        if (i==0){
            return new ProfileFragment();
        }else {
            if(i==1) {
                return new CartFrafgment();
            }else {
                return new ProductsFragment();
            }
        }
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }
}
