package com.example.hammerhead.ViewModels;

import static java.lang.Math.min;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hammerhead.Data.FinData;
import com.example.hammerhead.Data.IDataReceiver;
import com.example.hammerhead.Data.MaskData;
import com.example.hammerhead.Data.WetsuitData;
import com.example.hammerhead.Models.IItem;
import com.example.hammerhead.Utilities.CountMergeSort;
import com.example.hammerhead.Utilities.IMergeSort;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;

public class MainViewModel extends ViewModel implements IDataReceiver {

    protected List<IItem> itemList = new LinkedList<>();
    private MutableLiveData<List<IItem>> liveData;
    private final int featuredViewType = 3;

    private void addIItemList(List<IItem> data) {
        itemList.addAll(data);

        IMergeSort mergeSort = new CountMergeSort();
        mergeSort.sort(itemList, 0, itemList.size() - 1);

        itemList = itemList.subList(0, min(itemList.size(), 10));
        for (IItem aItem : itemList) {
            aItem.setViewType(featuredViewType);

        }

        liveData.setValue(itemList);

    }

    public LiveData<List<IItem>> getItemList() {
        if (liveData == null) {
            liveData = new MutableLiveData<List<IItem>>();
            //fetchData();
        }
        return liveData;
    }

    public void clearItemList() {
        itemList.clear();
        fetchData();
    }

    private void fetchData() {
        //This code uses the IDataReceiver interface to have the IItemData object call this class when it has received the data from Firestore.
        // As the OnCompleteListener is asnyc, we need to rely it sending a message to our adapter in order for it to update
        new WetsuitData(FirebaseFirestore.getInstance().collection("Wetsuits")).fetchCollectionData(this);
        new FinData(FirebaseFirestore.getInstance().collection("Fins")).fetchCollectionData(this);
        new MaskData(FirebaseFirestore.getInstance().collection("Masks")).fetchCollectionData(this);
    }

    @Override
    public void dataReceived(List<IItem> data) {
            addIItemList(data);
    }

    public IItem getItem(int i) {
        return itemList.get(i);
    }

}
