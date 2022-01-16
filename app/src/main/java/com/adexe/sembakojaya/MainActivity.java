package com.adexe.sembakojaya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private ArrayList<Sembako> mSembakoData;
    private SembakoAdapter mAdapter;
    private SwipeRefreshLayout swipe;
    private ArrayList<String> list_barang;
    RecyclerView.LayoutManager layoutManager;
    private static String url = "http://10.0.2.2/admin/api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           mSembakoData = new ArrayList<>();
                           swipe.setRefreshing(true);
                           mSembakoData.clear();
// adapterRecyclerView.notifyDataSetChanged();
                           mRecyclerView.setHasFixedSize(true);
                           layoutManager = new
                                   GridLayoutManager(getApplicationContext(), 2);
                           mRecyclerView.setLayoutManager(layoutManager);
                           mAdapter = new
                                   SembakoAdapter(getApplicationContext(), mSembakoData,
                                   findViewById(R.id.totalPrice));
                           mRecyclerView.setAdapter(mAdapter);
                           display();
                       }
                   }
        );
//        initializeData();
    }

    private void initializeData() {
        String[] foodsName = getResources().getStringArray(R.array.foods_name);
        String[] foodsPrice = getResources().getStringArray(R.array.foods_price);
        String[] foodsDescription = getResources().getStringArray(R.array.foods_description);
        TypedArray foodImagesResources =
                getResources().obtainTypedArray(R.array.foods_images);
        mSembakoData.clear();
        for (int i = 0; i < foodsName.length; i++) {
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
        } else if (item.getItemId() == R.id.lokasi) {
            startActivity(new Intent(this, LokasiActivity.class));
        } else if (item.getItemId() == R.id.call) {
            startActivity(new Intent(this, CallCenterActivity.class));
        } else if (item.getItemId() == R.id.smscenter) {
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

    public void onRefresh() {
        mSembakoData.clear();
        mAdapter.notifyDataSetChanged();
        display();
    }

    private void display() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlGetProduct = url + "?get_produk=1";
        JsonArrayRequest jsonArrayRequest = new
                JsonArrayRequest(Request.Method.GET, urlGetProduct, null, new
                Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Log.d("THEBUG", String.valueOf(object));
                                Sembako datas = new Sembako(
                                        object.getString("nama").toString(),
                                        object.getString("deskripsi").toString(),
                                        object.getInt("harga"),
                                        1
                                );
                                mSembakoData.add(datas);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        swipe.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Gagal Koneksi ke server", Toast.LENGTH_LONG).show();
                Log.d("tag", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }
}