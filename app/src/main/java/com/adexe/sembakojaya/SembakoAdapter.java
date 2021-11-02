package com.adexe.sembakojaya;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SembakoAdapter extends RecyclerView.Adapter<SembakoAdapter.ViewHolder>{
    int price = 0;
    private ArrayList<Sembako> mFoodsData;
    private Context mContext;
    private WeakReference<TextView> mTotalPriceText;
    SembakoAdapter(Context context, ArrayList<Sembako> foodsData, TextView totalPriceText) {
        this.mFoodsData = foodsData;
        this.mContext = context;
        this.mTotalPriceText = new WeakReference<>(totalPriceText);
    }

    @Override
    public SembakoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SembakoAdapter.ViewHolder holder, int position) {
        Sembako currentFood = mFoodsData.get(position);
        holder.bindTo(currentFood);
    }

    @Override
    public int getItemCount() {
        return mFoodsData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameText;
        private TextView mPriceText;
        private ImageView mFoodImage;

        ViewHolder(View itemView) {
            super(itemView);
            mNameText = (TextView)itemView.findViewById(R.id.name);
            mPriceText = (TextView)itemView.findViewById(R.id.price);
            mFoodImage = itemView.findViewById(R.id.foodImage);
            itemView.setOnClickListener(this);
        }

        void bindTo(Sembako currentFood){
            mNameText.setText(currentFood.getName());
            mPriceText.setText(String.valueOf(currentFood.getPrice()));
            mFoodImage.setImageResource(currentFood.getImageResource());
        }

        @Override
        public void onClick(View view) {
            Sembako currentFood = mFoodsData.get(getAdapterPosition());
            price += currentFood.getPrice();
            mTotalPriceText.get().setText("TOTAL = " + String.valueOf(price));
        }

        public void onClickText(View view){
            System.out.println("Coba Dong");
        }

    }
}