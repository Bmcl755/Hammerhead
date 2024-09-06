package com.example.hammerhead.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hammerhead.Data.FinData;
import com.example.hammerhead.Data.IDataReceiver;
import com.example.hammerhead.Data.MaskData;
import com.example.hammerhead.Data.WetsuitData;
import com.example.hammerhead.Models.IItem;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;

public class SearchViewModel extends ViewModel implements IDataReceiver {

    private List<IItem> itemList= new LinkedList<>();
    private String searchTerm;

    private void addIItemList(List<IItem> data) {
        itemList.addAll(data);
        liveData.setValue(itemList);
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    private MutableLiveData<Boolean> found;
    public LiveData<Boolean> isFound() {
        if(found == null){
            found = new MutableLiveData<Boolean>();
        }
        return found;
    }

    public List<IItem> getIItemList() {
        return itemList;
    }

    private MutableLiveData<List<IItem>> liveData;
    public LiveData<List<IItem>> getItemList() {
        if(liveData == null){
            liveData = new MutableLiveData<List<IItem>>();
            fetchData();
        }
        return liveData;
    }

    public void updateItemList(){
        fetchData();
    }

    public void clearItemList(){
        itemList.clear();
    }

    private void fetchData() {

        new WetsuitData(FirebaseFirestore.getInstance().collection("Wetsuits")).fetchCollectionData(this);
        new FinData(FirebaseFirestore.getInstance().collection("Fins")).fetchCollectionData(this);
        new MaskData(FirebaseFirestore.getInstance().collection("Masks")).fetchCollectionData(this);
    }

    private List<IItem> searchContains(List<IItem> data){
        List<IItem> searchData = new LinkedList<>();
        for(IItem iItem: data){
            if(iItem.getProductName().toLowerCase().contains(searchTerm.toLowerCase())){
                searchData.add(iItem);
            }
        }
        return searchData;
    }

    @Override
    public void dataReceived(List<IItem> data) {

        data = searchContains(data);
        found.setValue(true);
        if((data.size() == 0 || searchTerm.trim().equals("")) && itemList.size() == 0 ){
            found.setValue(false);
        }
        addIItemList(data);
    }
}
