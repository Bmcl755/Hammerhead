package com.example.hammerhead.Models;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class Fins extends Items{

    private String sizes;


    public Fins(String productName,String Brand, List<String> Colour, String Description, double Price, String sizes, List<String> itemNames) {
        this.productName = productName;
        this.brandName = Brand;
        this.description = Description;
        this.colours = Colour;
        this.price = Price;
        this.sizes = sizes;
        this.pictureFileList = itemNames;
    }

    @Override
    public String getSizes() {
        return sizes;
    }

    @Override
    public int getViewType() {
        return this.viewType;
    }

    public Fins(){
        this.viewType = 2;
    }

    protected Fins(Parcel in){
        productName = in.readString();
        brandName = in.readString();
        description = in.readString();
        colours = new ArrayList<String >();
        in.readStringList(colours);
        price = in.readDouble();
        sizes = in.readString();
        pictureFileList = new ArrayList<String>();
        in.readStringList(pictureFileList);
    }

    public static final Creator<Fins> CREATOR = new Creator<Fins>() {
        @Override
        public Fins createFromParcel(Parcel parcel) {
            return new Fins(parcel);
        }

        @Override
        public Fins[] newArray(int i) {
            return new Fins[i];
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
        parcel.writeStringList(colours);
        parcel.writeDouble(price);
        parcel.writeString(sizes);
        parcel.writeStringList(pictureFileList);
    }
}

