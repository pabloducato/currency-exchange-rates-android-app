package com.android.app.currency.exchange.rates.items;

public class CurrencyItem {
    private int mImageResource;
    private String mRateAbbreviation;
    private String mRateValue;
    private String mRateDescription;

    public CurrencyItem(int imageResource, String rateAbbreviation, String rateValue, String rateDescription) {
        mImageResource = imageResource;
        mRateAbbreviation = rateAbbreviation;
        mRateValue = rateValue;
        mRateDescription = rateDescription;
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
}
