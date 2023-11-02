package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.example.app1.Adapters.OffersClass;
import com.example.app1.Adapters.OrderClass;
import com.example.app1.Adapters.TableGenerator;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminAllOffersActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_all_offers);

        backButton=(ImageView)findViewById(R.id.admin_allOffers_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback=new Intent(AdminAllOffersActivity.this,Admin.class);
                startActivity(intentback);
            }
        });



        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        ArrayList<OffersClass> offersList = dbHelper.viewAllOffers();

        ArrayList<String> headers = new ArrayList<>(Arrays.asList("Offer Name", "Product", "Quantity","Offer Rate %"));

        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (OffersClass offersClass : offersList) {
            ArrayList<String> rowData = new ArrayList<>();
            rowData.add(String.valueOf(offersClass.getOfferName()));
            rowData.add(String.valueOf(offersClass.getProductName()));
            rowData.add(String.valueOf(offersClass.getMinimumQuantityReq()));
            rowData.add(String.valueOf(offersClass.getOfferRate()));
            data.add(rowData);
        }

        TableLayout tableLayout = findViewById(R.id.AdminAllOffersTable);
        TableGenerator.populateTable(this, tableLayout, headers, data);

    }
}