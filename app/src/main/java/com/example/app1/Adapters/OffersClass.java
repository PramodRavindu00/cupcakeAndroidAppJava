package com.example.app1.Adapters;

public class OffersClass {


    private String offerName;
    private String productName;
    private int minimumQuantityReq;
    private int offerRate;

    private int ActualValue;
    private int offerValue;



    public OffersClass(){}

    public OffersClass(int offerValue) {
        this.offerValue = offerValue;
    }

    public OffersClass(String productName, int minimumQuantityReq, int offerValue) {
        this.productName = productName;
        this.minimumQuantityReq = minimumQuantityReq;
        this.offerValue = offerValue;
    }

    public OffersClass(String offerName, String productName , int minimumQuantityReq, int offerRate) {
        this.offerName = offerName;
        this.productName = productName;
        this.minimumQuantityReq = minimumQuantityReq;
        this.offerRate = offerRate;
    }

    public OffersClass(String offerName, String productName, int minimumQuantityReq, int offerRate, int actualValue, int offerValue) {
        this.offerName = offerName;
        this.productName = productName;
        this.minimumQuantityReq = minimumQuantityReq;
        this.offerRate = offerRate;
        ActualValue = actualValue;
        this.offerValue = offerValue;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public int getMinimumQuantityReq() {
        return minimumQuantityReq;
    }

    public void setMinimumQuantityReq(int minimumQuantityReq) {
        this.minimumQuantityReq = minimumQuantityReq;
    }

    public int getOfferRate() {
        return offerRate;
    }

    public void setOfferRate(int offerRate) {
        this.offerRate = offerRate;
    }


    public int getActualValue() {
        return ActualValue;
    }

    public void setActualValue(int actualValue) {
        ActualValue = actualValue;
    }

    public int getOfferValue() {
        return offerValue;
    }

    public void setOfferValue(int offerValue) {
        this.offerValue = offerValue;
    }
}
