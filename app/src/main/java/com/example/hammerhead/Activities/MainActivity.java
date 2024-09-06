package com.example.hammerhead.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.content.Intent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.hammerhead.Adapters.ItemAdapter;
import com.example.hammerhead.Models.Fins;
import com.example.hammerhead.Models.IItem;
import com.example.hammerhead.Models.Mask;
import com.example.hammerhead.Models.Wetsuit;
import com.example.hammerhead.R;
import com.example.hammerhead.ViewModels.MainViewModel;
import com.example.hammerhead.Views.IRecyclerView;


import java.util.List;
public class MainActivity extends AppCompatActivity implements IRecyclerView {

    protected MainActivity.ViewHolder vh;
    protected MainViewModel mainViewModel;

    protected class ViewHolder {
        RecyclerView recyclerView;
        EditText editText;
        ProgressBar progressBar;

        public ViewHolder(){
            recyclerView = findViewById(R.id.featuredItemsRecyclerView);
            editText = findViewById(R.id.searchTextView);
            progressBar = findViewById(R.id.progressBar);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        vh = new MainActivity.ViewHolder();
        vh.editText.setOnEditorActionListener(editorListener);

        mainViewModel.getItemList().observe(this, itemList -> {
            vh.progressBar.setVisibility(View.GONE);
            propagateAdaptor(itemList);
        });

    }
    //When the user goes back to the main activity the featured list fetches the newest data and updates
    @Override
    protected void onResume() {
        super.onResume();
        vh.recyclerView.setVisibility(View.VISIBLE);
        vh.progressBar.setVisibility(View.GONE);
        mainViewModel.clearItemList();
    }
    //Allocates to the search bar, when the user presses the done button the search activty is started
    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE) {
                String text = textView.getText().toString();
                vh.editText.getText().clear();
                showSearchActivity(text);
            }
            return false;
        }
    };
    //Populates the recycler view using the list of IItems given
    protected void propagateAdaptor(List<IItem> data){
        ItemAdapter itemAdapter = new ItemAdapter(data,this,this);
        vh.recyclerView.setAdapter(itemAdapter);
        vh.recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        vh.progressBar.setVisibility(View.GONE);
        vh.recyclerView.setVisibility(View.VISIBLE);
    }
    //When the user tap/clicks card in the recycler view an intent is sent to open the corresponding
    @Override
    public void onItemClick(int position, ImageView imageView) {
    
        Intent intent = null;
        IItem item = mainViewModel.getItem(position);

        if(item.getClass() == Mask.class){
            intent = new Intent(this, MaskDetailsActivity.class);
            intent.putExtra("item",(Mask) item );
        } else if(item.getClass() == Wetsuit.class){
            intent = new Intent(this, WetsuitDetailsActivity.class);
            intent.putExtra("item", (Wetsuit) item );
        } else if (item.getClass() == Fins.class){
            intent = new Intent(this, FinsDetailsActivity.class);
            intent.putExtra("item", (Fins) item );
        }
        vh.editText.clearFocus();
        startActivity(intent);
    }
    //Allocated to the wetsuit category button, to show the wetsuit list when tapped/clicked
    public void showWetsuitsListActivity(View v){
        Intent wetsuitsListIntent = new Intent(this, WetsuitListActivity.class);
        vh.editText.clearFocus();
        startActivity(wetsuitsListIntent);
    }
    //Allocated to the fins category button, to show the fins list when tapped/clicked
    public void showFinsListActivity(View v){
        Intent finsListIntent = new Intent(this, FinsListActivity.class);
        vh.editText.clearFocus();
        startActivity(finsListIntent);
    }
    //Allocated to the mask category button, to show the mask list when tapped/clicked
    public void showMaskListActivity(View v){
        Intent maskListIntent = new Intent(this, MaskListActivity.class);
        vh.editText.clearFocus();
        startActivity(maskListIntent);
    }
    //Called after the user presses done on their keyboard to pass on their search to search activity
    public void showSearchActivity(String searchTerm) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        vh.editText.clearFocus();
        searchIntent.putExtra("searchTerm", searchTerm);
        startActivity(searchIntent);
    }
}