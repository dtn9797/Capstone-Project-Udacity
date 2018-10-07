package com.example.duynguyen.amashop;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duynguyen.amashop.model.Order;
import com.example.duynguyen.amashop.model.Product;
import com.example.duynguyen.amashop.model.ProductColor;
import com.example.duynguyen.amashop.model.User;
import com.example.duynguyen.amashop.utils.OnCartFabClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public final static String TAG = DetailActivity.class.getSimpleName();
    private Product product;
    private int mCurrentAmount = -1;
    private int mCurrentColorIndex = -1;
    private ArrayList<Integer> colorButtonIds = new ArrayList<>();
    private Integer mColorBtnUnfocusId;
    private String mCurrentUserId;
    private List<Order> mOrders= new ArrayList<>();

    private DatabaseReference mDatabase;
    public OnCartFabClickListener mCallback;

    public final static String PRODUCT_EXTRA = "product";
    public final static String AMOUNT_EXTRA = "amount";
    public final static String COLOR_INDEX_EXTRA = "color";
    public final static String SCROLL_POSITION_EXTRA = "scroll";
    public static final String USER_ID_EXTRA = "id";

    @BindView(R.id.app_bar)
    Toolbar toolbar;
    @BindView(R.id.parent_sv)
    ScrollView parentSv;
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
    @BindView(R.id.spinner)
    Spinner amountSpinner;
    @BindView(R.id.add_cart_button)
    Button addButton;
    @BindView(R.id.color_title_tv)
    TextView colorTitleTv;
    @BindView(R.id.cart_fab)
    FloatingActionButton cartFab;
    @BindView(R.id.cart_fm)
    FrameLayout cartFm;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            product = savedInstanceState.getParcelable(PRODUCT_EXTRA);
            mCurrentAmount = savedInstanceState.getInt(AMOUNT_EXTRA);
            mCurrentColorIndex = savedInstanceState.getInt(COLOR_INDEX_EXTRA);
            mCurrentUserId = savedInstanceState.getString(USER_ID_EXTRA);
            final int[] scrollPositions = savedInstanceState.getIntArray(SCROLL_POSITION_EXTRA);
            if(scrollPositions != null)
                parentSv.post(new Runnable() {
                    public void run() {
                        parentSv.scrollTo(scrollPositions[0], scrollPositions[1]);
                    }
                });
        }else {
            Intent intent = getIntent();
            if (intent == null) {
                closeOnError();
            }
            product = intent.getParcelableExtra(PRODUCT_EXTRA);
            mCurrentUserId = intent.getStringExtra(USER_ID_EXTRA);

        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        setUpToolbar();
        setupView(savedInstanceState);
        readDatabase(mCurrentUserId);


    }

    private void readDatabase(String userId) {
        DatabaseReference currentUserDatanase = mDatabase.child("carts").child(userId);
        currentUserDatanase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    mOrders.add(ds.getValue(Order.class));
                }
                mCallback.OnCartFabClick(mOrders);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void registerCallback (OnCartFabClickListener onCartFabClickListener){
        mCallback = onCartFabClickListener;
    }
    private void setupView(Bundle saveInstanceState) {
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
        if (colors.size() == 0) {
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
            if(i==0) {
                mColorBtnUnfocusId = colorButtonIds.get(0);
            }

            colorButton.setLayoutParams(lp);
            colorButton.setBackground(gradientDrawable);
            colorButton.setContentDescription(getString(R.string.image_color_description));
            colorButton.setOnClickListener(this);

            colorsLl.addView(colorButton);

            if (mCurrentColorIndex == i) {
                setFocus(mColorBtnUnfocusId, colorButtonId);
            }
        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.amount, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountSpinner.setAdapter(adapter);
        if (mCurrentAmount != -1) {
            amountSpinner.setSelection(mCurrentAmount - 1);
        }
        amountSpinner.setOnItemSelectedListener(this);
        addButton.setOnClickListener(this);
        cartFab.setOnClickListener(this);

        //add cart fragment
        if(saveInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.cart_fm, new CartFragment())
                    .commit();
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
        this.mColorBtnUnfocusId = focusButton.getId();
    }

    private void writeNewOrder(String userId, Order order) {

        mDatabase.child("carts").child(userId).push().setValue(order);
    }

    private Order createValidOrder () {
        if (mCurrentColorIndex == -1 && !product.getProductColors().isEmpty()){
            AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
            dialog.setCancelable(true);
            dialog.setTitle("Warning");
            dialog.setMessage("Please pick product color before adding to your cart.");
//            dialog.setPositiveButton(getString(R.string.reload_button), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int id) {
//                    loadMovieData(TOP_RATED_TYPE);
//                }
//            });
            final AlertDialog alert = dialog.create();
            alert.show();
            return null;
        }
        else{
            String name = product.getName();
            Double price = Double.parseDouble(product.getPrice());
            Integer amount = mCurrentAmount;
            String imageLink = product.getImageLink();
            String productColor = (product.getProductColors().isEmpty()) ? null : product.getProductColors().get(mCurrentColorIndex).getColourName();
            return new Order(name,price,amount,imageLink,productColor);
        }
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
                mCurrentColorIndex = i;
                setFocus(this.mColorBtnUnfocusId, colorButtonIds.get(i));
            }
        }
        switch (id){
            case R.id.add_cart_button:
                Order order = createValidOrder();
                if (order!=null) {
                    Toast.makeText(this, "Successfully add a new order in your cart.", Toast.LENGTH_SHORT).show();
                    writeNewOrder(mCurrentUserId, order);
                }
                break;
            case R.id.cart_fab:
                Toast.makeText(this, "FAB is clicked", Toast.LENGTH_SHORT).show();
                if ((cartFm.getVisibility() == View.INVISIBLE)) {
                    cartFm.setVisibility(View.VISIBLE);
                } else {
                    cartFm.setVisibility(View.INVISIBLE);
                }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mCurrentAmount = position + 1;
        Toast.makeText(this, "The item amount is " + String.valueOf(mCurrentAmount), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(PRODUCT_EXTRA, product);
        outState.putInt(AMOUNT_EXTRA, mCurrentAmount);
        outState.putInt(COLOR_INDEX_EXTRA, mCurrentColorIndex);
        outState.putString(USER_ID_EXTRA,mCurrentUserId);
        outState.putIntArray("SCROLL_POSITION",
                new int[]{ parentSv.getScrollX(), parentSv.getScrollY()});
    }

}
