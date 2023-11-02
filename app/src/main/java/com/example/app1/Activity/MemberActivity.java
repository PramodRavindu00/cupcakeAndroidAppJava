package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app1.R;

public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        ImageView imageViewMemberAllProducts=(ImageView) findViewById(R.id.ivMemberAllProducts);
        ImageView imageViewMyOrders=(ImageView) findViewById(R.id.ivMyOrders);
        ImageView imageViewMemberOffers=(ImageView) findViewById(R.id.ivMemberOffers);
        ImageView imageViewMemberProfile=(ImageView) findViewById(R.id.ivMemberProfile);

        ImageView imageViewLogout=(ImageView)findViewById(R.id.memberLogout);

        imageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin=new Intent(MemberActivity.this,LoginActivity.class);
                startActivity(intentLogin);
                Toast.makeText(getApplicationContext(), "You have successfully logout", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewMemberAllProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMemberAllProducts=new Intent(MemberActivity.this,MemberAllProductsActivity.class);
                startActivity(intentMemberAllProducts);
            }
        });
        imageViewMyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMyOrdersActivity=new Intent(MemberActivity.this, MemberMyOrdersActivity.class);
                startActivity(intentMyOrdersActivity);
            }
        });
        imageViewMemberOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMemberOffersActivity=new Intent(MemberActivity.this,MemberOffersActivity.class);
                startActivity(intentMemberOffersActivity);
            }
        });
        imageViewMemberProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMemberProfile=new Intent(MemberActivity.this, MemberProfileActivity.class);
                startActivity(intentMemberProfile);
            }
        });

    }
}