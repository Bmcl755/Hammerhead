package com.example.hammerhead.Data;

import android.util.Log;

import com.example.hammerhead.Models.IItem;
import com.example.hammerhead.Models.Mask;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;

public class MaskData extends ItemData {

    public MaskData( CollectionReference fs) {
        super(fs);
    }

    @Override
    public List<IItem> makeItemList(QuerySnapshot results) {
        List<IItem> iItemList = new LinkedList<IItem>();

        for (IItem aItem : results.toObjects(Mask.class)) {
            iItemList.add(aItem);
            Log.i("Parsing items", aItem.getProductName() + " loaded.");
        }
        return iItemList;
    }
}
