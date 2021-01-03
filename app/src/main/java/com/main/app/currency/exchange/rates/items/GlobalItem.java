package com.main.app.currency.exchange.rates.items;

public class GlobalItem {
    private int mImageResource;
    private String mRateAbbreviation;
    private String mRateValue;
    private String mRateDescription;
    private String mRatePrice;


    public GlobalItem(int imageResource, String rateAbbreviation, String rateValue, String rateDescription, String ratePrice) {
        mImageResource = imageResource;
        mRateAbbreviation = rateAbbreviation;
        mRateValue = rateValue;
        mRateDescription = rateDescription;
        mRatePrice = ratePrice;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getRateAbbreviation() {
        return mRateAbbreviation;
    }

    public String getRateValue() {
        return mRateValue;
    }

    public String getRateDescription() {
        return mRateDescription;
    }

    public String getRatePrice() {
        return mRatePrice;
    }
}
