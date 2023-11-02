package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app1.Adapters.UserClass;
import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

public class RegisterActivity extends AppCompatActivity {
    EditText EditTextUserId, EditTextPassword, EditTextConfirmPassword;
    Button ButtonRegister,ButtonBackToLogin;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.app1.R.layout.activity_register);

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        EditTextUserId=(EditText) findViewById(R.id.txt_userReg_UserId);
        EditTextPassword=(EditText) findViewById(R.id.txt_userReg_Password);
        EditTextConfirmPassword=(EditText) findViewById(R.id.txt_userReg_ConfirmPassword);
        ButtonRegister = (Button) findViewById(R.id.btn_userReg_Register);
        ButtonBackToLogin=(Button) findViewById(R.id.btn_Back_To_Register);





       ButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditTextUserId.getText().toString().isEmpty() || EditTextPassword.getText().toString().isEmpty()
                || EditTextConfirmPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Fields Can't be blank", Toast.LENGTH_SHORT).show();
                } else if (EditTextPassword.getText().toString().length() < 6){
                    Toast.makeText(getApplicationContext(),"Password must have more than 6 characters",
                            Toast.LENGTH_LONG).show();
                } else if (!EditTextPassword.getText().toString().equals(EditTextConfirmPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Password and confirm password should match", Toast.LENGTH_SHORT).show();
                } else{
                    UserClass userClass = new UserClass(EditTextUserId.getText().toString(), EditTextPassword.getText().toString());
                    if(dbHelper.RegisterUser(userClass)) {
                        Toast.makeText(getApplicationContext(), "User has registered successfully", Toast.LENGTH_SHORT).show();
                   } else {
                        Toast.makeText(getApplicationContext(), "User registration failed", Toast.LENGTH_SHORT).show();
                   }
              }}
        });

        ButtonBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogin = new Intent(RegisterActivity.this , LoginActivity.class);
                startActivity(intentLogin);
            }
        });
    }
}