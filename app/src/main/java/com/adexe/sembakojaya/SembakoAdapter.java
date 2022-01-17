package com.adexe.sembakojaya;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class SembakoAdapter extends RecyclerView.Adapter<SembakoAdapter.ViewHolder>{
    private WeakReference<TextView> mListofbelanja;
    int price = 0;
    private String listBelanja_str = "";
    private ArrayList<Sembako> mFoodsData;
    private Context mContext;
    private WeakReference<TextView> mTotalPriceText;
    SembakoAdapter(Context context, ArrayList<Sembako> foodsData, TextView totalPriceText,
                   TextView listofbelanja) {
        this.mFoodsData = foodsData;
        this.mContext = context;
        this.mTotalPriceText = new WeakReference<>(totalPriceText);
        this.mListofbelanja = new WeakReference<>(listofbelanja);
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

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameText;
        private TextView mPriceText;
        private TextView mDescText;
        private TextView mIdText;
        private ImageView mFoodImage;


        ViewHolder(View itemView) {
            super(itemView);
            mIdText = (TextView) itemView.findViewById(R.id.alifianadexe);
            mNameText = (TextView)itemView.findViewById(R.id.name);
            mPriceText = (TextView)itemView.findViewById(R.id.price);
            mDescText = (TextView) itemView.findViewById(R.id.deskripsi);
            mFoodImage = itemView.findViewById(R.id.foodImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Sembako currentFood = mFoodsData.get(getAdapterPosition());
                    price += currentFood.getPrice();
                    listBelanja_str += mIdText.getText()  + ",";

                    mListofbelanja.get().setText(listBelanja_str);
                    mTotalPriceText.get().setText("TOTAL = " + String.valueOf(price));
                }
            });

            mNameText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Sembako currentFood = mFoodsData.get(getAdapterPosition());
                    Intent myIntent = new Intent(mContext, DetailActivity.class);

                    myIntent.putExtra("nama", currentFood.getName());
                    myIntent.putExtra("harga", currentFood.getPrice());
                    myIntent.putExtra("gambar", currentFood.getImageResource());
                    myIntent.putExtra("deskripsi", currentFood.getDeskripsi());
                    mContext.startActivity(myIntent);
                }
            });
        }

        void bindTo(Sembako currentFood){
            Log.d("THEBUG", String.valueOf(currentFood.getId()));
            mIdText.setText(String.valueOf(currentFood.getId()));
            mNameText.setText(currentFood.getName());
            mPriceText.setText(String.valueOf(currentFood.getPrice()));
//            mFoodImage.setImageResource(currentFood.getImageResource());
            mDescText.setText(currentFood.getDeskripsi());
            Glide.with(mContext).load(currentFood.getImageResource()).into(mFoodImage);

        }

    }
}
