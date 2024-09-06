package com.example.hammerhead.Activities;

import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hammerhead.Adapters.ItemAdapter;
import com.example.hammerhead.Models.IItem;
import com.example.hammerhead.R;
import com.example.hammerhead.ViewModels.ListViewModel;
import com.example.hammerhead.Views.IRecyclerView;

import java.util.List;

public abstract class ListActivity extends AppCompatActivity implements IRecyclerView {

    protected ViewHolder vh;
    protected ListViewModel listViewModel;

    protected class ViewHolder {
        RecyclerView recyclerView;
        ProgressBar progressBar;
        ConstraintLayout constraintLayout;
        TextView categoryText, sortText;
        ImageView categoryImage;

        public ViewHolder(){
            recyclerView = findViewById(R.id.listRecyclerView);
            progressBar = findViewById(R.id.load_progressbar);
            constraintLayout = findViewById(R.id.constraintLayout);
            categoryText = findViewById(R.id.categoryTextView);
            categoryImage = findViewById(R.id.categoryImageView);
            sortText = findViewById(R.id.sortText);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        vh = new ViewHolder();

        //This method is called in the child classes to load that activities specific settings and
        //create its viewModel
        setCategorySpecifics();

        listViewModel.getItemList().observe(this, itemList -> {
            vh.progressBar.setVisibility(View.GONE);
            propagateAdaptor(itemList);
        });

        //setup for the sort dialog
        CardView refine = findViewById(R.id.refineCard);
        refine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    protected void setCategorySpecifics(){}

    protected void propagateAdaptor(List<IItem> data){

        ItemAdapter itemAdapter = new ItemAdapter(data,this,this);
        vh.recyclerView.setAdapter(itemAdapter);
        int orientation = getResources().getConfiguration().orientation;

        //cards will be placed 2 in a row when phone is horizontal to allow for a better viewing experience
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            vh.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        } else {
            vh.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        vh.recyclerView.setVisibility(View.VISIBLE);
    }

    public void onItemClick(int position) {}

    public void showDialog(){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sort_dialog);

        ConstraintLayout mostViewed = dialog.findViewById(R.id.mostViewedCard);
        ConstraintLayout highPrice = dialog.findViewById(R.id.highPriceCard);
        ConstraintLayout lowPrice = dialog.findViewById(R.id.lowPriceCard);

        mostViewed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vh.sortText.setText("Most Viewed");
                vh.sortText.setVisibility(View.VISIBLE);
                listViewModel.mostViewedSort();
                dialog.dismiss();
            }
        });

        highPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vh.sortText.setText("Highest Price");
                vh.sortText.setVisibility(View.VISIBLE);
                listViewModel.highestPriceSort();
                dialog.dismiss();
            }
        });

        lowPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vh.sortText.setText("Lowest Price");
                vh.sortText.setVisibility(View.VISIBLE);
                listViewModel.lowestPriceSort();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}


