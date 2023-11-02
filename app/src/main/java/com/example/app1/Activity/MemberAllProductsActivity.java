package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.app1.Adapters.UserClass;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MemberAllProductsActivity extends AppCompatActivity {
    private DBHelper dbHelper;

    EditText editTextproductID,editTextQuantity;
    Button btnOrder;
ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_all_products);

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

backButton=(ImageView)findViewById(R.id.back_btn_allProductM);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback=new Intent(MemberAllProductsActivity.this,MemberActivity.class);
                startActivity(intentback);
            }
        });

        String userID=dbHelper.getLoggedInUserId(this);

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

        TableLayout tableLayout = findViewById(R.id.memberAllProducts);
        TableGenerator.populateTable(this, tableLayout, headers, data);

        editTextproductID=(EditText) findViewById(R.id.order_Product_ID);
        editTextQuantity=(EditText) findViewById(R.id.order_quantity);
        btnOrder=(Button) findViewById(R.id.btn_order);

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextproductID.getText().toString().isEmpty() || editTextQuantity.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input fields can not be empty", Toast.LENGTH_SHORT).show();
                }
                else{

                    String productID=editTextproductID.getText().toString();
                    int quantity= Integer.parseInt(editTextQuantity.getText().toString());

                    OrderClass orderClass;
                    orderClass = new OrderClass(productID,quantity,userID);
                    ProductClass productClass=new ProductClass(productID);

                    if (dbHelper.productIDAvailable(productClass)) {
                        if(dbHelper.makeOrder(orderClass)){
                            Toast.makeText(getApplicationContext(), "Your order has made successfully", Toast.LENGTH_SHORT).show();
                            editTextproductID.setText("");
                            editTextQuantity.setText("");
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "An error occurred while making order", Toast.LENGTH_SHORT).show();
                            editTextproductID.setText("");
                            editTextQuantity.setText("");
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "You entered an unavailable product ID,try again with an available product", Toast.LENGTH_SHORT).show();
                        editTextproductID.setText("");
                        editTextQuantity.setText("");
                    }
                }
            }
        });

    }
}