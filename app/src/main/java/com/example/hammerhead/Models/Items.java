package com.example.hammerhead.Models;

import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public abstract class Items implements IItem, Parcelable {
    protected String productName, brandName, description;
    protected double price;
    protected List<String> colours,pictureFileList;
    protected int viewCount;
    @Exclude
    protected int viewType;

    public String getProductName() {
        return productName;
    }

    public List<String> getPictureFileList() {
        return pictureFileList;
    }

    public int getViewCount() {
        return viewCount;
    }

    public int getViewType() {
        return viewType;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getColours() {
        return colours;
    }

    @Exclude
    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


    @Exclude
    public String getGender() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }

    @Exclude
    public String getThickness() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }

    @Exclude
    public String getSizes() {
        throw new RuntimeException(this.getClass().getSimpleName() + " doesn't have this method");
    }
}
