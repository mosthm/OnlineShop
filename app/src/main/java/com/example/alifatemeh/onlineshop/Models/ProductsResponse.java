package com.example.alifatemeh.onlineshop.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsResponse {
    @SerializedName("results")
    List<Products> products;

    public ProductsResponse() {
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}
