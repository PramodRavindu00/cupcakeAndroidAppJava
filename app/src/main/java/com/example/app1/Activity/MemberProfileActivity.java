package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app1.DBClass.DBHelper;
import com.example.app1.R;

public class MemberProfileActivity extends AppCompatActivity {

    EditText editTextChangeUserName,editTextCurrentPassword,editTextNewPassword,editTextConfirmPassword;
    Button ChangeUserName,ChangePassword,DeactivateAccount;
    TextView userNameTextView;
    ImageView BackButton;
    private DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

        dbHelper = new DBHelper(this);
        dbHelper.openDB();

        userNameTextView=(TextView) findViewById(R.id.textViewUserNameM);

        editTextChangeUserName=(EditText) findViewById(R.id.txt_edit_userNameM);
        editTextCurrentPassword=(EditText) findViewById(R.id.txt_currentPasswordM);
        editTextNewPassword=(EditText) findViewById(R.id.txt_newPasswordM);
        editTextConfirmPassword=(EditText) findViewById(R.id.txt_confirm_newPasswordM);

        ChangeUserName=(Button) findViewById(R.id.btn_edit_userNameM);
        ChangePassword=(Button) findViewById(R.id.btn_changePasswordM);
        DeactivateAccount=(Button) findViewById(R.id.btn_deactivateAccountM);
        BackButton=(ImageView) findViewById(R.id.back_btn_profileM);

        String loggedInUserID=dbHelper.getLoggedInUserId(this);

        userNameTextView.setText("Your user ID : "+loggedInUserID);

        ChangeUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextChangeUserName.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Enter an User ID to update", Toast.LENGTH_SHORT).show();
                }else{
                    String userID=editTextChangeUserName.getText().toString();
                    if(dbHelper.updateUserName(userID,loggedInUserID)){
                        Toast.makeText(getApplicationContext(), "User ID updated successfully", Toast.LENGTH_SHORT).show();
                        editTextChangeUserName.setText("");
                        userNameTextView.setText("Your user ID : "+userID);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "User ID didn't updated", Toast.LENGTH_SHORT).show();
                        editTextChangeUserName.setText("");
                    }
                }
            }
        });

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentPassword = editTextCurrentPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Input fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    String checkCurrentPassword = dbHelper.getCurrentPassword(loggedInUserID);

                    if (!currentPassword.equals(checkCurrentPassword)) {
                        Toast.makeText(getApplicationContext(), "Invalid current password", Toast.LENGTH_SHORT).show();
                        editTextCurrentPassword.setText("");
                    } else if (newPassword.equals(currentPassword)) {
                        Toast.makeText(getApplicationContext(), "Cannot use the same password for the new password", Toast.LENGTH_SHORT).show();
                        editTextNewPassword.setText("");
                        editTextConfirmPassword.setText("");
                    } else if (newPassword.length() < 6) {
                        Toast.makeText(getApplicationContext(), "New password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                        editTextNewPassword.setText("");
                        editTextConfirmPassword.setText("");
                    } else if (!newPassword.equals(confirmPassword)) {
                        Toast.makeText(getApplicationContext(), "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                        editTextNewPassword.setText("");
                        editTextConfirmPassword.setText("");
                    } else {
                        if (dbHelper.changePassword(newPassword,loggedInUserID)) {
                            Toast.makeText(getApplicationContext(), "Password updated successfully,You have to login again", Toast.LENGTH_SHORT).show();
                            Intent intentlogin=new Intent(MemberProfileActivity.this,LoginActivity.class);
                            startActivity(intentlogin);
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to update password", Toast.LENGTH_SHORT).show();
                            editTextCurrentPassword.setText("");
                            editTextNewPassword.setText("");
                            editTextConfirmPassword.setText("");
                        }
                    }
                }
            }
        });


        DeactivateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.deactivateUserAccount(loggedInUserID)){
                    Toast.makeText(getApplicationContext(), "Account Deactivated.You will be navigated to login page", Toast.LENGTH_SHORT).show();
                    Intent intentlogin=new Intent(MemberProfileActivity.this,LoginActivity.class);
                    startActivity(intentlogin);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Account deactivation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToDashboard=new Intent(MemberProfileActivity.this,MemberActivity.class);
                startActivity(backToDashboard);
            }
        });
    }
}