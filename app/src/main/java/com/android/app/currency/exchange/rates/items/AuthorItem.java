package com.android.app.currency.exchange.rates.items;

public class AuthorItem {

    private final int mImageResource;
    private final String mAuthorTitle;
    private final String mAuthorDescription;
    private final String mAuthorLink;

    public AuthorItem(int imageResource, String authorTitle, String authorDescription, String authorLink) {
        mImageResource = imageResource;
        mAuthorTitle = authorTitle;
        mAuthorDescription = authorDescription;
        mAuthorLink = authorLink;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getAuthorTitle() {
        return mAuthorTitle;
    }

    public String getAuthorDescription() {
        return mAuthorDescription;
    }

    public String getAuthorLink() {
        return mAuthorLink;
    }

}
