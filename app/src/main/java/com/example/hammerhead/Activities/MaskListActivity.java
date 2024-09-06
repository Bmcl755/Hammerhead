package com.example.hammerhead.Activities;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.hammerhead.Data.IItemData;
import com.example.hammerhead.Data.MaskData;
import com.example.hammerhead.Models.Mask;
import com.example.hammerhead.R;
import com.example.hammerhead.ViewModels.ListViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

public class MaskListActivity extends ListActivity  {

    @Override
    protected void setCategorySpecifics(){
        vh.categoryText.setText("Masks");
        vh.categoryImage.setImageResource(R.drawable.mask_icon_white);
        vh.constraintLayout.setBackgroundColor(getResources().getColor(R.color.mask_main));

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.mask_main));

        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        IItemData iItemData = new MaskData(FirebaseFirestore.getInstance().collection("Masks") );
        listViewModel.setDataType(iItemData);
    }

    @Override
    public void onItemClick(int position, ImageView imageView) {
        Intent intent = new Intent(this, MaskDetailsActivity.class);
        intent.putExtra("item", (Mask) listViewModel.getItem(position));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MaskListActivity.this,imageView, ViewCompat.getTransitionName(imageView));
        startActivity(intent, options.toBundle());

    }
}