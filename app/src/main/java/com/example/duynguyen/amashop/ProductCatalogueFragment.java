package com.example.duynguyen.amashop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duynguyen.amashop.model.Product;
import com.example.duynguyen.amashop.utils.RetrofitClient;
import com.example.duynguyen.amashop.utils.RetrofitInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCatalogueFragment extends Fragment {
    public String TAG = ProductCatalogueFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        loadProductData();
        View view = inflater.inflate(R.layout.fragment_product_catalogue,container,false);
        return view;
    }

    private void loadProductData () {
        RetrofitInterface retrofitInterface = new RetrofitClient().getClient().create(RetrofitInterface.class);
        Call<List<Product>> call= retrofitInterface.get_products("nail_polish");
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                Log.d(TAG,"Successfully get data ");
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.d(TAG,"Fail to get data ");
            }
        });
    }
}
