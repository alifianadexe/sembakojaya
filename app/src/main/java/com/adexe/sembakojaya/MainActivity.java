package com.adexe.sembakojaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Sembako> mSembakoData;
    private SembakoAdapter mAdapter;
    private ArrayList<String> list_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mSembakoData = new ArrayList<>();
        mAdapter = new SembakoAdapter(this, mSembakoData, findViewById(R.id.totalPrice));
        mRecyclerView.setAdapter(mAdapter);
        initializeData();
    }

    private void initializeData() {
        String[] foodsName = getResources().getStringArray(R.array.foods_name);
        String[] foodsPrice = getResources().getStringArray(R.array.foods_price);
        String[] foodsDescription = getResources().getStringArray(R.array.foods_description);
        TypedArray foodImagesResources =
                getResources().obtainTypedArray(R.array.foods_images);
        mSembakoData.clear();
        for(int i=0;i<foodsName.length;i++){
            mSembakoData.add(new Sembako(foodsName[i], foodsDescription[i], Integer.parseInt(foodsPrice[i]), foodImagesResources.getResourceId(i, 0)));
        }
        foodImagesResources.recycle();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_drawer, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.updateuser) {
            startActivity(new Intent(this, UserPasswordActivity.class));
        }else if (item.getItemId() == R.id.lokasi) {
            startActivity(new Intent(this, LokasiActivity.class));
        }else if (item.getItemId() == R.id.call) {
            startActivity(new Intent(this, CallCenterActivity.class));
        }else if (item.getItemId() == R.id.smscenter) {
            startActivity(new Intent(this, SMSCenterActivity.class));
        }

        return true;
    }

    public void onClickText(View v) {
        TextView txtNama = findViewById(R.id.name);
        TextView txtHarga = findViewById(R.id.hargaText);
        TextView txtDeskripsi = findViewById(R.id.deskripsi);

        Intent myIntent = new Intent(this, DetailActivity.class);

        myIntent.putExtra("nama", txtNama.getText());
        myIntent.putExtra("harga", txtHarga.getText());
        myIntent.putExtra("deskripsi", txtDeskripsi.getText());
        myIntent.putExtra("gambar", R.id.foodImageDetail);
        startActivity(myIntent);
    }

    public void goToPurchaseDetail(View view) {
        TextView purchaseAmountTv = findViewById(R.id.totalPrice);
        String purchaseAmount = purchaseAmountTv.getText().toString().split("=")[1].trim();
        Intent intent = new Intent(this, PembayaranActivity.class);
        intent.putExtra("purchaseAmount", purchaseAmount);
        startActivity(intent);
    }



}