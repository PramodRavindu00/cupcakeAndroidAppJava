package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.app1.R;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin2);

        ImageView imageViewNewProduct = (ImageView) findViewById(R.id.ivAddProduct);

        ImageView imageViewAllProduct = (ImageView) findViewById(R.id.ivAllProducts);
        ImageView imageViewAllCategory = (ImageView) findViewById(R.id.ivAllCategory);

        ImageView imageViewOrders = (ImageView) findViewById(R.id.ivOrder);
        ImageView imageViewOffers = (ImageView) findViewById(R.id.ivOffers);
        ImageView imageViewProfile = (ImageView) findViewById(R.id.ivProfile);

        ImageView imageViewLogout=(ImageView)findViewById(R.id.adminLogout);

        imageViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin=new Intent(Admin.this,LoginActivity.class);
                startActivity(intentLogin);
                Toast.makeText(getApplicationContext(), "You have successfully logout", Toast.LENGTH_SHORT).show();
            }
        });


        imageViewNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProduct = new Intent(Admin.this, CategoryProductActivity.class);
                startActivity(intentProduct);
            }
        });


        imageViewAllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAllProduct= new Intent(Admin.this,AllProductsActivity.class);
                startActivity(intentAllProduct);
            }
        });
        imageViewAllCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAllCategory= new Intent(Admin.this,AllCategoryActivity.class);
                startActivity(intentAllCategory);
            }
        });

        imageViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOrders= new Intent(Admin.this,OrdersActivity.class);
                startActivity(intentOrders);
            }
        });
        imageViewOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentOffers= new Intent(Admin.this,OffersActivity.class);
                startActivity(intentOffers);
            }
        });
        imageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProfile= new Intent(Admin.this, ProfileActivityAdmin.class);
                startActivity(intentProfile);
            }
        });
    }
}