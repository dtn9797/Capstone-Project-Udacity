package com.example.duynguyen.amashop;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duynguyen.amashop.model.Product;
import com.example.duynguyen.amashop.model.ProductColor;
import com.ramotion.fluidslider.FluidSlider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String TAG = DetailActivity.class.getSimpleName();
    public final static String PRODUCT_EXTRA = "product";
    private Product product;
    private ArrayList<Integer> colorButtonIds = new ArrayList<>();
    private Integer colorBtnUnfocusId;

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
    @BindView((R.id.color_title_tv))
    TextView colorTitleTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setUpToolbar();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        product = intent.getParcelableExtra(PRODUCT_EXTRA);

        setupView();


    }

    private void setupView() {
        Picasso.get().load(product.getImageLink()).into(productIv);
        titleTv.setText(product.getName());
        String price = String.format(getResources().getString(R.string.detail_price_sample), product.getPrice());
        priceTv.setText(price);
        try {
            ratingBar.setRating(product.getRating().intValue());
        } catch (NullPointerException ignored) {
            ratingBar.setVisibility(View.GONE);
        }
        descriptionTv.setText(product.getDescription());

        List<ProductColor> colors = product.getProductColors();
        if (colors.size()==0){
            colorTitleTv.setVisibility(View.GONE);
        }
        colorButtonIds = new ArrayList<>();
        for (int i = 0; i < colors.size(); i++) {
            ImageButton colorButton = new ImageButton(this);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(130, 130);
            lp.setMargins(20, 20, 20, 20);

            int stringHex = Color.parseColor(colors.get(i).getHexValue());
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setColor(stringHex);
            gradientDrawable.setCornerRadius(150);

            //add id
            Integer colorButtonId = View.generateViewId();
            colorButton.setId(colorButtonId);
            colorButtonIds.add(colorButtonId);

            colorButton.setLayoutParams(lp);
            colorButton.setBackground(gradientDrawable);
            colorButton.setContentDescription(getString(R.string.image_color_description));
            colorButton.setOnClickListener(this);

            colorsLl.addView(colorButton);
        }
        if (colors.size() != 0) {
            colorBtnUnfocusId = colorButtonIds.get(0);
        }

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.close_on_intent_error), Toast.LENGTH_SHORT).show();
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

    private void setFocus(Integer btnUnfocusId, Integer btnFocusId) {
        ImageButton focusButton = findViewById(btnFocusId);
        ImageButton unFocusButton = findViewById(btnUnfocusId);
        unFocusButton.setImageDrawable(null);
        focusButton.setImageDrawable(getDrawable(R.drawable.ic_check_black_24dp));
        this.colorBtnUnfocusId = focusButton.getId();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        for (int i = 0; i < colorButtonIds.size(); i++) {
            if (id == colorButtonIds.get(i)) {
                Toast.makeText(this, "Button is selected", Toast.LENGTH_SHORT).show();
                setFocus(this.colorBtnUnfocusId,colorButtonIds.get(i));
            }
        }
    }
}