package com.example.app1.DBClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBConnector extends SQLiteOpenHelper {

    public DBConnector(Context context) {
        super(context, "DBEg", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("Create table orderTable (orderID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " productID VARCHAR,Quantity INTEGER,UserID VARCHAR,Total INTEGER,Status VARCHAR," +
                "FOREIGN KEY(UserID) REFERENCES userInfo(UserID),FOREIGN KEY(productID) REFERENCES Product(productID))");

        sqLiteDatabase.execSQL("Create table offersTable (offerName VARCHAR PRIMARY KEY NOT NULL," +
                " productName VARCHAR,Quantity INTEGER,offerRate INTEGER,actualValue INTEGER,offerValue INTEGER," +
                "FOREIGN KEY(productName) REFERENCES Product(productName))");

        sqLiteDatabase.execSQL("Create table Category (CategoryID VARCHAR PRIMARY KEY NOT NULL, CategoryName VARCHAR)");

        sqLiteDatabase.execSQL("Create table userInfo (UserID VARCHAR PRIMARY KEY, Password VARCHAR, userType VARCHAR)");

        sqLiteDatabase.execSQL("Create table Product (ProductID VARCHAR PRIMARY KEY NOT NULL," +
                " ProductName VARCHAR, CategoryID VARCHAR, Price INTEGER, " +
                "FOREIGN KEY(CategoryID) REFERENCES Category(CategoryID))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
