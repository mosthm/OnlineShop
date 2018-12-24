package com.example.alifatemeh.onlineshop.Data;

import com.example.alifatemeh.onlineshop.Models.TokenResponse;
import com.example.alifatemeh.onlineshop.Models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OnlineShopAPI {

    String BASE_URL = "https://api.backtory.com/";

    //******************************Register User***************************************************

    @Headers("X-Backtory-Authentication-Id: 5a154d2fe4b03ffa0436a534")
    @POST("auth/users")
    Call<User> registerUser(@Body User user);

    interface RegisterUserCallback{
        void onResponse(boolean successful,String errorMessage,User user);
        void onFailure(String cause);
    }


    //*****************************Login User*******************************************************

    @Headers({
            "X-Backtory-Authentication-Id: 5a154d2fe4b03ffa0436a534",
            "X-Backtory-Authentication-Key:57bdf2629df04f46a31de972"
    })
    @FormUrlEncoded
    @POST("auth/login")
    Call<TokenResponse> loginUser(
            @Field("username") String username,
            @Field("password") String password
    );

    interface LoginUserCallback{
        void onResponse(boolean successful,String errorDescription,TokenResponse tokenResponse);
        void onFailure(String cause);
    }

}
