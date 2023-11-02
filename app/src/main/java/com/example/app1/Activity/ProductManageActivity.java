package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app1.Adapters.CategoryClass;
import com.example.app1.Adapters.ProductClass;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

import java.util.ArrayList;
import java.util.Vector;

public class ProductManageActivity extends AppCompatActivity {

    Spinner spinnerChangeCategory;

    EditText editTextSearchProduct,editTextChangeProductName,editTextChangePrice;

   Button ButtonSearchProduct,ButtonEditProduct,ButtonDeleteProduct;
   TextView textViewProductSearchResults;

   ImageView BackButton;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_manage);

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        BackButton=(ImageView)findViewById(R.id.back_mngProduct);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAllProducts=new Intent(ProductManageActivity.this,AllProductsActivity.class);
                startActivity(intentAllProducts);
            }
        });

        editTextSearchProduct=(EditText) findViewById(R.id.txt_Search_Product) ;
        editTextChangeProductName=(EditText)findViewById(R.id.txt_Change_Product_Name);
        editTextChangePrice=(EditText)findViewById(R.id.txt_Change_Price);
        spinnerChangeCategory=(Spinner) findViewById(R.id.sp_Product_Manage);

        ButtonSearchProduct=(Button)findViewById(R.id.btn_Search_Product);
        ButtonEditProduct=(Button)findViewById(R.id.btn_Edit_Product);
        ButtonDeleteProduct=(Button)findViewById(R.id.btn_Delete_Product);
        textViewProductSearchResults=(TextView)findViewById(R.id.ProductSearchResult) ;

        Vector<String> vecCategory = dbHelper.getCategory_Name();
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vecCategory);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChangeCategory.setAdapter(ad);

        ButtonSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String productID=editTextSearchProduct.getText().toString();
                if(productID.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter A valid Product name or ID to search", Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayList<ProductClass> productSearchResults = dbHelper.SearchProduct(productID);
                    textViewProductSearchResults.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    if(productSearchResults.isEmpty()){
                        textViewProductSearchResults.setText("No result Found");
                        editTextSearchProduct.setText("");
                    }
                    else{
                        StringBuilder stringBuilder=new StringBuilder();
                        for(ProductClass productClass:productSearchResults){
                            stringBuilder.append("Product ID: ").append(productClass.getProductID()).append("\n");
                            stringBuilder.append("Product Name: ").append(productClass.getProductName()).append("\n");
                            stringBuilder.append("Price: ").append(productClass.getPrice()).append("\n");
                            stringBuilder.append("Category Name: ").append(productClass.getCategoryName()).append("\n");
                        }
                        textViewProductSearchResults.setText(stringBuilder.toString());
                    }
                }
            }
        });

        ButtonEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextSearchProduct.getText().toString().isEmpty() ||
                        editTextChangeProductName.getText().toString().isEmpty() ||
                        editTextChangePrice.getText().toString().isEmpty()) {
                    textViewProductSearchResults.setText("");
                    Toast.makeText(getApplicationContext(), "Input fields Can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    String searchProduct=editTextSearchProduct.getText().toString();
                    String newName=editTextChangeProductName.getText().toString();
                    String newCategoryId = dbHelper.getCategory_Id(spinnerChangeCategory.getSelectedItem().toString());
                    int newPrice= Integer.parseInt(editTextChangePrice.getText().toString());
                    textViewProductSearchResults.setText("");

                    if (dbHelper.UpdateProductDetails(searchProduct,newName,newCategoryId,newPrice)) {
                        Toast.makeText(getApplicationContext(), "Product details have been updated successfully", Toast.LENGTH_SHORT).show();
                        editTextSearchProduct.setText("");
                        editTextChangeProductName.setText("");
                        editTextChangePrice.setText("");
                        recreate();
                    } else {
                        Toast.makeText(getApplicationContext(), "Product details updating failed", Toast.LENGTH_SHORT).show();
                        editTextSearchProduct.setText("");
                        editTextChangeProductName.setText("");
                        editTextChangePrice.setText("");
                    }
                }
            }
        });

        ButtonDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextSearchProduct.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input fields Can't empty", Toast.LENGTH_SHORT).show();
                    textViewProductSearchResults.setText("");
                }
                else{
                    if(dbHelper.deleteProduct(editTextSearchProduct.getText().toString())){
                        Toast.makeText(getApplicationContext(), "Product has been deleted successfully", Toast.LENGTH_SHORT).show();
                        editTextSearchProduct.setText("");
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Product deletion failed", Toast.LENGTH_SHORT).show();
                        textViewProductSearchResults.setText("");
                    }
                }
            }
        });

    }
}