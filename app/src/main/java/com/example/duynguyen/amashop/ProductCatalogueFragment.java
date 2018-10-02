package com.example.duynguyen.amashop;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProductCatalogueFragment extends Fragment implements View.OnClickListener, ProductsAdapter.ItemListener {
    public String TAG = ProductCatalogueFragment.class.getSimpleName();
    private ArrayList<? extends Product> data = new ArrayList<>();
    public ProductsAdapter mProductsAdapter;
    String BRAND0_TYPE = "Vegan";
    String BRAND1_TYPE = "Natural";
    String BRAND2_TYPE = "Canadian";
    String BRAND3_TYPE = "Non-gmo";
    private NavigationIconClickListener mNavigationIconClickListener;
    private String mCurrentBrand = "Vegan";
    private Boolean mCurrentExpandedView = false;

    private static final String DATA_ARRAY_EXTRA = "data_extra";
    private static final String BRAND_TYPE_EXTRA = "brand_extra";

    @BindView(R.id.product_grid)
    LinearLayout mProductGrid;
    @BindView(R.id.products_rv)
    RecyclerView mProductsRv;
    @BindView(R.id.product_grid_subtitle_tv)
    TextView subtitleTv;
    @BindView(R.id.polish_brand_0_bt)
    Button mBrand0Bt;
    @BindView(R.id.polish_brand_1_bt)
    Button mBrand1Bt;
    @BindView(R.id.polish_brand_2_bt)
    Button mBrand2Bt;
    @BindView(R.id.polish_brand_3_bt)
    Button mBrand3Bt;
    @BindView(R.id.app_bar)
    Toolbar mToolBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_catalogue, container, false);
        ButterKnife.bind(this, view);

        if (savedInstanceState == null){
            loadProductData(mCurrentBrand);
        }
        setUpView();
        setUpToolbar(view);
        return view;
    }

    private void setUpView() {
        mBrand0Bt.setOnClickListener(this);
        mBrand1Bt.setOnClickListener(this);
        mBrand2Bt.setOnClickListener(this);
        mBrand3Bt.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        mProductsAdapter = new ProductsAdapter(getContext(),this);
        ProductGridItemDecoration productGridItemDecoration = new ProductGridItemDecoration(80, 50);

        mProductsRv.setLayoutManager(gridLayoutManager);
        mProductsRv.addItemDecoration(productGridItemDecoration);
        mProductsRv.setAdapter(mProductsAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mProductGrid.setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.product_grid_background_shape));
        }
    }

    private void setUpToolbar(View view) {
        Drawable openIconId = ContextCompat.getDrawable(getContext(), R.drawable.ic_menu_black_24dp);
        Drawable closeIconId = ContextCompat.getDrawable(getContext(), R.drawable.ic_close_black_24dp);
        mNavigationIconClickListener = new NavigationIconClickListener(getContext(), mProductGrid, openIconId, closeIconId, false);
        ((MainActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolBar);
        setHasOptionsMenu(true);
        mToolBar.setNavigationOnClickListener(mNavigationIconClickListener);

    }

    private void loadProductData(String brand) {
        RetrofitInterface retrofitInterface = RetrofitClient.getClient().create(RetrofitInterface.class);
        Call<List<Product>> call;

        if (brand.equals(BRAND1_TYPE)) {
            call = retrofitInterface.get_products_by_tag("nail_polish", BRAND1_TYPE);
        } else if (brand.equals(BRAND2_TYPE)) {
            call = retrofitInterface.get_products_by_tag("nail_polish", BRAND2_TYPE);
        } else if (brand.equals(BRAND3_TYPE)) {
            call = retrofitInterface.get_products_by_tag("nail_polish", BRAND3_TYPE);
        } else {
            call = retrofitInterface.get_products_by_tag("nail_polish", BRAND0_TYPE);
        }

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(@NonNull Call<List<Product>> call, @NonNull Response<List<Product>> response) {
                data = (ArrayList<? extends Product>) response.body();
                mProductsAdapter.setData((List<Product>) data);
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
                        loadProductData(BRAND0_TYPE);
                    }
                });
                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });
    }

    private void performMenuItemClick(String brandType) {
        loadProductData(brandType);
        subtitleTv.setText(brandType);
        mCurrentBrand = brandType;
        mNavigationIconClickListener.click();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(DATA_ARRAY_EXTRA, (ArrayList<? extends Parcelable>) data);
        outState.putString(BRAND_TYPE_EXTRA,mCurrentBrand);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState!=null){
            data = savedInstanceState.getParcelableArrayList(DATA_ARRAY_EXTRA);
            mCurrentBrand = savedInstanceState.getString(BRAND_TYPE_EXTRA);
            mProductsAdapter.setData((List<Product>) data);
            subtitleTv.setText(mCurrentBrand);
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sign_out_button:
                ((NavigationHost) getActivity()).navigateBack(true);
                return true;
        }

        return false;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.polish_brand_0_bt:
                performMenuItemClick(BRAND0_TYPE);

                break;
            case R.id.polish_brand_1_bt:
                performMenuItemClick(BRAND1_TYPE);
                break;
            case R.id.polish_brand_2_bt:
                performMenuItemClick(BRAND2_TYPE);
                break;
            case R.id.polish_brand_3_bt:
                performMenuItemClick(BRAND3_TYPE);
                break;

        }
    }

    @Override
    public void onProductItemClick(int pos) {
        Toast.makeText(getContext(),"Product Item is clicked at "+pos,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(),DetailActivity.class);
        intent.putExtra(DetailActivity.PRODUCT_EXTRA, (Parcelable) data.get(pos));
        startActivity(intent);
    }
}
