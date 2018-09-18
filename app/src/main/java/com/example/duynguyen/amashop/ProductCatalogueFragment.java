package com.example.duynguyen.amashop;


import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.duynguyen.amashop.model.Product;
import com.example.duynguyen.amashop.utils.NavigationHost;
import com.example.duynguyen.amashop.utils.NavigationIconClickListener;
import com.example.duynguyen.amashop.utils.ProductGridItemDecoration;
import com.example.duynguyen.amashop.utils.ProductsAdapter;
import com.example.duynguyen.amashop.utils.RetrofitClient;
import com.example.duynguyen.amashop.utils.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCatalogueFragment extends Fragment {
    public String TAG = ProductCatalogueFragment.class.getSimpleName();
    private List<Product> data = new ArrayList<>();
    public ProductsAdapter mProductsAdapter;

    @BindView(R.id.product_grid)
    LinearLayout mProductGrid;
    @BindView(R.id.products_rv)
    RecyclerView mProductsRv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_catalogue, container, false);
        ButterKnife.bind(this, view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL,false);
        mProductsAdapter = new ProductsAdapter(getContext());
        ProductGridItemDecoration productGridItemDecoration = new ProductGridItemDecoration(80, 50);

        mProductsRv.setLayoutManager(gridLayoutManager);
        mProductsRv.addItemDecoration(productGridItemDecoration);
        mProductsRv.setAdapter(mProductsAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mProductGrid.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.product_grid_background_shape));
        }

        loadProductData();
        setUpToolbar(view);
        ((Button)view.findViewById(R.id.polish_brand_0_bt)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Button is clicked");
            }
        });
        return view;
    }

    private void setUpToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(getContext(), view.findViewById(R.id.product_grid)));
    }

    private void loadProductData() {
        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<List<Product>> call = retrofitInterface.get_products("nail_polish");
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                data = response.body();
                mProductsAdapter.setData(data);
                Log.d(TAG, "Successfully get data ");

            }

            @Override
            public void onFailure(@NonNull Call<List<Product>> call, @NonNull Throwable t) {
                //Show alert dialog
                Log.e("Error", "Error in retrofit");
                AlertDialog.Builder dialog = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                dialog.setCancelable(false);
                dialog.setTitle(getString(R.string.connection_error_title));
                dialog.setMessage(getString(R.string.connection_error));
                dialog.setPositiveButton(getString(R.string.reload_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        loadProductData();
                    }
                });
                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sign_out_button:
                ((NavigationHost)getActivity()).navigateBack(true);
                return true;
        }

        return false;
    }
}
