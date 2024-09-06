package com.example.hammerhead.Data;

import android.util.Log;

import com.example.hammerhead.Models.Fins;
import com.example.hammerhead.Models.IItem;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class FinData extends ItemData{
    public FinData(CollectionReference fs) {
        super(fs);
    }

    @Override
    public List<IItem> makeItemList(QuerySnapshot results) {
        List<IItem> iItemList = new LinkedList<IItem>();

        for (IItem aItem : results.toObjects(Fins.class)) {
            iItemList.add(aItem);
            Log.i("Parsing items", aItem.getProductName() + " loaded.");
        }
        return iItemList;
    }
}
