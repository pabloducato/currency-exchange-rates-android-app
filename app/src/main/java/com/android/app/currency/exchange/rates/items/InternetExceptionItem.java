package com.android.app.currency.exchange.rates.items;

public class InternetExceptionItem {
    private final int mImageResource;
    private final String mInternetExceptionDate;
    private final String mInternetExceptionTime;
    private final String mInternetExceptionMessage;

    public InternetExceptionItem(int imageResource, String internetExceptionDate, String internetExceptionTime, String internetExceptionMessage) {
        mImageResource = imageResource;
        mInternetExceptionDate = internetExceptionDate;
        mInternetExceptionTime = internetExceptionTime;
        mInternetExceptionMessage = internetExceptionMessage;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getInternetExceptionDate() {
        return mInternetExceptionDate;
    }

    public String getInternetExceptionTime() {
        return mInternetExceptionTime;
    }

    public String getInternetExceptionMessage() {
        return mInternetExceptionMessage;
    }

}
