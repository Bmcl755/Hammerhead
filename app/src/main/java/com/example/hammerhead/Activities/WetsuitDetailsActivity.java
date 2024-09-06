package com.example.hammerhead.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.hammerhead.Adapters.ViewPagerAdapter;
import com.example.hammerhead.Data.DataIncrementer;
import com.example.hammerhead.Data.IDataIncrementer;
import com.example.hammerhead.Models.IItem;
import com.example.hammerhead.Models.Wetsuit;
import com.example.hammerhead.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class WetsuitDetailsActivity extends AppCompatActivity {

    protected ViewHolder vh;

    protected class ViewHolder {

        TextView productName, description, colours,gender, thickness, price, brand;

        public ViewHolder(){
            productName = findViewById(R.id.productNameTextView);
            description = findViewById(R.id.descriptionTextView);
            colours = findViewById((R.id.coloursTextView));
            price = findViewById(R.id.priceTextView);
            brand = findViewById(R.id.brandTextView);
            gender = findViewById(R.id.genderTextView);
            thickness = findViewById(R.id.thicknessTextView);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details_wetsuit);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Intent intent = getIntent();
        Wetsuit wetsuit = intent.getParcelableExtra("item");

        ViewHolder vh = new ViewHolder();

        increaseViewCount(wetsuit.getProductName());

        vh.productName.setText(wetsuit.getProductName());
        vh.description.setText(wetsuit.getDescription());
        vh.brand.setText((wetsuit.getBrandName()));
        vh.colours.setText(Arrays.toString(wetsuit.getColours().toArray()).replace("[","").replace("]",""));
        vh.price.setText("$" + String.format("%.2f", wetsuit.getPrice()).concat(" NZD"));
        vh.thickness.setText(wetsuit.getThickness());
        vh.gender.setText(wetsuit.getGender());

        propagateViewAdapter(wetsuit);
    }

    public void increaseViewCount(String name){
        IDataIncrementer dataIncrementer = new DataIncrementer(name,this, FirebaseFirestore.getInstance().collection("Wetsuits").document(name));
        dataIncrementer.incrementItem();
    }

    public void propagateViewAdapter(IItem item){
        int[] images = new int[item.getPictureFileList().size()];
        for (int i = 0; i < item.getPictureFileList().size(); i++) {
            images[i] = getResources().getIdentifier(item.getPictureFileList().get(i), "drawable", getPackageName());
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(WetsuitDetailsActivity.this, images);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);
    }


}
