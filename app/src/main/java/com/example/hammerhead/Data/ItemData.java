package com.example.hammerhead.Data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.hammerhead.Models.IItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public abstract class ItemData implements IItemData{

    CollectionReference fs;


    public ItemData(CollectionReference fs){

        this.fs = fs;
    }


    public void fetchCollectionData(IDataReceiver iDataReceiver) {

        fs.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<IItem> iItemList = makeItemList(task.getResult());
                    Log.i("Getting items", "Success");
                    iDataReceiver.dataReceived(iItemList);
                }
            }
        });
    }

    protected abstract List<IItem> makeItemList(QuerySnapshot result);

}
