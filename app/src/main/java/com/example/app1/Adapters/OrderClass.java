package com.example.app1.Adapters;


public class OrderClass {
    private int orderID;
    private String productID;
    private int quantity;

    private String userID;

    private int total;
    private String status;

    public OrderClass(){}


    public OrderClass(int orderID, String productID, int quantity, String status) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.status = status;
    }
    public OrderClass(String productID, int quantity, String userID) {
        this.productID = productID;
        this.quantity = quantity;
        this.userID = userID;
    }



    public OrderClass(int orderID, String productID, int quantity, String userID, int total, String status) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.userID = userID;
        this.total = total;
        this.status = status;
    }

    public OrderClass(int orderID, String productID, int quantity, int total, String status) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
