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

import com.example.app1.Adapters.OffersClass;
import com.example.app1.Adapters.TableGenerator;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MemberOffersActivity extends AppCompatActivity {
    private DBHelper dbHelper;

    EditText editTextOrderOffer;
    Button ButtonOrderOffer;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_offers);

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        backButton=(ImageView)findViewById(R.id.back_btn_offersM);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback=new Intent(MemberOffersActivity.this,MemberActivity.class);
                startActivity(intentback);
            }
        });

        ArrayList<OffersClass> offersList = dbHelper.viewAllOffers();

        ArrayList<String> headers = new ArrayList<>(Arrays.asList("Offer Name", "Product", "Quantity","Total","Offer Value"));

        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (OffersClass offersClass : offersList) {
            ArrayList<String> rowData = new ArrayList<>();
            rowData.add(String.valueOf(offersClass.getOfferName()));
            rowData.add(String.valueOf(offersClass.getProductName()));
            rowData.add(String.valueOf(offersClass.getMinimumQuantityReq()));
           rowData.add(String.valueOf(offersClass.getActualValue()));
            rowData.add(String.valueOf(offersClass.getOfferValue()));
            data.add(rowData);
        }

        TableLayout tableLayout = findViewById(R.id.memberOffersTable);
        TableGenerator.populateTable(this, tableLayout, headers, data);

       editTextOrderOffer=(EditText) findViewById(R.id.txt_order_offer);
       ButtonOrderOffer=(Button) findViewById(R.id.btn_order_offer);

        String userID= dbHelper.getLoggedInUserId(this); //get the saved login information

       ButtonOrderOffer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             if(editTextOrderOffer.getText().toString().isEmpty()){
                 Toast.makeText(getApplicationContext(), "Enter Valid offer Name to order", Toast.LENGTH_SHORT).show();
             }
             else{
                 String offerName=editTextOrderOffer.getText().toString();  //offerName
                 String productName = null;
                 int quantity=0;
                 int total=0;
                 ArrayList<OffersClass> offerDetails=dbHelper.getOfferDetails(offerName);
                for(OffersClass offersClass:offerDetails){
                     productName=offersClass.getProductName();
                    quantity=offersClass.getMinimumQuantityReq();
                    total=offersClass.getOfferValue();
                }
                String productID= dbHelper.getProduct_ID(productName);
                if(dbHelper.makeOrderFromOffer(productID,quantity,userID,total)){
                    Toast.makeText(getApplicationContext(), "Offer has been ordered successfully", Toast.LENGTH_SHORT).show();
                    editTextOrderOffer.setText("");
                }
                else{
                    Toast.makeText(getApplicationContext(), "An error occurred while ordering", Toast.LENGTH_SHORT).show();
                    editTextOrderOffer.setText("");
                }
             }

           }
       });
    }
}