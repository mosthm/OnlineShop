package com.example.alifatemeh.onlineshop.Data;

import com.example.alifatemeh.onlineshop.Models.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginUserController {
    OnlineShopAPI.LoginUserCallback loginUserCallback;

    public LoginUserController(OnlineShopAPI.LoginUserCallback loginUserCallback) {
        this.loginUserCallback = loginUserCallback;
    }
    public void start(String username,String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShopAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        OnlineShopAPI chatRoomAPI=retrofit.create(OnlineShopAPI.class);
        Call<TokenResponse> call=chatRoomAPI.loginUser(username,password);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if(response.isSuccessful()){
                    loginUserCallback.onResponse(true,null,response.body());
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {

            }
        });

    }
}
