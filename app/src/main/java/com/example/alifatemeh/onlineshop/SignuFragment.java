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
import com.example.alifatemeh.onlineshop.Data.RegisterUserController;
import com.example.alifatemeh.onlineshop.Models.MypreferenceManager;
import com.example.alifatemeh.onlineshop.Models.TokenResponse;
import com.example.alifatemeh.onlineshop.Models.User;

import butterknife.BindView;

import static android.content.ContentValues.TAG;

public class SignuFragment extends Fragment {
    private TextView linklogin;
    private TextView btnsignup;
    private EditText nameText;
    private EditText mobileText;
    private EditText passwordText;
    private EditText addressText;
    private EditText emailText;
    private EditText reEnterPasswordText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
        configureViews();
    }

    private void findViews(View view){
        linklogin= view.findViewById((R.id.link_login));
        btnsignup= view.findViewById((R.id.btn_signup));
        nameText= view.findViewById((R.id.input_name));
//        mobileText= view.findViewById((R.id.input_mobile));
        passwordText= view.findViewById((R.id.input_password));
        reEnterPasswordText= view.findViewById((R.id.input_reEnterPassword));
//        addressText= view.findViewById((R.id.input_address));
        emailText= view.findViewById((R.id.input_email));
    }

    private void configureViews(){
        linklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginFragment loginFragment = new LoginFragment();
                getFragmentManager().beginTransaction()
                        .add(R.id.fragment_container,loginFragment)
                        .commit();
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btnsignup.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = nameText.getText().toString();
//        String address = addressText.getText().toString();
        String email = emailText.getText().toString();
//        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        btnsignup.setEnabled(true);

        RegisterUserController userController=new RegisterUserController(registerUserCallback);
        User user = new User();
        user.setUsername(nameText.getText().toString());
        user.setPassword(passwordText.getText().toString());
        user.setEmail(emailText.getText().toString());
        userController.start(user);
//        setResult(RESULT_OK, null);
//         finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getActivity(), "Login failed", Toast.LENGTH_LONG).show();

        btnsignup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
//        String address = addressText.getText().toString();
        String email = emailText.getText().toString();
//        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

//        if (address.isEmpty()) {
//            addressText.setError("Enter Valid Address");
//            valid = false;
//        } else {
//            addressText.setError(null);
//        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

//        if (mobile.isEmpty() || mobile.length()!=10) {
//            mobileText.setError("Enter Valid Mobile Number");
//            valid = false;
//        } else {
//            mobileText.setError(null);
//        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }

    private void loginUser(){
        LoginUserController loginUserController = new LoginUserController(loginUserCallback);
        loginUserController.start(
                nameText.getText().toString(),
                passwordText.getText().toString()
        );

    }

    OnlineShopAPI.LoginUserCallback loginUserCallback =new OnlineShopAPI.LoginUserCallback() {
        @Override
        public void onResponse(boolean successful, String errorDescription, TokenResponse tokenResponse) {
            if(successful){
                Toast.makeText(getActivity(),"Login Successfull " + tokenResponse.getAccessToken() ,Toast.LENGTH_SHORT).show();
                loginUser();
                MypreferenceManager.getInstance(getActivity()).putUsername(nameText.getText().toString());
                MypreferenceManager.getInstance(getActivity()).putAccessToken(tokenResponse.getAccessToken());
                LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(  new Intent("login_ok") );

            }else {
                //Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(String cause) {

        }
    };
    OnlineShopAPI.RegisterUserCallback registerUserCallback = new OnlineShopAPI.RegisterUserCallback() {
        @Override
        public void onResponse(boolean successful,String errorMessage,User user) {
            if(successful){
                Log.d("TAG", "onResponse" + errorMessage);
                Toast.makeText(getActivity(),"Done " + user.getUsername(),Toast.LENGTH_SHORT).show();
                loginUser();
            }else {
                Toast.makeText(getActivity(),errorMessage,Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onFailure(String cause) {
//            Toast.makeText(getActivity(),cause.getUsername(),Toast.LENGTH_SHORT).show();
            Log.d("TAG", "onResponse" + cause);
        }
    };

}
