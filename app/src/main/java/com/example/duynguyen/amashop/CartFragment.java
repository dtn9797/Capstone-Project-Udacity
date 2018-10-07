package com.example.duynguyen.amashop;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duynguyen.amashop.model.Order;
import com.example.duynguyen.amashop.utils.OnCartFabClickListener;
import com.example.duynguyen.amashop.utils.OrderAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartFragment extends Fragment implements OnCartFabClickListener{
    @BindView(R.id.orders_rv)
    RecyclerView ordersRv;
    OrderAdapter orderAdapter;

    private DatabaseReference mDataBase;
    private String mCurrentUserId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container,false);
        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        orderAdapter = new OrderAdapter(getContext());
        ordersRv.setAdapter(orderAdapter);
        ordersRv.setLayoutManager(linearLayoutManager);

        return view;
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

    //    private void readCurrentUser (String userId){
//        DatabaseReference currentUserDatanase = mDatabase.child("users").child(userId);
//        currentUserDatanase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                User user = dataSnapshot.getValue(User.class);
//                //dataSnapshot.getChildren()
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e(TAG, "onCancelled", databaseError.toException());
//            }
//        });
//    }
}
