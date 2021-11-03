package com.adexe.sembakojaya;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    TextView edtUserId;
    TextView edtPassword;
    ImageView mFoodImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_item);

        Intent myIntent = getIntent(); // this is just for example purpose
        String coba = myIntent.getStringExtra("nama");
        String jadi = myIntent.getStringExtra("harga");

        edtUserId = findViewById(R.id.detail_text);
        edtPassword = findViewById(R.id.hargaText);
        mFoodImage = findViewById(R.id.foodImageDetail);


        int resId = myIntent.getIntExtra("gambar", 0);
        mFoodImage.setImageResource(resId);

        edtUserId.setText("Nama : " + coba);
        edtPassword.setText("Harga : " + jadi);
    }
}
