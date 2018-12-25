package com.example.alifatemeh.onlineshop;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.alifatemeh.onlineshop.Models.MypreferenceManager;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }
        if(MypreferenceManager.getInstance(MainActivity.this).getAccessToken() != null){
            openOnlineShopFragments();
        }else {
            openLoginFragment();
        }

    }

    public void findview(){
        tabLayout=findViewById(R.id.tab_layout);
        viewPager=findViewById(R.id.view_pager);
    }

    public void openLoginFragment(){

        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,loginFragment)
                .commit();

    }
    public void openOnlineShopFragments(){
        String[] titles = {"R.drawable.ic_person_black_24dp","R.drawable.ic_shopping_cart_black_24dp","R.drawable.ic_shop_black_24dp"};
        Log.d("TAG", "login ok " );
        myFragmentPagerAdapter =new MyFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
        //connect tablayout to viewpager
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(2);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_person_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_shopping_cart_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_shop_black_24dp);

    }
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(broadcastReceiver);
        LocalBroadcastManager.getInstance(MainActivity.this).unregisterReceiver(broadcastReceivermessage);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                broadcastReceiver,new IntentFilter("login_ok")
        );
        LocalBroadcastManager.getInstance(MainActivity.this).registerReceiver(
                broadcastReceivermessage,new IntentFilter("intent_ok")
        );
    }

    //define an object of type registerUserCallback

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //delet afther fragmnet
            getSupportFragmentManager().popBackStack();
            //add new fragment
            openOnlineShopFragments();

        }
    };
    private BroadcastReceiver broadcastReceivermessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            openOnlineShopFragmentsxxxxxx();

        }
    };
    public void openOnlineShopFragmentsxxxxxx(){

//        RoomMessageFragment roomMessageFragment=new RoomMessageFragment();
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_container,roomMessageFragment)
//                .addToBackStack(null)
//                .commit();
//        Log.d("TAG", "Id_room_pass : "+
//                MypreferenceManager.getInstance(this).getIdRoomMassege() );
    }
}
