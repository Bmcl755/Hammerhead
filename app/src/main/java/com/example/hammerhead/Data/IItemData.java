package com.example.hammerhead.Data;

import com.example.hammerhead.Models.IItem;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public interface IItemData {
    void fetchCollectionData(IDataReceiver iDataReceiver);
}
