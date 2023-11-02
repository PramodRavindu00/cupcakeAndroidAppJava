package com.example.app1.Adapters;

public class InvoiceClass {
    private String InvoiceId;
    private String ProductId;
    private int Qty;
    private int Total;

    public InvoiceClass(String invoiceId, String productId, int qty, int total) {
        InvoiceId = invoiceId;
        ProductId = productId;
        Qty = qty;
        Total = total;
    }

    public String getInvoiceId() {
        return InvoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        InvoiceId = invoiceId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
