package com.example.hammerhead.Models;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class Wetsuit extends Items{

    private String thickness, gender;

    public Wetsuit(String productName, String brandName, String description, double price, List<String> pictureFileList, List<String> colours, String thickness, String gender){
        this.productName = productName;
        this.brandName = brandName;
        this.description = description;
        this.price = price;
        this.pictureFileList = pictureFileList;
        this.colours = colours;
        this.thickness = thickness;
        this.gender = gender;
    }
    @Override
    public String getGender() {
        return gender;
    }
    @Override
    public String getThickness() {
        return thickness;
    }

    public Wetsuit(){
        this.viewType = 1;
    }

    protected Wetsuit(Parcel in){
        productName = in.readString();
        brandName = in.readString();
        description = in.readString();
        price = in.readDouble();
        pictureFileList = new ArrayList<String>();
        in.readStringList(pictureFileList);
        colours = new ArrayList<String>();
        in.readStringList(colours);
        thickness = in.readString();
        gender = in.readString();
    }

    public static final Creator<Wetsuit> CREATOR = new Creator<Wetsuit>() {
        @Override
        public Wetsuit createFromParcel(Parcel parcel) {
            return new Wetsuit(parcel);
        }

        @Override
        public Wetsuit[] newArray(int i) {
            return new Wetsuit[0];
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
        parcel.writeString(thickness);
        parcel.writeString(gender);
    }
}
