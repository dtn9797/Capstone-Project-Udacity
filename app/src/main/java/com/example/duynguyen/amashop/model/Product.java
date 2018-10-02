
package com.example.duynguyen.amashop.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("price_sign")
    @Expose
    private String priceSign;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("image_link")
    @Expose
    private String imageLink;
    @SerializedName("product_link")
    @Expose
    private String productLink;
    @SerializedName("website_link")
    @Expose
    private String websiteLink;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("tag_list")
    @Expose
    private List<String> tagList = null;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("product_api_url")
    @Expose
    private String productApiUrl;
    @SerializedName("api_featured_image")
    @Expose
    private String apiFeaturedImage;
    @SerializedName("product_colors")
    @Expose
    private List<ProductColor> productColors = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceSign() {
        return priceSign;
    }

    public void setPriceSign(String priceSign) {
        this.priceSign = priceSign;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getProductLink() {
        return productLink;
    }

    public void setProductLink(String productLink) {
        this.productLink = productLink;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProductApiUrl() {
        return productApiUrl;
    }

    public void setProductApiUrl(String productApiUrl) {
        this.productApiUrl = productApiUrl;
    }

    public String getApiFeaturedImage() {
        return apiFeaturedImage;
    }

    public void setApiFeaturedImage(String apiFeaturedImage) {
        this.apiFeaturedImage = apiFeaturedImage;
    }

    public List<ProductColor> getProductColors() {
        return productColors;
    }

    public void setProductColors(List<ProductColor> productColors) {
        this.productColors = productColors;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.brand);
        dest.writeString(this.name);
        dest.writeString(this.price);
        dest.writeString(this.priceSign);
        dest.writeString(this.currency);
        dest.writeString(this.imageLink);
        dest.writeString(this.productLink);
        dest.writeString(this.websiteLink);
        dest.writeString(this.description);
        dest.writeValue(this.rating);
        dest.writeString(this.category);
        dest.writeString(this.productType);
        dest.writeStringList(this.tagList);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.productApiUrl);
        dest.writeString(this.apiFeaturedImage);
        dest.writeList(this.productColors);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.brand = in.readString();
        this.name = in.readString();
        this.price = in.readString();
        this.priceSign = in.readString();
        this.currency = in.readString();
        this.imageLink = in.readString();
        this.productLink = in.readString();
        this.websiteLink = in.readString();
        this.description = in.readString();
        this.rating = (Double) in.readValue(Double.class.getClassLoader());
        this.category = in.readString();
        this.productType = in.readString();
        this.tagList = in.createStringArrayList();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.productApiUrl = in.readString();
        this.apiFeaturedImage = in.readString();
        this.productColors = new ArrayList<ProductColor>();
        in.readList(this.productColors, ProductColor.class.getClassLoader());
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
