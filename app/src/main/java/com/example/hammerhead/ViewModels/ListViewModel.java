package com.example.hammerhead.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hammerhead.Data.IDataReceiver;
import com.example.hammerhead.Data.IItemData;
import com.example.hammerhead.Models.IItem;
import com.example.hammerhead.Utilities.CountMergeSort;
import com.example.hammerhead.Utilities.HighestMergeSort;
import com.example.hammerhead.Utilities.IMergeSort;
import com.example.hammerhead.Utilities.LowestMergeSort;

import java.util.List;

public class ListViewModel extends ViewModel implements IDataReceiver {

    private List<IItem> iItemList;
    private MutableLiveData<List<IItem>> liveData;
    private IItemData iItemData;

    public LiveData<List<IItem>> getItemList(){
        if(liveData == null){
            liveData = new MutableLiveData<List<IItem>>();
            fetchData();
        }
        return liveData;
    }

    public void setDataType(IItemData dataType) {
        this.iItemData = dataType;
    }

    public IItem getItem(int i) {
        return iItemList.get(i);
    }

    public void dataReceived(List<IItem> data){
        iItemList = data;
        liveData.setValue(data);
    }

    private void fetchData() {
        //This code uses the IDataReceiver interface to have the IItemData object call this class when it has received the data from Firestore.
        // As the OnCompleteListener is asnyc, we need to rely it sending a message to our adapter in order for it to update
        iItemData.fetchCollectionData(this);
    }

    public void mostViewedSort(){
        IMergeSort mergeSort = new CountMergeSort();
        mergeSort.sort(iItemList,0,iItemList.size()-1);

        liveData.setValue(iItemList);
    }

    public void highestPriceSort(){
        IMergeSort mergeSort = new HighestMergeSort();
        mergeSort.sort(iItemList,0,iItemList.size()-1);

        liveData.setValue(iItemList);
    }

    public void lowestPriceSort(){
        IMergeSort mergeSort = new LowestMergeSort();
        mergeSort.sort(iItemList,0,iItemList.size()-1);

        liveData.setValue(iItemList);
    }
}
