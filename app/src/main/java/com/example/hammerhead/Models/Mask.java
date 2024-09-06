package com.example.hammerhead.Models;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class Mask extends Items{



    public Mask(String productName, String brandName, String description, double price, List<String> pictureFileList, List<String> colours){
        this.productName = productName;
        this.brandName = brandName;
        this.description = description;
        this.price = price;
        this.pictureFileList = pictureFileList;
        this.colours = colours;
    }

    public Mask(){
        this.viewType = 0;
    }

    protected Mask(Parcel in){
        productName = in.readString();
        brandName = in.readString();
        description = in.readString();
        price = in.readDouble();
        pictureFileList = new ArrayList<String>();
        in.readStringList(pictureFileList);
        colours = new ArrayList<String>();
        in.readStringList(colours);
    }

    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel parcel) {
            return new Mask(parcel);
        }

        @Override
        public Mask[] newArray(int i) {
            return new Mask[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(productName);
        parcel.writeString(brandName);
        parcel.writeString(description);
        parcel.writeDouble(price);
        parcel.writeStringList(pictureFileList);
        parcel.writeStringList(colours);
    }

    @Override
    public int getViewType() {
        return this.viewType;
    }
}
