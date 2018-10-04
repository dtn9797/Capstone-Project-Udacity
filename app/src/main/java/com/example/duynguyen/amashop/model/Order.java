package com.example.duynguyen.amashop.model;

public class Order {
    private String name;
    private Double price;
    private Integer amount;
    private String productImage;
    private String productColor;

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
    public Double getTotalPrice () {
        return price*amount;
    }
}
