package com.example.mobwebhazi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_login);
        final EditText etUserName = findViewById(R.id.etUserName);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (etUserName.getText().toString().isEmpty()) {
                    etUserName.requestFocus();
                    etUserName.setError("Please enter your username");
                    return;
                }

                if (etPassword.getText().toString().isEmpty()) {
                    etPassword.requestFocus();
                    etPassword.setError("Please enter your password");
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("username", etUserName.getText().toString());
                startActivity(intent);
            }
        });
    }



}
