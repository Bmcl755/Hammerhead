package com.example.hammerhead.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hammerhead.Adapters.ItemAdapter;
import com.example.hammerhead.Models.Fins;
import com.example.hammerhead.Models.IItem;
import com.example.hammerhead.Models.Mask;
import com.example.hammerhead.Models.Wetsuit;
import com.example.hammerhead.R;
import com.example.hammerhead.ViewModels.SearchViewModel;
import com.example.hammerhead.Views.IRecyclerView;

import java.util.List;


public class SearchActivity extends AppCompatActivity implements IRecyclerView {

    String searchTerm;
    ViewHolder vh;
    SearchViewModel model;

    protected class ViewHolder {
        RecyclerView recyclerView;
        ProgressBar progressBar;
        ConstraintLayout constraintLayout;
        TextView noResultText, resultText;
        EditText editText;

        public ViewHolder(){
            recyclerView = findViewById(R.id.listRecyclerView);
            progressBar = findViewById(R.id.load_progressbar);
            constraintLayout = findViewById(R.id.constraintLayout);
            resultText = findViewById(R.id.resultTextView);
            noResultText = findViewById(R.id.noResultTextView);
            editText = findViewById(R.id.searchTextView);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        vh = new SearchActivity.ViewHolder();
        model = new ViewModelProvider(this).get(SearchViewModel.class);
        Intent intent = getIntent();
        searchTerm = intent.getExtras().getString("searchTerm");
        model.setSearchTerm(searchTerm);
        setResultText(searchTerm);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.main_colour));

        //Sets the search bar to wait for user to press done then take their search term to populate the recycler view
        vh.editText.setOnEditorActionListener(editorListener);
        //Calls propagate adaptor when the itemList livedata is changed/updated
        model.getItemList().observe(this, itemList ->{
            vh.progressBar.setVisibility(View.GONE);
            propagateAdaptor(itemList);
        });
        //Displays the no results found text if no item contains the search term
        model.isFound().observe(this, isFound -> {
            if(isFound){
                vh.noResultText.setVisibility(View.GONE);
            }
            else {
                vh.noResultText.setVisibility(View.VISIBLE);
            }
        });
    }
    //Allocated to the edit text search bar, allows the user to search for a new term and repopulate the recycle view with the results
    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE) {
                String text = textView.getText().toString();
                vh.editText.getText().clear();
                model.setSearchTerm(text);
                setResultText(text);
                model.clearItemList();
                model.updateItemList();
            }
            return false;
        }
    };
    //Populates the recycler view using the list of IItems given
    protected void propagateAdaptor(List<IItem> data){

        ItemAdapter itemAdapter = new ItemAdapter(data,this,this);
        vh.recyclerView.setAdapter(itemAdapter);
        int orientation = getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            vh.recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        } else {
            vh.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        vh.recyclerView.setVisibility(View.VISIBLE);
    }
    //When the user tap/clicks card in the recycler view an intent is sent to open the corresponding detailActivity depending on the item subclass
    @Override
    public void onItemClick(int position, ImageView imageView) {

        Intent intent = null;
        IItem item = model.getIItemList().get(position);

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
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                SearchActivity.this,imageView, ViewCompat.getTransitionName(imageView));
        startActivity(intent, options.toBundle());
    }

    public void setResultText(String searchTerm){
        vh.resultText.setText("Showing results for \"" + searchTerm + "\" " );
    }
}
