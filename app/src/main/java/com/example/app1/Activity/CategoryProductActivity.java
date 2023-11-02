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
import android.widget.Toast;

import com.example.app1.Adapters.CategoryClass;
import com.example.app1.Adapters.ProductClass;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;
import java.util.Vector;

public class CategoryProductActivity extends AppCompatActivity {
    EditText EditTextProductId, EditTextProductName, EditTextPrice, EditTextCategoryId,EditTextCategoryName;
    Spinner SpinnerCategory;
        ImageView backButton;

    Button ButtonAddCategory,ButtonAddProduct;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.app1.R.layout.activity_product);

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        backButton=(ImageView)findViewById(R.id.back_pAndC);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback=new Intent(CategoryProductActivity.this,Admin.class);
                startActivity(intentback);
            }
        });

        EditTextCategoryId= (EditText) findViewById(R.id.txt_C_CategoryID);
        EditTextCategoryName= (EditText) findViewById(R.id.txt_C_CategoryName);
        EditTextProductId = (EditText) findViewById(R.id.txt_P_ProductId);
        EditTextProductName = (EditText) findViewById(R.id.txt_P_ProductName);
        EditTextPrice = (EditText) findViewById(R.id.txt_P_Price);

        SpinnerCategory = (Spinner) findViewById(R.id.sp_P_Category);

        ButtonAddCategory =(Button) findViewById(R.id.btn_NewCategory) ;
        ButtonAddProduct = (Button) findViewById(R.id.btn_NewProduct);

        Vector<String> vecCategory = dbHelper.getCategory_Name();
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vecCategory);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerCategory.setAdapter(ad);


        ButtonAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EditTextCategoryId.getText().toString().isEmpty() || EditTextCategoryName.getText().toString().isEmpty() ){
                    Toast.makeText(getApplicationContext(), "Input fields Can't be empty", Toast.LENGTH_SHORT).show();
                } else{
                    CategoryClass categoryClass = new CategoryClass(EditTextCategoryId.getText().toString(), EditTextCategoryName.getText().toString() );
                    if(dbHelper.CreateNewCategory(categoryClass)) {
                        Toast.makeText(getApplicationContext(), "New category added", Toast.LENGTH_LONG).show();
                        EditTextCategoryId.setText("");
                        EditTextCategoryName.setText("");
                        updateCategorySpinner();
                    } else {
                        Toast.makeText(getApplicationContext(), "Category creation failed", Toast.LENGTH_LONG).show();
                        EditTextCategoryId.setText("");
                        EditTextCategoryName.setText("");
                    }
                }
            }
        });




        ButtonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditTextProductId.getText().toString().isEmpty() ||
                        EditTextProductName.getText().toString().isEmpty() ||
                        EditTextPrice.getText().toString().isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Input fields Can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    String CategoryId = dbHelper.getCategory_Id(SpinnerCategory.getSelectedItem().toString());
                    ProductClass product = new ProductClass(EditTextProductId.getText().toString(), EditTextProductName.getText().toString(), CategoryId, Integer.parseInt(EditTextPrice.getText().toString()));
                    if (dbHelper.InsertProduct(product)) {
                        Toast.makeText(getApplicationContext(), "New Product has been added", Toast.LENGTH_SHORT).show();
                        EditTextProductId.setText("");
                        EditTextProductName.setText("");
                        EditTextPrice.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "An error occurred while adding the product", Toast.LENGTH_SHORT).show();
                        EditTextProductId.setText("");
                        EditTextProductName.setText("");
                        EditTextPrice.setText("");
                    }
                }
            }
        });
    }
    private void updateCategorySpinner() {
        Vector<String> vecCategory = dbHelper.getCategory_Name();
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) SpinnerCategory.getAdapter();
        adapter.clear();
        adapter.addAll(vecCategory);
        adapter.notifyDataSetChanged();
    }
}