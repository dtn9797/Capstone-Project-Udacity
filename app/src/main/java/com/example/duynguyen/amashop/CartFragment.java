package com.example.duynguyen.amashop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duynguyen.amashop.model.Order;
import com.example.duynguyen.amashop.utils.GooglePayHelper;
import com.example.duynguyen.amashop.utils.OnCartFabClickListener;
import com.example.duynguyen.amashop.utils.OrderAdapter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardInfo;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.firebase.database.DatabaseReference;
import com.stripe.android.model.Token;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends Fragment implements OnCartFabClickListener{
    @BindView(R.id.orders_rv)
    RecyclerView ordersRv;
    @BindView(R.id.total_price_tv)
    TextView totalPriceTv;
    @BindView(R.id.pay_btn)
    ImageButton payBtn;
    OrderAdapter orderAdapter;

    PaymentsClient mPaymentsClient;
    final int LOAD_PAYMENT_DATA_REQUEST_CODE = 200;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container,false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        orderAdapter = new OrderAdapter(getContext());
        ordersRv.setAdapter(orderAdapter);
        ordersRv.setLayoutManager(linearLayoutManager);

        mPaymentsClient =
                Wallet.getPaymentsClient(getActivity(),
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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                switch (requestCode) {
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        // You can get some data on the user's card, such as the brand and last 4 digits
                        CardInfo info = paymentData.getCardInfo();
                        // You can also pull the user address from the PaymentData object.
                        UserAddress address = paymentData.getShippingAddress();
                        // This is the raw JSON string version of your Stripe token.
                        String rawToken = paymentData.getPaymentMethodToken().getToken();

                        // Now that you have a Stripe token object, charge that by using the id
                        Token stripeToken = Token.fromString(rawToken);
                        if (stripeToken != null) {
                            // This chargeToken function is a call to your own server, which should then connect
                            // to Stripe's API to finish the charge.
                            Toast.makeText(getActivity(),
                                    "Got token " + stripeToken.toString(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        break;
                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        // Log the status for debugging
                        // Generally there is no need to show an error to
                        // the user as the Google Payment API will do that
                        break;
                    default:
                        // Do nothing.
                }
                break; // Breaks the case LOAD_PAYMENT_DATA_REQUEST_CODE
            // Handle any other startActivityForResult calls you may have made.
            default:
                // Do nothing.
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DetailActivity)getActivity()).registerCallback(this);
    }

    @Override
    public void OnCartFabClick(List<Order> orders) {
        orderAdapter.setData(orders);
    }
//
// findViewById(R.id.buy_button).setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        PaymentDataRequest request = createPaymentDataRequest();
//                        if (request != null) {
//                            AutoResolveHelper.resolveTask(
//                                    mPaymentsClient.loadPaymentData(request),
//                                    MainActivity.this,
//                                    LOAD_PAYMENT_DATA_REQUEST_CODE);
//                            // LOAD_PAYMENT_DATA_REQUEST_CODE is a constant integer of your choice,
//                            // similar to what you would use in startActivityForResult
//                        }
//                    }
//                });
//
//
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case LOAD_PAYMENT_DATA_REQUEST_CODE:
//                switch (resultCode) {
//                    case Activity.RESULT_OK:
//                        PaymentData paymentData = PaymentData.getFromIntent(data);
//                        // You can get some data on the user's card, such as the brand and last 4 digits
//                        CardInfo info = paymentData.getCardInfo();
//                        // You can also pull the user address from the PaymentData object.
//                        UserAddress address = paymentData.getShippingAddress();
//                        // This is the raw JSON string version of your Stripe token.
//                        String rawToken = paymentData.getPaymentMethodToken().getToken();
//
//                        // Now that you have a Stripe token object, charge that by using the id
//                        Token stripeToken = Token.fromString(rawToken);
//                        if (stripeToken != null) {
//                            // This chargeToken function is a call to your own server, which should then connect
//                            // to Stripe's API to finish the charge.
//                            Toast.makeText(MainActivity.this,
//                                    "Got token " + stripeToken.toString(), Toast.LENGTH_LONG).show();
//                        }
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        break;
//                    case AutoResolveHelper.RESULT_ERROR:
//                        Status status = AutoResolveHelper.getStatusFromIntent(data);
//                        // Log the status for debugging
//                        // Generally there is no need to show an error to
//                        // the user as the Google Payment API will do that
//                        break;
//                    default:
//                        // Do nothing.
//                }
//                break; // Breaks the case LOAD_PAYMENT_DATA_REQUEST_CODE
//            // Handle any other startActivityForResult calls you may have made.
//            default:
//                // Do nothing.
//        }
//    }
//
//    private void isReadyToPay() {
//        IsReadyToPayRequest request = IsReadyToPayRequest.newBuilder()
//                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
//                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
//                .build();
//        Task<Boolean> task = mPaymentsClient.isReadyToPay(request);
//        task.addOnCompleteListener(
//                new OnCompleteListener<Boolean>() {
//                    public void onComplete(Task<Boolean> task) {
//                        try {
//                            boolean result =
//                                    task.getResult(ApiException.class);
//                            if(result == true) {
//                                //show Google as payment option
//                            } else {
//                                //hide Google as payment option
//                            }
//                        } catch (ApiException exception) { }
//                    }
//                });
    }



