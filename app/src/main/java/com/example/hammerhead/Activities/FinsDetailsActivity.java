package com.example.hammerhead.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import androidx.appcompat.app.AppCompatActivity;

import com.example.hammerhead.Adapters.ViewPagerAdapter;
import com.example.hammerhead.Data.DataIncrementer;
import com.example.hammerhead.Data.IDataIncrementer;
import com.example.hammerhead.Models.Fins;
import com.example.hammerhead.R;
import com.example.hammerhead.ViewModels.ListViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class FinsDetailsActivity extends AppCompatActivity{

    protected ViewHolder vh;

    protected class ViewHolder {

        TextView productName, description, colours, size, price, brand;

        public ViewHolder(){
            productName = findViewById(R.id.productNameTextView);
            description = findViewById(R.id.descriptionTextView);
            colours = findViewById((R.id.coloursTextView));
            size = findViewById(R.id.sizeTextView);
            price = findViewById(R.id.priceTextView);
            brand = findViewById(R.id.brandTextView);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_fins);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent intent = getIntent();
        Fins fin = intent.getParcelableExtra("item");

        ViewHolder vh = new ViewHolder();

        increaseViewCount(fin.getProductName());

        vh.productName.setText(fin.getProductName());
        vh.description.setText(fin.getDescription());
        vh.colours.setText(Arrays.toString(fin.getColours().toArray()).replace("[","").replace("]",""));
        vh.size.setText(fin.getSizes());
        vh.price.setText("$" + String.format("%.2f", fin.getPrice()).concat(" NZD"));
        vh.brand.setText(fin.getBrandName());

        propagateViewAdapter(fin);
    }

    public void increaseViewCount(String name){
        IDataIncrementer dataIncrementer = new DataIncrementer(name,this, FirebaseFirestore.getInstance().collection("Fins").document(name));
        dataIncrementer.incrementItem();
    }

    public void propagateViewAdapter(Fins fins){

        int[] images = new int[fins.getPictureFileList().size()];
        for (int i = 0; i < fins.getPictureFileList().size(); i++) {
            images[i] = getResources().getIdentifier(fins.getPictureFileList().get(i), "drawable", getPackageName());
        }
        ViewPager viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(FinsDetailsActivity.this, images);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);
    }
}
