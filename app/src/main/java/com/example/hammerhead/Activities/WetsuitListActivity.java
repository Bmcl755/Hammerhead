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
import com.example.hammerhead.Data.WetsuitData;
import com.example.hammerhead.Models.Wetsuit;
import com.example.hammerhead.R;
import com.example.hammerhead.ViewModels.ListViewModel;
import com.google.firebase.firestore.FirebaseFirestore;


public class WetsuitListActivity extends ListActivity{

    @Override
    protected void setCategorySpecifics() {
        vh.categoryText.setText("Wetsuits");
        vh.constraintLayout.setBackgroundColor(this.getResources().getColor(R.color.wetsuit_main));
        vh.categoryImage.setImageResource(R.drawable.wetsuit_icon_white);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.wetsuit_main));

        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);
        IItemData iItemData = new WetsuitData(FirebaseFirestore.getInstance().collection("Wetsuits") );
        listViewModel.setDataType(iItemData);

    }

    @Override
    public void onItemClick(int position,ImageView imageView) {
        Intent intent = new Intent(this, WetsuitDetailsActivity.class);

        intent.putExtra("item", (Wetsuit) listViewModel.getItem(position));

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                WetsuitListActivity.this,imageView, ViewCompat.getTransitionName(imageView));
        startActivity(intent, options.toBundle());
    }
}
