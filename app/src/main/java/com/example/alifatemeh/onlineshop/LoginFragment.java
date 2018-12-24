package com.example.alifatemeh.onlineshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alifatemeh.onlineshop.Data.LoginUserController;
import com.example.alifatemeh.onlineshop.Data.OnlineShopAPI;
import com.example.alifatemeh.onlineshop.Models.MypreferenceManager;
import com.example.alifatemeh.onlineshop.Models.TokenResponse;

import static android.content.ContentValues.TAG;

public class LoginFragment extends Fragment {
    private TextView linksignup;
    private TextView btnlogin;
    private EditText username;
    private EditText password;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configureViews();
    }

    private void findViews(View view){
        linksignup= view.findViewById((R.id.link_signup));
        btnlogin= view.findViewById((R.id.btn_login));
        username= view.findViewById((R.id.input_username));
        password= view.findViewById((R.id.input_password));
    }
    private void configureViews(){
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        linksignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignuFragment signuFragment = new SignuFragment();
                getFragmentManager().beginTransaction()
                        .add(R.id.fragment_container,signuFragment)
                        .addToBackStack(null)
                        .commit();
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }



    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btnlogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String username1 = username.getText().toString();
        String password1 = password.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onLoginSuccess() {
        btnlogin.setEnabled(true);
        loginUser();
//        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();

        btnlogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username1 = username.getText().toString();
        String password1 = password.getText().toString();

//        if (username1.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            _emailText.setError("enter a valid email address");
//            valid = false;
//        } else {
//            _emailText.setError(null);
//        }
        if (username1.isEmpty() || username1.length() < 4 || username1.length() > 10) {
            username.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            username.setError(null);
        }
        if (password1.isEmpty() || password1.length() < 4 || password1.length() > 10) {
            password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }



    private void loginUser(){
        LoginUserController loginUserController = new LoginUserController(loginUserCallback);
        loginUserController.start(
                username.getText().toString(),
                password.getText().toString()
        );

    }

    OnlineShopAPI.LoginUserCallback loginUserCallback =new OnlineShopAPI.LoginUserCallback() {
        @Override
        public void onResponse(boolean successful, String errorDescription, TokenResponse tokenResponse) {
            if(successful){
                Toast.makeText(getActivity(),"Login Successfull " + tokenResponse.getAccessToken() ,Toast.LENGTH_SHORT).show();

                MypreferenceManager.getInstance(getActivity()).putUsername(username.getText().toString());
                MypreferenceManager.getInstance(getActivity()).putAccessToken(tokenResponse.getAccessToken());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(  new Intent("login_ok") );
//                loginUser();

            }else {
                //Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(String cause) {

        }
    };
}
