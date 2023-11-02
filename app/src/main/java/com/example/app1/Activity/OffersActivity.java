package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app1.Adapters.OffersClass;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

import java.util.Vector;

public class OffersActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    Button ButtonAllOffers, ButtonDeleteOffer, ButtonNewOffer, ButtonCheckOfferName;

    EditText editTextOffername, editTextOfferQuantity, editTextDiscountRate;
    Spinner SpinnerProduct;
    TextView offerNameAvailableText;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        backButton=(ImageView)findViewById(R.id.back_offersA);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback=new Intent(OffersActivity.this,Admin.class);
                startActivity(intentback);
            }
        });

        editTextOffername = (EditText) findViewById(R.id.offer_name);
        editTextOfferQuantity = (EditText) findViewById(R.id.offer_Req_Quantity);
        editTextDiscountRate = (EditText) findViewById(R.id.offer_Rate);
        SpinnerProduct = (Spinner) findViewById(R.id.spinner_ProductName);
        offerNameAvailableText = (TextView) findViewById(R.id.offerNameCheckTextView);

        Vector<String> vecCategory = dbHelper.getProduct_Name();
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vecCategory);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerProduct.setAdapter(ad);

        ButtonCheckOfferName = (Button) findViewById(R.id.btn_offerName_check);
        ButtonNewOffer = (Button) findViewById(R.id.btn_new_Offer);
        ButtonDeleteOffer = (Button) findViewById(R.id.btn_delete_Offer);
        ButtonAllOffers = (Button) findViewById(R.id.btn_call_All_Offers);

        OffersClass offersClass = new OffersClass();

        ButtonCheckOfferName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offersClass.setOfferName(editTextOffername.getText().toString());
                if (editTextOffername.getText().toString().isEmpty()) {
                    offerNameAvailableText.setText("Enter the offer name to check availability");
                } else {

                    if (!dbHelper.offerNameAvailable(offersClass)) {
                        offerNameAvailableText.setText("Entered Offer Name is not available.Try a different offer Name");
                    } else {
                        offerNameAvailableText.setText("Offer Name is available.You can proceed with creating a new offer ");
                    }
                }
            }
        });


        ButtonNewOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextOffername.getText().toString().isEmpty()||editTextOfferQuantity.getText().toString().isEmpty() ||
                        editTextDiscountRate.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Input fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    offersClass.setOfferName(editTextOffername.getText().toString());
                    if (!dbHelper.offerNameAvailable(offersClass)) {
                        offerNameAvailableText.setText("Entered Offer Name is not available.Try a different offer Name");
                    }
                    else {
                        String offerName = editTextOffername.getText().toString();
                        String productName = SpinnerProduct.getSelectedItem().toString();
                        int quantity = Integer.parseInt(editTextOfferQuantity.getText().toString());
                        int offerRate = Integer.parseInt(editTextDiscountRate.getText().toString());

                        OffersClass oc = new OffersClass(offerName, productName, quantity, offerRate);

                        if (dbHelper.createNewOffer(oc)) {
                            Toast.makeText(getApplicationContext(), "New offer has been Introduced", Toast.LENGTH_SHORT).show();
                            editTextOffername.setText("");
                            editTextOfferQuantity.setText("");
                            editTextDiscountRate.setText("");
                        } else {
                            Toast.makeText(getApplicationContext(), "An error occurred while inserting data", Toast.LENGTH_SHORT).show();
                            editTextOffername.setText("");
                            editTextOfferQuantity.setText("");
                            editTextDiscountRate.setText("");
                        }
                    }
                }

            }
        });

        ButtonDeleteOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextOffername.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter valid offer name to delete", Toast.LENGTH_LONG).show();
                }else{
                    String offerName=editTextOffername.getText().toString();
                    if(dbHelper.deleteOffer(offerName)){
                        Toast.makeText(getApplicationContext(), "Offer has been deleted successfully", Toast.LENGTH_LONG).show();
                        editTextOffername.setText("");
                    }else{
                        Toast.makeText(getApplicationContext(), "An error occurred while deleting the offer", Toast.LENGTH_LONG).show();
                    }
                    editTextOffername.setText("");
                    offerNameAvailableText.setText("");
                }

            }
        });

        ButtonAllOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdminOffersActivity = new Intent(OffersActivity.this, AdminAllOffersActivity.class);
                startActivity(intentAdminOffersActivity);
            }
        });


    }
}
