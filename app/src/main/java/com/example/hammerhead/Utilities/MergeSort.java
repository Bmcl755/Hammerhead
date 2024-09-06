package com.example.hammerhead.Utilities;

import com.example.hammerhead.Models.IItem;

import java.util.List;

public abstract class MergeSort implements IMergeSort {

    public void sort(List<IItem> itemList, int l, int r)
    {
        if (l < r) {
            int m =l+ (r-l)/2;

            sort(itemList, l, m);
            sort(itemList, m + 1, r);

            merge(itemList, l, m, r);
        }
    }

    void merge(List<IItem> itemList, int l, int m, int r){}
}
