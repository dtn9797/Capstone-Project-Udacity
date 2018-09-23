
package com.example.duynguyen.amashop.model;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductColor implements Serializable, Parcelable
{

    @SerializedName("hex_value")
    @Expose
    private String hexValue;
    @SerializedName("colour_name")
    @Expose
    private String colourName;
    public final static Parcelable.Creator<ProductColor> CREATOR = new Creator<ProductColor>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductColor createFromParcel(Parcel in) {
            return new ProductColor(in);
        }

        public ProductColor[] newArray(int size) {
            return (new ProductColor[size]);
        }

    }
            ;
    private final static long serialVersionUID = 3773245363013656661L;

    protected ProductColor(Parcel in) {
        this.hexValue = ((String) in.readValue((String.class.getClassLoader())));
        this.colourName = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ProductColor() {
    }

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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(hexValue);
        dest.writeValue(colourName);
    }

    public int describeContents() {
        return  0;
    }

}
