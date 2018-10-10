package com.example.duynguyen.amashop.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duynguyen.amashop.R;
import com.example.duynguyen.amashop.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsVH> {
    private Context mContext;
    private List<Product> mData = new ArrayList<>();
    protected static ItemListener mItemClickListener;

    public ProductsAdapter(Context mContext, ItemListener itemListener) {
        this.mContext = mContext;
        mItemClickListener = itemListener;
    }

    public interface ItemListener {
        void onProductItemClick(int pos);
    }

    public void setData(List<Product> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.product_vh, parent, false);
        return new ProductsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsVH holder, int position) {
        holder.setData(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ProductsVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView titleTv;
        TextView subtitleTv;
        int pos;

        ProductsVH(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.product_iv);
            titleTv = itemView.findViewById(R.id.product_title_tv);
            subtitleTv = itemView.findViewById(R.id.product_subtitle_tv);
        }

        public void setData(Product data, int pos) {
            Picasso.get().load(data.getImageLink()).into(imageView);
            titleTv.setText(data.getBrand());
            subtitleTv.setText(data.getName());
            this.pos = pos;
        }

        @Override
        public void onClick(View v) {
            mItemClickListener.onProductItemClick(pos);
        }
    }
}
