package org.ulpgc.model;


import org.ulpgc.control.utils.ColorType;

public class Product {

    private String name;
    private double price;
    private String category;
    private String brand;
    private ColorType colorType;
    private String imageUrl;
    private String productUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ColorType getColor() {
        return colorType;
    }

    public void setColor(ColorType colorType) {
        this.colorType = colorType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }
}