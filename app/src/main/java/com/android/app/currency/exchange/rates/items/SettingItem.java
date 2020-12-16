package com.android.app.currency.exchange.rates.items;

public class SettingItem {

    private int mImageResource;
    private String mSettingName;

    public SettingItem(int imageResource, String settingName) {
        mImageResource = imageResource;
        mSettingName = settingName;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getSettingName() {
        return mSettingName;
    }

}
