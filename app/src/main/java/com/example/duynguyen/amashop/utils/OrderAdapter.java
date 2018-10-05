package com.example.duynguyen.amashop.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duynguyen.amashop.R;
import com.example.duynguyen.amashop.model.Order;
import com.example.duynguyen.amashop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderVH> {
    Context mContext;
    List<Order> mData= new ArrayList<>();

    public OrderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData (List<Order> data){
        this.mData = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public OrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_vh,parent,false);
        return new OrderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderVH holder, int position) {
        holder.setData(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class OrderVH extends RecyclerView.ViewHolder {
        @BindView(R.id.order_image_iv)
        ImageView imageIv;
        @BindView(R.id.order_name_tv)
        TextView nameTv;
        @BindView(R.id.order_color_tv)
        TextView colorTv;
        @BindView(R.id.order_amount_tv)
        TextView amountTv;
        @BindView(R.id.order_price_tv)
        TextView priceTv;
        @BindView(R.id.delete_order_iv)
        ImageView deleteIv;


        public OrderVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData (Order order){
            Picasso.get().load(order.getProductImage()).into(imageIv);
            nameTv.setText(order.getName());
            String colorText = order.getProductColor();
            if (colorText.isEmpty()){
                colorTv.setVisibility(View.GONE);
            }else {
                colorTv.setText(String.format(mContext.getResources().getString(R.string.order_color_title),
                        colorText));
            }

            String amountText = String.format(mContext.getResources().getString(R.string.order_amount_title),
                    order.getAmount());
            amountTv.setText(amountText);
            priceTv.setText(String.valueOf(order.getTotalPrice()));
            //need to set delete Iv
        }
    }
}
