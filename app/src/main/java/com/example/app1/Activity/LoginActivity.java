package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app1.Adapters.OrderClass;
import com.example.app1.Adapters.UserClass;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText EditTextUserId, EditTextPassword;
    Button ButtonLogin,ButtonRegister,ButtonForgotPassword;


    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.app1.R.layout.activity_login);
        dbHelper = new DBHelper(this);
        dbHelper.openDB();
        EditTextUserId=(EditText) findViewById(com.example.app1.R.id.txt_L_UserId);
        EditTextPassword = (EditText) findViewById(R.id.txt_L_Password);
        ButtonLogin = (Button) findViewById(R.id.btn_L_Login);
        ButtonRegister=(Button)findViewById(R.id.btn_call_register);
        ButtonForgotPassword =(Button) findViewById(R.id.btn_forgot_password);




        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditTextUserId.getText().toString().isEmpty() || EditTextPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Login credentials Can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<UserClass> userDetails = dbHelper.ValidLogin(EditTextUserId.getText().toString(), EditTextPassword.getText().toString());
                    if (userDetails.size() != 0) {
                        UserClass user = userDetails.get(0);
                        String UserType = user.getUserType();
                        if (UserType.equals("Admin")) {
                            Toast.makeText(getApplicationContext(), "logged in as an " + UserType, Toast.LENGTH_SHORT).show();
                            Intent intentAdmin = new Intent(LoginActivity.this, Admin.class);
                            startActivity(intentAdmin);
                        } else if (UserType.equals("Member")) {
                            Toast.makeText(getApplicationContext(), "logged in as a " + UserType, Toast.LENGTH_SHORT).show();
                            Intent intentmember = new Intent(LoginActivity.this, MemberActivity.class);
                            startActivity(intentmember);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(intentRegister);
            }
        });
        ButtonForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForgotPassword = new Intent(LoginActivity.this , ForgotPasswordActivity.class);
                startActivity(intentForgotPassword);
            }
        });
    }
}