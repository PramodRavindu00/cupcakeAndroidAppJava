package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app1.Adapters.OffersClass;
import com.example.app1.Adapters.OrderClass;
import com.example.app1.Adapters.TableGenerator;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MemberMyOrdersActivity extends AppCompatActivity {
    private DBHelper dbHelper;

    EditText EditTextOrderCollected;

    Button ButtonOrderCollected;
ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_order);
        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        backButton=(ImageView)findViewById(R.id.back_myOrders);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback=new Intent(MemberMyOrdersActivity.this,MemberActivity.class);
                startActivity(intentback);
            }
        });

        String userID=dbHelper.getLoggedInUserId(this);

        ArrayList<OrderClass> myOrderList = dbHelper.viewMyOrders(userID);

        ArrayList<String> headers = new ArrayList<>(Arrays.asList("Order ID", "Product ID", "Quantity","Total","Status"));

        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (OrderClass orderClass : myOrderList) {
            ArrayList<String> rowData = new ArrayList<>();
            rowData.add(String.valueOf(orderClass.getOrderID()));
            rowData.add(String.valueOf(orderClass.getProductID()));
            rowData.add(String.valueOf(orderClass.getQuantity()));
            rowData.add(String.valueOf(orderClass.getTotal()));
            rowData.add(String.valueOf(orderClass.getStatus()));
            data.add(rowData);
        }

        TableLayout tableLayout = findViewById(R.id.MyOrdersTable);
        TableGenerator.populateTable(this, tableLayout, headers, data);


        EditTextOrderCollected=(EditText) findViewById(R.id.txt_order_collected);
        ButtonOrderCollected=(Button) findViewById(R.id.btn_order_collected);

        ButtonOrderCollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderID=EditTextOrderCollected.getText().toString();
                String currentStatus=dbHelper.getOrderStatus(orderID);
                if(orderID.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter the order ID to mark as collected", Toast.LENGTH_SHORT).show();
                }
                else if(currentStatus.equals("Collected")){
                    Toast.makeText(getApplicationContext(), "Order is already marked as collected", Toast.LENGTH_SHORT).show();
                    EditTextOrderCollected.setText("");
                }
                else if(!currentStatus.equals("Completed")){
                    Toast.makeText(getApplicationContext(), "Order is not yet completed", Toast.LENGTH_SHORT).show();
                    EditTextOrderCollected.setText("");
                }
                else{
                    if(dbHelper.memberUpdateOrder(orderID)){
                        Toast.makeText(getApplicationContext(), "Order status updated as collected", Toast.LENGTH_SHORT).show();
                        EditTextOrderCollected.setText("");
                        recreate();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Order status didn't update", Toast.LENGTH_SHORT).show();
                        EditTextOrderCollected.setText("");
                    }
                }
            }
        });
    }
}