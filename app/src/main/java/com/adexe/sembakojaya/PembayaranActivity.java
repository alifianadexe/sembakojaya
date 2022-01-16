package com.adexe.sembakojaya;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PembayaranActivity extends AppCompatActivity {
    ListView listView;
    String[] list_belanja;
    ArrayList<HashMap<String, Object>> list_jadi_belanja = new ArrayList<>();
    String fruitNames[] = {"Banana", "Grape", "Guava", "Mango", "Orange", "Watermelon"};
    private static String url = "http://10.0.2.2/admin/api.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pembayaran);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String purchaseAmount = intent.getStringExtra("purchaseAmount");
        String listBelanja = intent.getStringExtra("listOfBelanja");
        Log.d("THEBUGXXX", listBelanja);
        list_belanja = listBelanja.toString().split(",");
        for (String part : list_belanja) {
            getProductDetail(part);
        }

        TextView purchaseAmountTv = findViewById(R.id.purchaseAmount);
        purchaseAmountTv.setText(purchaseAmount);
        EditText edtPaymentAmount = findViewById(R.id.editTextNumber);
    }

    public void changePaymentAmount() {
        Intent intent = getIntent();
        Integer purchaseAmount = Integer.parseInt(intent.getStringExtra("purchaseAmount")) ;
        EditText edtPaymentAmount = findViewById(R.id.editTextNumber);
        TextView returnAmountText = findViewById(R.id.textView18);
        if(edtPaymentAmount.getText().toString().equals("") || edtPaymentAmount.getText().toString().equals("0")) {
            return;
        }
        Integer paymentAmount = Integer.parseInt(edtPaymentAmount.getText().toString());
        Integer returnAmount = paymentAmount - purchaseAmount;
        returnAmountText.setText(returnAmount.toString());
    }

    public void finishPurchaseDetail(View view) {
        TextView kembalian = findViewById(R.id.textView18);
        TextView pembayaran = findViewById(R.id.editTextNumber);

        Toast toast = Toast.makeText(getApplicationContext(),"Total Bayar : Rp. " + kembalian.getText() + " Dengan Kembalian " + pembayaran.getText(),Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();

        finish();
    }

    public void getProductDetail(String id_product) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlGetUser = url + "?get_produk_detail="+id_product;
        JsonObjectRequest jsonObjReq = new
                JsonObjectRequest(Request.Method.GET, urlGetUser, null, new
                Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("THEBUG", String.valueOf(response));
                        try {
                            JSONObject result = response.getJSONObject("result");
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("qty", 1);
                            map.put("nama", result.getString("nama"));
                            map.put("harga", result.getInt("harga"));
                            list_jadi_belanja.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String[] from = {"fruitName", "fruitImage", "fruitImagaaaa"};
                        int to[] = {R.id.qty, R.id.nama, R.id.harga};
                        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), list_jadi_belanja, R.layout.list_item_belanjaan, from, to);
                        listView.setAdapter(simpleAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PembayaranActivity.this, "Gagal Koneksi ke server", Toast.LENGTH_LONG).show();
                Log.d("THEBUG", "onErrorResponse: " + error.getMessage());
            }
        });
        queue.add(jsonObjReq);
    }
}
