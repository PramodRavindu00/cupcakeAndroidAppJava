package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app1.Adapters.CategoryClass;
import com.example.app1.Adapters.TableGenerator;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class AllCategoryActivity extends AppCompatActivity {
    EditText editTextSearchCategory,editTextChangeCategory;
    TextView CategorySearchResults;
ImageView backButton;
    Button BtnSearchCategory,BtnEditCategory,BtnDeleteCategory;

 private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);
        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        backButton=(ImageView)findViewById(R.id.all_category_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentback=new Intent(AllCategoryActivity.this,Admin.class);
                startActivity(intentback);
            }
        });



        ArrayList<CategoryClass> categoryList = dbHelper.viewAllCategories();
        ArrayList<String> headers = new ArrayList<>(Arrays.asList("Category ID", "Category Name"));
        ArrayList<ArrayList<String>> data = new ArrayList<>();

        for (CategoryClass categoryClass : categoryList) {
            ArrayList<String> rowData = new ArrayList<>();
            rowData.add(String.valueOf(categoryClass.getCategoryID()));
            rowData.add(categoryClass.getCategoryName());
            data.add(rowData);
        }

        TableLayout tableLayout = findViewById(R.id.AllCategoryTable);
        TableGenerator.populateTable(this, tableLayout, headers, data);


        editTextSearchCategory=(EditText) findViewById(R.id.txt_search_Category);
        editTextChangeCategory=(EditText) findViewById(R.id.txt_edit_Category);
        CategorySearchResults=(TextView) findViewById(R.id.categorySearchResult);

        BtnSearchCategory=(Button) findViewById(R.id.btn_Category_Search);
        BtnEditCategory=(Button) findViewById(R.id.btn_Edit_Category);
        BtnDeleteCategory=(Button) findViewById(R.id.btn_Delete_Category);

        BtnSearchCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextSearchCategory.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter A valid Category name or ID to search", Toast.LENGTH_SHORT).show();
                }
                else {
                    ArrayList<CategoryClass> searchResults = dbHelper.searchCategory(editTextSearchCategory.getText().toString());
                   CategorySearchResults.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    if(searchResults.isEmpty()){
                        CategorySearchResults.setText("No result Found");
                        editTextSearchCategory.setText("");
                    }
                    else{
                        StringBuilder stringBuilder=new StringBuilder();
                        for(CategoryClass categoryClass:searchResults){
                            stringBuilder.append(categoryClass.getCategoryName());
                        }
                        CategorySearchResults.setText(stringBuilder.toString());
                    }
                }
            }
        });

        BtnEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextSearchCategory.getText().toString().isEmpty() || editTextChangeCategory.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Input fields cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    String currentName=editTextSearchCategory.getText().toString();
                    String newName=editTextChangeCategory.getText().toString();
                    if(dbHelper.editCategoryName(currentName,newName)){

                        Toast.makeText(getApplicationContext(), "Category name updated successfully", Toast.LENGTH_SHORT).show();
                        editTextSearchCategory.setText("");
                        editTextChangeCategory.setText("");
                        recreate();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Category name did not updated", Toast.LENGTH_SHORT).show();
                        editTextSearchCategory.setText("");
                        editTextChangeCategory.setText("");
                    }

                }
            }
        });

        BtnDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextSearchCategory.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter A valid Category name or ID to delete", Toast.LENGTH_SHORT).show();
                }else{
                    String key=editTextSearchCategory.getText().toString();
                    ArrayList<CategoryClass> searchResults = dbHelper.searchCategory(key);
                    if(!searchResults.isEmpty()){
                        if(dbHelper.deleteCategory(key)){
                            Toast.makeText(getApplicationContext(), "Category deleted successfully", Toast.LENGTH_SHORT).show();
                            editTextSearchCategory.setText("");
                            recreate();
                        }else{
                            Toast.makeText(getApplicationContext(), "An error occurred while deleting", Toast.LENGTH_SHORT).show();
                            editTextSearchCategory.setText("");
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Entered category is not found", Toast.LENGTH_SHORT).show();
                        editTextSearchCategory.setText("");
                   }
                }
            }
        });

    }
}