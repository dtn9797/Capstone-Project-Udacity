package com.example.duynguyen.amashop;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duynguyen.amashop.model.Order;
import com.example.duynguyen.amashop.utils.GooglePayHelper;
import com.example.duynguyen.amashop.utils.OnCartFabClickListener;
import com.example.duynguyen.amashop.utils.OrderAdapter;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends Fragment implements OnCartFabClickListener, OrderAdapter.OnDeleteClickListener {
    @BindView(R.id.orders_rv)
    RecyclerView ordersRv;
    @BindView(R.id.total_price_tv)
    TextView totalPriceTv;
    @BindView(R.id.pay_btn)
    ImageButton payBtn;
    @BindView(R.id.cart_close_iv)
    ImageView closeBtn;

    private OrderAdapter orderAdapter;
    private List<Order> mOrders = new ArrayList<>();
    private PaymentsClient mPaymentsClient;
    private DatabaseReference mDatabase;

    final public static int LOAD_PAYMENT_DATA_REQUEST_CODE = 200;
    public static final String ORDERS_EXTRA = "order";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        orderAdapter = new OrderAdapter(getContext(), this);
        ordersRv.setAdapter(orderAdapter);
        ordersRv.setLayoutManager(linearLayoutManager);

        mPaymentsClient =
                Wallet.getPaymentsClient(Objects.requireNonNull(getActivity()),
                        new Wallet.WalletOptions.Builder().setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                                .build());

        payBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        PaymentDataRequest request = GooglePayHelper.createPaymentDataRequest();
                        if (request != null) {
                            AutoResolveHelper.resolveTask(
                                    mPaymentsClient.loadPaymentData(request),
                                    getActivity(),
                                    LOAD_PAYMENT_DATA_REQUEST_CODE);
                            // LOAD_PAYMENT_DATA_REQUEST_CODE is a constant integer of your choice,
                            // similar to what you would use in startActivityForResult
                        }
                    }
                });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DetailActivity) getActivity()).toggleCartFragment();
            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if (savedInstanceState != null) {
            mOrders = savedInstanceState.getParcelableArrayList(ORDERS_EXTRA);
            updateView();
        }

        return view;
    }

    private double getTotalPrice() {
        double totalPrice = 0;
        for (Order order : mOrders) {
            totalPrice += order.getTotalPrice();
        }
        return totalPrice;
    }

    private void updateView() {
        orderAdapter.setData(mOrders);
        String format = "$ %s";
        String totalPrice = String.valueOf(getTotalPrice());
        totalPriceTv.setText(String.format(format, totalPrice));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DetailActivity) Objects.requireNonNull(getActivity())).registerCallback(this);
    }

    @Override
    public void OnCartFabClick(List<Order> orders) {
        mOrders = orders;
        updateView();
    }

    @Override
    public void onDeteleClicked(int pos) {
        Toast.makeText(getContext(), "close button is click at " + String.valueOf(pos), Toast.LENGTH_SHORT).show();
        mDatabase.child("carts").child(((DetailActivity) Objects.requireNonNull(getActivity())).getCurrentUserId()).child(mOrders.get(pos).getKey()).removeValue();
        mOrders.remove(pos);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ORDERS_EXTRA, (ArrayList<? extends Parcelable>) mOrders);
    }
}



