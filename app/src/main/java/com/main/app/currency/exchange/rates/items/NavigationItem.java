package com.main.app.currency.exchange.rates.items;

public class NavigationItem {

    private int mImageResource;
    private String mNavigationName;

    public NavigationItem(int imageResource, String navigationName) {
        mImageResource = imageResource;
        mNavigationName = navigationName;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getNavigationName() {
        return mNavigationName;
    }

}
