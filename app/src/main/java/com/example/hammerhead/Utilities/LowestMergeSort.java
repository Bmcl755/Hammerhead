package com.example.hammerhead.Utilities;

import com.example.hammerhead.Models.IItem;

import java.util.LinkedList;
import java.util.List;

public class LowestMergeSort extends MergeSort{

    @Override
    void merge(List<IItem> itemList, int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;

        List<IItem> L = new LinkedList<>();
        List<IItem> R = new LinkedList<>();


        for (int i = 0; i < n1; ++i)
            L.add(itemList.get(l + i));
        for (int j = 0; j < n2; ++j)
            R.add(itemList.get(m + 1 +j));

        int i = 0, j = 0;

        int k = l;
        while (i < n1 && j < n2) {
            if (L.get(i).getPrice() <= R.get(j).getPrice()) {
                itemList.set(k, L.get(i));
                i++;
            }
            else {
                itemList.set(k, R.get(j));
                j++;
            }
            k++;
        }

        while (i < n1) {
            itemList.set(k, L.get(i));
            i++;
            k++;
        }

        while (j < n2) {
            itemList.set(k, R.get(j));
            j++;
            k++;
        }
    }
}
