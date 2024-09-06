package com.example.hammerhead.Activities;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.hammerhead.Data.FinData;
import com.example.hammerhead.Data.IItemData;
import com.example.hammerhead.Models.Fins;
import com.example.hammerhead.R;
import com.example.hammerhead.ViewModels.ListViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class FinsListActivity extends ListActivity{

    @Override
    protected void setCategorySpecifics(){
        vh.categoryText.setText("Fins");
        vh.constraintLayout.setBackgroundColor(this.getResources().getColor(R.color.fins_main));
        vh.categoryImage.setImageResource(R.drawable.flippers_icon_white);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.fins_main));

        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        IItemData iItemData = new FinData(FirebaseFirestore.getInstance().collection("Fins") );
        listViewModel.setDataType(iItemData);
    }

    @Override
    public void onItemClick(int position, ImageView imageView) {
        Intent intent = new Intent(this, FinsDetailsActivity.class);
        intent.putExtra("item", (Fins) listViewModel.getItem(position));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                FinsListActivity.this,imageView, ViewCompat.getTransitionName(imageView));
        startActivity(intent, options.toBundle());
    }

}
