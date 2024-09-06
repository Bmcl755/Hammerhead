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
import com.example.hammerhead.Models.Mask;
import com.example.hammerhead.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class MaskDetailsActivity extends AppCompatActivity {

    protected ViewHolder vh;

    protected class ViewHolder {

        TextView productName, description, colours, price, brand;

        public ViewHolder(){
            productName = findViewById(R.id.productNameTextView);
            description = findViewById(R.id.descriptionTextView);
            colours = findViewById((R.id.coloursTextView));
            price = findViewById(R.id.priceTextView);
            brand = findViewById(R.id.brandTextView);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSharedElementsUseOverlay(true);
        setContentView(R.layout.item_details_mask);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        ViewHolder vh = new ViewHolder();

        Intent intent = getIntent();
        Mask mask = intent.getParcelableExtra("item");

        increaseViewCount(mask.getProductName());

        vh.productName.setText(mask.getProductName());
        vh.description.setText(mask.getDescription());
        vh.brand.setText((mask.getBrandName()));
        vh.colours.setText(Arrays.toString(mask.getColours().toArray()).replace("[","").replace("]",""));
        vh.price.setText("$" + String.format("%.2f", mask.getPrice()).concat(" NZD"));

        propagateViewAdapter(mask);
    }

    public void increaseViewCount(String name){
        IDataIncrementer dataIncrementer = new DataIncrementer(name,this, FirebaseFirestore.getInstance().collection("Masks").document(name));
        dataIncrementer.incrementItem();
    }

    public void propagateViewAdapter(IItem mask){
        int[] images = new int[mask.getPictureFileList().size()];
        for (int i = 0; i < mask.getPictureFileList().size(); i++) {
            images[i] = getResources().getIdentifier(mask.getPictureFileList().get(i), "drawable", getPackageName());
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(MaskDetailsActivity.this, images);
        viewPager.setAdapter(viewPagerAdapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);
    }


}
