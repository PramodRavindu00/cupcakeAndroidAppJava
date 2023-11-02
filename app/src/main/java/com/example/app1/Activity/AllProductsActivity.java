package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.app1.Adapters.OrderClass;
import com.example.app1.Adapters.ProductClass;
import com.example.app1.Adapters.TableGenerator;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AllProductsActivity extends AppCompatActivity {

    ImageView backButton;
    Button BtnCallProductManage;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        backButton=(ImageView)findViewById(R.id.back_btn_all_products);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback=new Intent(AllProductsActivity.this,Admin.class);
                startActivity(intentback);
            }
        });

        ArrayList<ProductClass> productList = dbHelper.viewAllProducts();

        ArrayList<String> headers = new ArrayList<>(Arrays.asList("Product ID", "Product Name", "Category ID","Price"));

        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (ProductClass productClass : productList) {
            ArrayList<String> rowData = new ArrayList<>();
            rowData.add(String.valueOf(productClass.getProductID()));
            rowData.add(productClass.getProductName());
            rowData.add(productClass.getCategoryId());
            rowData.add(String.valueOf(productClass.getPrice()));
            data.add(rowData);
        }

        TableLayout tableLayout = findViewById(R.id.AdminAllProducts);
        TableGenerator.populateTable(this, tableLayout, headers, data);

        BtnCallProductManage=(Button) findViewById(R.id.call_Product_Manage);
        BtnCallProductManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentProductManage=new Intent(AllProductsActivity.this,ProductManageActivity.class);
                startActivity(intentProductManage);
            }
        });
    }
}