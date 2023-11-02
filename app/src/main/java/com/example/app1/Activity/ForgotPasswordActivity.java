package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.app1.R;

public class ForgotPasswordActivity extends AppCompatActivity {
ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backButton=(ImageView)findViewById(R.id.back_btn_reset);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback=new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                startActivity(intentback);
            }
        });
    }
}