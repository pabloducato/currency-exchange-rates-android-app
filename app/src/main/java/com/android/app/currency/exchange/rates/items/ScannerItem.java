package com.android.app.currency.exchange.rates.items;

public class ScannerItem {

    private int mImageResource;
    private String mScannerName;

    public ScannerItem(int imageResource, String scannerName) {
        mImageResource = imageResource;
        mScannerName = scannerName;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getScannerName() {
        return mScannerName;
    }

}
