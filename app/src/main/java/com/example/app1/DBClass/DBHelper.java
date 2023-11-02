package com.example.app1.DBClass;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.app1.Adapters.CategoryClass;
import com.example.app1.Adapters.InvoiceClass;
import com.example.app1.Adapters.OffersClass;
import com.example.app1.Adapters.OrderClass;
import com.example.app1.Adapters.ProductClass;
import com.example.app1.Adapters.UserClass;
import java.util.ArrayList;
import java.util.Vector;

public class DBHelper {
    private Context con;
    private SQLiteDatabase db;

    public DBHelper(Context con) {
        this.con = con;
    }

    public DBHelper openDB(){
        DBConnector dbCon = new DBConnector(con);
        db = dbCon.getWritableDatabase();
        return this;
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean RegisterUser(UserClass userClass){
        try{
            db.execSQL("Insert into userInfo values('"+ userClass.getUserId()+"', '"+ userClass.getPassword()+"','"+userClass.getUserType()+"')");
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public ArrayList<UserClass> ValidLogin(String UserId, String Password){
        ArrayList<UserClass> userList = new ArrayList<UserClass>();
        try{
            Cursor cursor = db.rawQuery("Select * from userInfo where UserId = '" +UserId+ "' and Password= '" +Password+ "' ", null);
            if(cursor.moveToFirst()){
                do {
                    UserClass userClass = new UserClass();
                    userClass.setUserId(cursor.getString(0));
                    userClass.setPassword(cursor.getString(1));
                    userClass.setUserType(cursor.getString(2));
                    saveLoggedInUserId(con.getApplicationContext(), cursor.getString(0));
                    userList.add(userClass);
                } while(cursor.moveToNext());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return userList;
    }
    private void saveLoggedInUserId(Context context, String userId) {
        SharedPreferences sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_id", userId);
        editor.apply();
    }
    public String getLoggedInUserId(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        return sharedPref.getString("user_id", null);
    }
    public boolean updateUserName(String userId,String loggedUserId){
        try{
            db.execSQL("UPDATE userInfo SET UserId='" + userId + "' WHERE UserId='" + loggedUserId + "'");
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public String getCurrentPassword(String userID){
        String currentUserId=null;

        try{
            Cursor cursor = db.rawQuery("Select Password from userInfo where UserID='"+ userID + "'", null);
            if(cursor.moveToFirst()){
                currentUserId=cursor.getString(0);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return currentUserId;
    }




    public boolean changePassword(String password, String loggedUserId) {
        try {
            db.execSQL("UPDATE userInfo SET password = '" + password + "' WHERE UserId = '" + loggedUserId + "'");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean deactivateUserAccount(String userID) {
        try {
            db.execSQL("DELETE FROM userInfo WHERE UserId = '" + userID + "'");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean CreateNewCategory(CategoryClass categoryClass){
        try{
            db.execSQL("Insert into Category values('"+categoryClass.getCategoryID()+"', '"+categoryClass.getCategoryName()+"')");
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public Vector<String> getCategory_Name(){
        Vector<String> vecCategory = new Vector<String>();
        try{
            Cursor cursor = db.rawQuery("Select CategoryName from Category ",null);
            if(cursor.moveToFirst()){
                do{
                    vecCategory.add(cursor.getString(0));
                }while (cursor.moveToNext());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return vecCategory;
    }

    public ArrayList<CategoryClass> searchCategory(String searchKey){
        ArrayList<CategoryClass> categoryList = new ArrayList<CategoryClass>();
        try{
            Cursor cursor = db.rawQuery("Select CategoryName from Category WHERE CategoryName = ? OR CategoryID = ?", new String[]{searchKey, searchKey});
            if(cursor.moveToFirst()){
                do {
                    CategoryClass categoryClass = new CategoryClass();
                    categoryClass.setCategoryName((cursor.getString(0)));
                    categoryList.add(categoryClass);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  categoryList;
    }
    public String getCategory_Id(String CategoryName){

        String CategoryId=null;

        try{
            Cursor cursor = db.rawQuery("Select CategoryID from Category where CategoryName='"+ CategoryName + "'", null);
            if(cursor.moveToFirst()){
                CategoryId=cursor.getString(0);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return CategoryId;
    }
    public ArrayList<CategoryClass> viewAllCategories(){
        ArrayList<CategoryClass> categoryList = new ArrayList<CategoryClass>();
        try{
            Cursor cursor = db.rawQuery("Select * from Category",null);
            if(cursor.moveToFirst()){
                do {
                    CategoryClass categoryClass = new CategoryClass();
                    categoryClass.setCategoryID(cursor.getString(0));
                    categoryClass.setCategoryName(cursor.getString(1));

                    categoryList.add(categoryClass);
                } while (cursor.moveToNext());
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  categoryList;
    }
    public boolean editCategoryName(String searchKey,String newName){
        try {
            db.execSQL("UPDATE Category SET CategoryName='" + newName + "' WHERE CategoryID='" + searchKey+ "' OR CategoryName='" + searchKey + "'");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean deleteCategory(String Key){
        try {
            db.execSQL("UPDATE Product SET CategoryID = 'NULL' WHERE CategoryID='" + Key + "'");
            db.execSQL("Delete from Category  WHERE CategoryID='" + Key+ "' OR CategoryName='" + Key + "'");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean productIDAvailable(ProductClass productClass){
        try{
            Cursor cursor=db.rawQuery    ("select ProductID from Product where ProductID=?",new String[]{productClass.getProductID()});
            if(cursor!= null && cursor.moveToFirst() ){
                cursor.close();
                return true;
            }else{
                return false;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean InsertProduct(ProductClass productClass){
        try{
            db.execSQL("Insert into Product values('"+productClass.getProductID()+"', '"+productClass.getProductName()+"', '"+productClass.getCategoryId()+"', '"+productClass.getPrice()+"' )");
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public ArrayList<ProductClass> viewAllProducts(){
        ArrayList<ProductClass> productList = new ArrayList<ProductClass>();
        try{
            Cursor cursor = db.rawQuery("Select * from Product",null);
            if(cursor.moveToFirst()){
                do {
                    ProductClass productClass = new ProductClass();
                    productClass.setProductID(cursor.getString(0));
                    productClass.setProductName(cursor.getString(1));
                    productClass.setCategoryId(cursor.getString(2));
                    productClass.setPrice(Integer.parseInt(String.valueOf(cursor.getInt(3))));
                    productList.add(productClass);
                } while (cursor.moveToNext());
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  productList;
    }
    public boolean UpdateProductDetails(String searchProduct,String newProductName,String newCategoryID,int newPrice){
        try{
            db.execSQL("UPDATE Product SET  ProductName='"+newProductName+"',CategoryID= '"+newCategoryID+"', Price='"+newPrice+"' WHERE ProductID='"+searchProduct+"' or ProductName='"+searchProduct+"' ");
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean deleteProduct(String key){
        try {

            db.execSQL("Delete from Product  WHERE ProductID='" + key+ "' OR ProductName='" + key + "'");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }
    public ArrayList<ProductClass> SearchProduct(String key){
        ArrayList<ProductClass> productList = new ArrayList<ProductClass>();
        try{
            Cursor cursor = db.rawQuery("SELECT p.ProductID, p.ProductName, c.CategoryName, p.Price " +
                    "FROM Product p " +
                    "INNER JOIN Category c ON p.CategoryID = c.CategoryID " +
                    "WHERE p.ProductID ='"+key+"' OR p.ProductName ='"+key+"'", null);
            if(cursor.moveToFirst()){
                do {
                    ProductClass productClass = new ProductClass();
                    productClass.setProductID(cursor.getString(0));
                    productClass.setProductName(cursor.getString(1));
                    productClass.setCategoryName(cursor.getString(2));
                    productClass.setPrice(cursor.getInt(3));
                    productList.add(productClass);
                } while (cursor.moveToNext());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  productList;
    }
    public Vector<String> getProduct_Name(){
        Vector<String> vecCategory = new Vector<String>();
        try{
            Cursor cursor = db.rawQuery("Select ProductName from Product ",null);
            if(cursor.moveToFirst()){
                do{
                    vecCategory.add(cursor.getString(0));
                }while (cursor.moveToNext());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return vecCategory;
    }
    public String getProduct_ID(String productName){
       String productID=null;
        try{
            Cursor cursor = db.rawQuery("Select ProductID from Product where ProductName='"+productName+"'",null);
            if(cursor.moveToFirst()){
                do{
                    productID=cursor.getString(0);
                }while (cursor.moveToNext());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return productID;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean makeOrder(OrderClass orderClass) {
        try {
            String productID = orderClass.getProductID();
            int quantity = orderClass.getQuantity();
            String userID=orderClass.getUserID();
            String status = "RECEIVED";
            int price = 0;

            String query = "SELECT Price FROM Product WHERE ProductID='" + productID + "'";

            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("Price");
                if (columnIndex >= 0) {
                    price = cursor.getInt(columnIndex);
                }
            }
            cursor.close();
            int total = price * quantity;


            String insertQuery = "INSERT INTO orderTable (ProductID, Quantity, UserID, Total, Status) " +
                    "VALUES ('" + productID + "', " + quantity + ", '" + userID + "', " + total + ", '" + status + "')";

            db.execSQL(insertQuery);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public ArrayList<OrderClass> viewAllOrders(){
        ArrayList<OrderClass> orderList = new ArrayList<OrderClass>();
        try{
            Cursor cursor = db.rawQuery("Select orderID,productID,Quantity,status from orderTable",null);
            if(cursor.moveToFirst()){
                do {

                    int orderID = Integer.parseInt(cursor.getString(0));
                    String productID = cursor.getString(1);
                    int quantity = Integer.parseInt(cursor.getString(2));
                    String status = cursor.getString(3);

                    OrderClass orderClass = new OrderClass(orderID, productID, quantity, status);
                    orderList.add(orderClass);
                } while (cursor.moveToNext());
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  orderList;
    }
    public ArrayList<OrderClass> viewMyOrders(String userId){

        ArrayList<OrderClass> MyOrderList = new ArrayList<OrderClass>();
        try{
            Cursor cursor = db.rawQuery("Select orderID,productID,Quantity,Total,Status from orderTable where userID= '" +userId+ "'",null);
            if(cursor.moveToFirst()){
                do {

                    int orderID = Integer.parseInt(cursor.getString(0));
                    String productID = cursor.getString(1);
                    int quantity = Integer.parseInt(cursor.getString(2));
                    int total=Integer.parseInt(cursor.getString(3));
                    String status = cursor.getString(4);
                    OrderClass orderClass = new OrderClass(orderID, productID, quantity,total, status);
                    MyOrderList.add(orderClass);
                } while (cursor.moveToNext());
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  MyOrderList;
    }
    public boolean adminUpdateOrder(String orderID,String status){
        try {

           db.execSQL("UPDATE orderTable set Status='"+status+"' WHERE orderID='" + orderID+ "' ");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean memberUpdateOrder(String orderID){
        try {
             String status="Collected";
            db.execSQL("UPDATE orderTable set Status='"+status+"' WHERE orderID='" + orderID+ "' ");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean deleteOrder(String orderID){
        try {

            db.execSQL("Delete FROM orderTable WHERE orderID='" + orderID+ "' ");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public String getOrderStatus(String orderID){
        String orderStatus=null;

        try{
            Cursor cursor = db.rawQuery("Select Status from orderTable where orderID='"+ orderID + "'", null);
            if(cursor.moveToFirst()){
                orderStatus=cursor.getString(0);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return orderStatus;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean offerNameAvailable(OffersClass offersClass){
        try{
            Cursor cursor=db.rawQuery    ("select offerName from offersTable where offerName=?",new String[]{offersClass.getOfferName()});
            if(cursor!= null && cursor.moveToFirst() ){
                cursor.close();
                return false;
            }else{
                return true;
            }
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean createNewOffer(OffersClass offersClass){
        try {
            String offerName = offersClass.getOfferName();
            String productName = offersClass.getProductName();
            int offerQuantity = offersClass.getMinimumQuantityReq();
            int offerRate = offersClass.getOfferRate();
            int price = 0;
            String query = "SELECT Price FROM Product where ProductName='"+productName+"'";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("Price");
                if (columnIndex >= 0) {
                    price = cursor.getInt(columnIndex);
                }

            }
            cursor.close();
            int total = price*offerQuantity;
            int discount=(total*offerRate)/100;
            int offerValue=total-discount;
            db.execSQL("Insert into offersTable(offerName, productName, Quantity, offerRate,actualValue,offerValue) values('" + offerName + "', '" +productName  + "', '" + offerQuantity + "', '" + offerRate + "','" + total + "','" + offerValue + "'  )");
            return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public ArrayList<OffersClass> viewAllOffers(){
        ArrayList<OffersClass> offersList = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery("Select * from offersTable",null);
            if(cursor.moveToFirst()){
                do {
                    OffersClass offersClass = new OffersClass();
                    offersClass.setOfferName(cursor.getString(0));
                    offersClass.setProductName(cursor.getString(1));
                    offersClass.setMinimumQuantityReq(Integer.parseInt(cursor.getString(2)));
                    offersClass.setOfferRate(Integer.parseInt(String.valueOf(cursor.getInt(3))));
                    offersClass.setActualValue(Integer.parseInt(String.valueOf(cursor.getInt(4))));
                    offersClass.setOfferValue(Integer.parseInt(String.valueOf(cursor.getInt(5))));
                    offersList.add(offersClass);
                } while (cursor.moveToNext());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  offersList;
    }

    public ArrayList<OffersClass> getOfferDetails(String offerName){
        ArrayList<OffersClass> offersList = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery("Select productName,Quantity,offerValue from offersTable where offerName='"+offerName+"'",null);
            if(cursor.moveToFirst()){
                do {
                    OffersClass offersClass = new OffersClass();
                    offersClass.setProductName(cursor.getString(0));
                    offersClass.setMinimumQuantityReq(Integer.parseInt(cursor.getString(1)));
                    offersClass.setOfferValue(Integer.parseInt(cursor.getString(2)));
                    offersList.add(offersClass);
                } while (cursor.moveToNext());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  offersList;
    }
    public boolean deleteOffer(String offerName){
        try{
            db.execSQL  ("Delete  from offersTable where offerName='"+offerName+"'");
                return true;
        } catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean makeOrderFromOffer(String productID, int quantity, String userID, int total) {
        try {
            String status = "Received";
            db.execSQL("INSERT INTO orderTable (productID, Quantity, UserID, Total, Status) VALUES (?, ?, ?, ?, ?)",
                    new String[] { productID, String.valueOf(quantity), userID, String.valueOf(total), status });
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
