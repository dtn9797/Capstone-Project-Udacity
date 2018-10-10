package com.example.duynguyen.amashop.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    private String name;
    private Double price;
    private Integer amount;
    private String productImage;
    private String productColor = "";
    private String key = "";

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Order() {
    }

    public Order(String name, Double price, Integer amount, String productImage, String productColor) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.productImage = productImage;
        this.productColor = productColor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getProductImage() {
        return productImage;
    }

    public String getProductColor() {
        return productColor;
    }

    public Double getTotalPrice() {
        return price * amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.price);
        dest.writeValue(this.amount);
        dest.writeString(this.productImage);
        dest.writeString(this.productColor);
        dest.writeString(this.key);
    }

    protected Order(Parcel in) {
        this.name = in.readString();
        this.price = (Double) in.readValue(Double.class.getClassLoader());
        this.amount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.productImage = in.readString();
        this.productColor = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
}
