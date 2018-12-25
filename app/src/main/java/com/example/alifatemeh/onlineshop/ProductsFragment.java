package com.example.alifatemeh.onlineshop;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alifatemeh.onlineshop.Data.OnSelectedListener;
import com.example.alifatemeh.onlineshop.Data.OnlineShopAPI;
import com.example.alifatemeh.onlineshop.Data.ProductsController;
import com.example.alifatemeh.onlineshop.Models.MypreferenceManager;
import com.example.alifatemeh.onlineshop.Models.Products;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductsFragment extends Fragment {
    private RecyclerView products;
    private List<Products> productsList=new ArrayList<>();
    private ProductAdapter productAdapter;
    private ProgressBar progressBar;
    private TextView progressUpdate;
    private TextView idRoom;
    private OnSelectedListener onSelectedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.template_product,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        View viewtemp =LayoutInflater.from(viewG.getContext())
//                .inflate(R.layout.template_room,viewGroup,false);
        findview(view);
        initRoomList();
        //macke object of roomController Class
        ProductsController productsController = new ProductsController(productsCallback);
        productsController.strat("bearer "+MypreferenceManager.getInstance(getActivity()).getAccessToken());
        Log.d("Tag","roomList " + productsList.size());
    }

    private void initRoomList(){
        productAdapter=new ProductAdapter(productsList,getActivity());
        products.setLayoutManager(new LinearLayoutManager(getActivity()));
        products.setAdapter(productAdapter);
//        idRoom=rooms.findViewById(R.id.name);
    }

    //find view in items viewfragment
    public void findview(View view){
        products=view.findViewById(R.id.products);
        progressBar=view.findViewById(R.id.progress_bar);
        progressUpdate=view.findViewById(R.id.progressUpdate);
    }

    // Call list rooms get of server by API
    OnlineShopAPI.GetProductsCallback productsCallback = new OnlineShopAPI.GetProductsCallback() {
        @Override
        public void onResponse(List<Products> inputList) {

            Log.d("Tag","roomList " +productsList.size());

            new SortRoomsAsync(inputList).execute();
        }

        @Override
        public void onFailure(String cause) {

        }
    };


    //sort list rooms
    private class SortRoomsAsync extends AsyncTask<Void ,Integer ,Boolean> {
        List<Products> products;

        public SortRoomsAsync(List<Products> products) {
            this.products = products;
        }

        //sort list rooms in thread
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            for (int i=0;i<400000;i++){
                Collections.sort(products, new Comparator<Products>() {
                    @Override
                    public int compare(Products x, Products y) {
                        return x.getName().compareTo(y.getName());
                    }
                });
                if(i%1000000==0){
                    Log.d("TAG","here "+i );
                    publishProgress(i/10000);
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressUpdate.setText(getString(R.string.progress_update_template,values[0],40));
        }

        @Override
        protected void onPostExecute(Boolean successful) {
            super.onPostExecute(successful);
            progressBar.setVisibility(View.INVISIBLE);
            productsList.clear();
            productsList.addAll(this.products);
            productAdapter.notifyDataSetChanged();
            progressUpdate.setVisibility(View.INVISIBLE);


        }

    }


}
