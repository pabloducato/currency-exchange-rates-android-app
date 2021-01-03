package com.main.app.currency.exchange.rates.items;

public class CryptoItem {
    private int mImageResource;
    private String mRateAbbreviation;
    private String mRateValue;
    private String mRateDescription;
    private String mRatePercentage;

    public CryptoItem(int imageResource, String rateAbbreviation, String rateValue, String rateDescription, String ratePercentage) {
        mImageResource = imageResource;
        mRateAbbreviation = rateAbbreviation;
        mRateValue = rateValue;
        mRateDescription = rateDescription;
        mRatePercentage = ratePercentage;
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

    public String getRatePercentage() {
        return mRatePercentage;
    }

}
