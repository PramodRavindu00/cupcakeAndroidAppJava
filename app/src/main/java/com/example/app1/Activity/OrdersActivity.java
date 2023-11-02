package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class OrdersActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    EditText EditTextOrderUpdate,EditTextOrderDelete;

    Button ButtonOrderUpdate,ButtonOrderDelete;
    RadioGroup StatusUpdate;
    RadioButton SelectedRadioButton;

    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        backButton=(ImageView)findViewById(R.id.back_ordersA);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback=new Intent(OrdersActivity.this,Admin.class);
                startActivity(intentback);
            }
        });

        ArrayList<OrderClass> ordersList = dbHelper.viewAllOrders();

        ArrayList<String> headers = new ArrayList<>(Arrays.asList("Order ID", "Product ID", "Quantity","Status"));

        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (OrderClass orderClass : ordersList) {
            ArrayList<String> rowData = new ArrayList<>();
            rowData.add(String.valueOf(orderClass.getOrderID()));
            rowData.add(String.valueOf(orderClass.getProductID()));
            rowData.add(String.valueOf(orderClass.getQuantity()));
            rowData.add(String.valueOf(orderClass.getStatus()));
            data.add(rowData);
        }

        TableLayout tableLayout = findViewById(R.id.orderTable);
        TableGenerator.populateTable(this, tableLayout, headers, data);

         EditTextOrderUpdate=(EditText) findViewById(R.id.editTextUpdateOrder);
         EditTextOrderDelete=(EditText) findViewById(R.id.editTextDeleteOrder);
         StatusUpdate=(RadioGroup) findViewById(R.id.radioGroup);
         ButtonOrderUpdate=(Button) findViewById(R.id.updateStatusButton);
         ButtonOrderDelete=(Button) findViewById(R.id.deleteOrderButton);

        ButtonOrderUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderID = EditTextOrderUpdate.getText().toString();
                int selectedRadioButtonId = StatusUpdate.getCheckedRadioButtonId();
                SelectedRadioButton = findViewById(selectedRadioButtonId);

                if (orderID.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the Order ID to update its status", Toast.LENGTH_SHORT).show();
                } else if (selectedRadioButtonId == -1) {
                    Toast.makeText(getApplicationContext(), "Order status must be checked", Toast.LENGTH_SHORT).show();
                } else {
                    String status = SelectedRadioButton.getText().toString();
                    String currentStatus = dbHelper.getOrderStatus(orderID);

                    if (currentStatus == null || currentStatus.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Order ID not found", Toast.LENGTH_SHORT).show();
                        EditTextOrderUpdate.setText("");
                        StatusUpdate.clearCheck();
                    } else if (currentStatus.equals(status) || currentStatus.equals("Collected")) {
                        Toast.makeText(getApplicationContext(), "Cannot update. Order is already " + currentStatus, Toast.LENGTH_SHORT).show();
                        EditTextOrderUpdate.setText("");
                        StatusUpdate.clearCheck();
                    } else {
                        if (dbHelper.adminUpdateOrder(orderID, status)) {
                            Toast.makeText(getApplicationContext(), "Order Status has been updated", Toast.LENGTH_SHORT).show();
                            EditTextOrderUpdate.setText("");
                            StatusUpdate.clearCheck();
                            recreate();
                        } else {
                            Toast.makeText(getApplicationContext(), "Order Status didn't update", Toast.LENGTH_SHORT).show();
                            EditTextOrderUpdate.setText("");
                            StatusUpdate.clearCheck();
                        }
                    }
                }
            }
        });




        ButtonOrderDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String orderID=EditTextOrderDelete.getText().toString();
                 String currentStatus=dbHelper.getOrderStatus(orderID);
                 if(orderID.isEmpty()){
                     Toast.makeText(getApplicationContext(), "Enter the Order ID you want to delete", Toast.LENGTH_SHORT).show();
                 }
                 else if (!currentStatus.equals("Collected")){
                     Toast.makeText(getApplicationContext(), "Order is not collected yet", Toast.LENGTH_SHORT).show();
                     EditTextOrderDelete.setText("");
                 }
                 else if(dbHelper.deleteOrder(orderID)){
                     Toast.makeText(getApplicationContext(), "Order has been deleted successfully", Toast.LENGTH_SHORT).show();
                     EditTextOrderDelete.setText("");
                    recreate();
                 }
                 else{
                     Toast.makeText(getApplicationContext(), "An error occurred while deleting the order", Toast.LENGTH_SHORT).show();
                     EditTextOrderDelete.setText("");
                 }
             }
         });

    }

}