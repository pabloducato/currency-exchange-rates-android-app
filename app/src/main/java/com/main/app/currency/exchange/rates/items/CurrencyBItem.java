package com.main.app.currency.exchange.rates.items;

public class CurrencyBItem {
    private int mImageResource;
    private String mRateAbbreviation;
    private String mRateValue;
    private String mRateDescription;
    private String mRateBigValue;

    public CurrencyBItem(int imageResource, String rateAbbreviation, String rateValue, String rateDescription, String rateBigValue) {
        mImageResource = imageResource;
        mRateAbbreviation = rateAbbreviation;
        mRateValue = rateValue;
        mRateDescription = rateDescription;
        mRateBigValue = rateBigValue;
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

    public String getRateBigValue() {
        return mRateBigValue;
    }
}
