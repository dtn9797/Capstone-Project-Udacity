package com.example.duynguyen.amashop.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductColor implements Parcelable {

    @SerializedName("hex_value")
    @Expose
    private String hexValue;
    @SerializedName("colour_name")
    @Expose
    private String colourName;

    public String getHexValue() {
        return hexValue;
    }

    public void setHexValue(String hexValue) {
        this.hexValue = hexValue;
    }

    public String getColourName() {
        return colourName;
    }

    public void setColourName(String colourName) {
        this.colourName = colourName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hexValue);
        dest.writeString(this.colourName);
    }

    public ProductColor() {
    }

    protected ProductColor(Parcel in) {
        this.hexValue = in.readString();
        this.colourName = in.readString();
    }

    public static final Parcelable.Creator<ProductColor> CREATOR = new Parcelable.Creator<ProductColor>() {
        @Override
        public ProductColor createFromParcel(Parcel source) {
            return new ProductColor(source);
        }

        @Override
        public ProductColor[] newArray(int size) {
            return new ProductColor[size];
        }
    };
}
