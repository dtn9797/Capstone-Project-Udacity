package com.example.duynguyen.amashop.utils;

import com.example.duynguyen.amashop.model.Product;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by duynguyen on 5/16/18.
 */

public interface RetrofitInterface {
    @GET("/api/v1/products.json?product_tags=Canadian&product_type=nail_polish")
    Call<List<Product>> get_products(@Query("product_type") String productType);

    @GET("/api/v1/products.json?product_tags=Canadian&product_type=nail_polish")
    Call<List<Product>> get_products_by_tag(@Query("product_type") String productType, @Query("product_tags") String productTag);

}
