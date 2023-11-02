package com.example.app1.Adapters;

public class ProductClass {
    private String ProductID;
    private String ProductName;
    private String CategoryId;
    private int Price;

    private String CategoryName;


    public ProductClass() {
    }

    public ProductClass(String productID) {
        ProductID = productID;
    }

    public ProductClass(String productName, String categoryId, int price) {
        ProductName = productName;
        CategoryId = categoryId;
        Price = price;
    }

    public ProductClass(String productID, String productName, String categoryId, int price) {
        ProductID = productID;
        ProductName = productName;
        CategoryId = categoryId;
        Price = price;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
