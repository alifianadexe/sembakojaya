package com.adexe.sembakojaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences shp;
    SharedPreferences.Editor shpEditor;
    EditText edtUserId, edtPassword;
    Button btnLogin;
    TextView txtInfo;
    String sharedPrefFile = "com.adexe.sembakojaya";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        edtUserId = findViewById(R.id.edtUserid);
        edtPassword = findViewById(R.id.edtPassword);
        edtUserId.setText("");
        edtPassword.setText("");
        btnLogin = findViewById(R.id.btnLogin);
        txtInfo = findViewById(R.id.txtInfo);
        shp = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtUserId.getText().toString().equals("") || edtPassword.getText().toString().equals(""))
                    txtInfo.setText("Please insert userid and password");
                else
                    DoLogin(edtUserId.getText().toString(), edtPassword.getText().toString());
            }
        });
    }


    public void DoLogin(String userid, String password) {
        String storedUsername = shp.getString("USERNAME", "secret");
        String storedPassword = shp.getString("PASSWORD", "secret");
        try {
            if (password.equals(storedPassword) && userid.equals(storedUsername)) {
                if (shp == null)
                    shp = getSharedPreferences("myPreferences", MODE_PRIVATE);

                shpEditor = shp.edit();
                shpEditor.putString("LOGIN_USERNAME", userid);
                shpEditor.commit();

                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            } else
                txtInfo.setText("Invalid Credentails");
        } catch (Exception ex) {
            txtInfo.setText(ex.getMessage().toString());
        }
    }
}
