package com.example.hammerhead.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hammerhead.Models.IItem;
import com.example.hammerhead.R;
import com.example.hammerhead.Views.IRecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<IItem> listItems;
    private Context ct;

    private final IRecyclerView recyclerViewInterface;

    public ItemAdapter(List<IItem> listItems, Context ct, IRecyclerView recyclerViewInterface) {
        this.listItems = listItems;
        this.ct = ct;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public int getItemViewType(int position) {
        return listItems.get(position).getViewType();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(ct);

        View view;

        switch (viewType) {
            case 0:
                view = inflater.inflate(R.layout.card_view, parent, false);
                return new MaskViewHolder(view);
            case 1:
                view = inflater.inflate(R.layout.card_view, parent, false);
                return new WetsuitViewHolder(view);
            case 2:
                view = inflater.inflate(R.layout.card_view, parent, false);
                return new FinViewHolder(view);
            case 3:
                view = inflater.inflate(R.layout.featured_item_card, parent, false);
                return new MainViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.names.setText(listItems.get(position).getProductName());
        int i = ct.getResources().getIdentifier(listItems.get(position).getPictureFileList().get(0), "drawable", ct.getPackageName());
        holder.imageView.setImageResource(i);

        switch (listItems.get(position).getViewType()) {
            case 0:
                holder.brand.setText(listItems.get(position).getBrandName());
                holder.prices.setText("$" + String.format("%.2f", listItems.get(position).getPrice()));

                break;
            case 1:
                holder.brand.setText(listItems.get(position).getBrandName());
                holder.prices.setText("$" + String.format("%.2f", listItems.get(position).getPrice()));
                holder.thickness.setText(listItems.get(position).getThickness());
                holder.thickName.setVisibility(View.VISIBLE);
                holder.thickness.setVisibility(View.VISIBLE);
                holder.imageView.setBackgroundColor(ct.getResources().getColor(R.color.wetsuit_secondary));

                holder.genderView.setVisibility(View.VISIBLE);
                if(listItems.get(position).getGender().equals("Men's")){
                    holder.genderView.setImageResource(ct.getResources().getIdentifier("man_icon", "drawable", ct.getPackageName()));
                } else {
                    holder.genderView.setImageResource(ct.getResources().getIdentifier("woman_icon", "drawable", ct.getPackageName()));
                }

                break;
            case 2:
                holder.brand.setText(listItems.get(position).getBrandName());
                holder.prices.setText("$" + String.format("%.2f", listItems.get(position).getPrice()));
                holder.size.setText(listItems.get(position).getSizes());
                holder.size.setVisibility(View.VISIBLE);
                holder.sizeName.setVisibility(View.VISIBLE);
                holder.imageView.setBackgroundColor(ct.getResources().getColor(R.color.fins_secondary));
                break;
            case 4:
                holder.brand.setText(listItems.get(position).getBrandName());
                holder.prices.setText("$" + String.format("%.2f", listItems.get(position).getPrice()));
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView names, brand, prices, size, thickName, thickness, sizeName;
        ;
        ImageView imageView,genderView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.getContext();
            this.names = itemView.findViewById(R.id.nameTextView);
            this.imageView = itemView.findViewById(R.id.itemImageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position, imageView);
                        }
                    }
                }
            });
        }
    }

    private class MainViewHolder extends ItemViewHolder {

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class MaskViewHolder extends ItemViewHolder {

        public MaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.brand = itemView.findViewById(R.id.brandTextView);
            this.prices = itemView.findViewById(R.id.priceTextView);
        }
    }

    private class WetsuitViewHolder extends ItemViewHolder {

        public WetsuitViewHolder(@NonNull View itemView) {
            super(itemView);
            this.brand = itemView.findViewById(R.id.brandTextView);
            this.prices = itemView.findViewById(R.id.priceTextView);
            this.thickness = itemView.findViewById(R.id.thicknessTextView);
            this.thickName = itemView.findViewById(R.id.thickTextView);
            this.genderView = itemView.findViewById(R.id.genderImageView);
        }
    }

    private class FinViewHolder extends ItemViewHolder {

        public FinViewHolder(@NonNull View itemView) {
            super(itemView);
            this.brand = itemView.findViewById(R.id.brandTextView);
            this.prices = itemView.findViewById(R.id.priceTextView);
            this.size = itemView.findViewById(R.id.sizeTextView);
            this.sizeName = itemView.findViewById(R.id.sizeDisplayTextView);
        }
    }
}

