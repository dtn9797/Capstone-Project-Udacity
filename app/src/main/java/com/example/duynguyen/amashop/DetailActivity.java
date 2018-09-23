package com.example.duynguyen.amashop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ramotion.fluidslider.FluidSlider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public final static String TAG = DetailActivity.class.getSimpleName();
    public final static String PRODUCT_EXTRA = "product";

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.product_iv)
    ImageView productIv;
    @BindView(R.id.product_title_tv)
    TextView titleTv;
    @BindView(R.id.price_tv)
    TextView priceTv;
    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.description_tv)
    TextView descriptionTv;
    @BindView(R.id.colors_ll)
    LinearLayout colorsLl;
    @BindView(R.id.fluidSlider)
    FluidSlider fluidSlider;
    @BindView(R.id.add_cart_button)
    Button addButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setUpToolbar();


    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_product_button) {
            Log.d(TAG, "Add product button is clicked");
        }
        return super.onOptionsItemSelected(item);
    }
}
