package com.example.hammerhead.Data;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;

public class DataIncrementer implements IDataIncrementer {

    String productName;
    DocumentReference fs;
    Context ct;

    public DataIncrementer(String productName, Context ct, DocumentReference fs) {
        this.productName = productName;
        this.fs = fs;
        this.ct = ct;
    }

    public void incrementItem() {
        fs.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()){
                        Log.d("Parsing item ", "DocumentSnapshot data: " + document.getData());
                        updateViewNumber((Long) document.getData().get("viewCount"));
                    } else {
                        Log.d("Parsing item ", "No such document");
                    }
                } else {
                    Log.d("Parsing item ", "get failed with ", task.getException());
                }
            }
        });
    }

    private void updateViewNumber(Long viewNumber){
        HashMap update = new HashMap();
        viewNumber++;
        update.put("viewCount", viewNumber);
        fs.update(update);
    }
}
