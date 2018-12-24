package com.example.alifatemeh.onlineshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        LoginFragment loginFragment = new LoginFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,loginFragment)
                .commit();
    }
}
