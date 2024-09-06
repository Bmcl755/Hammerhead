package com.example.hammerhead.Models;

import java.util.List;

public interface IItem {

    public String getBrandName();

    public String getProductName();

    public String getDescription();

    public double getPrice();

    public List<String> getColours();

    public List<String> getPictureFileList();

    public String getGender();

    public String getThickness();

    public String getSizes();

    public int getViewCount();

    public int getViewType();

    public void setViewType(int viewType);

}
