package com.example.app1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.app1.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Button ButtonCallLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonCallLogin = (Button) findViewById(R.id.btnCallLogin);

        ButtonCallLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(MainActivity.this , LoginActivity.class);
                startActivity(intentLogin);
            }
        });



    }
}