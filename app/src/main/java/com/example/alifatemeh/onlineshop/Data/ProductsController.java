package com.example.alifatemeh.onlineshop.Data;

import android.util.Log;

import com.example.alifatemeh.onlineshop.Models.ProductsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductsController {
    OnlineShopAPI.GetProductsCallback productsCallback;

    public ProductsController(OnlineShopAPI.GetProductsCallback productsCallback) {
        this.productsCallback = productsCallback;
    }

    public void strat(String authorization){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(OnlineShopAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();





        OnlineShopAPI onlineShopAPI=retrofit.create(OnlineShopAPI.class);
        Call<ProductsResponse> call=onlineShopAPI.getProducts(authorization);
        Log.d("Tag","strat " + authorization );
        call.enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                Log.d("Tag","onResponse " + response.body() );
                if(response.isSuccessful()){
                    productsCallback.onResponse(response.body().getProducts());
                }else {}
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                Log.d("Tag","onFailure " + t.getMessage() );
                productsCallback.onFailure(t.getMessage());
            }
        });
    }
}
