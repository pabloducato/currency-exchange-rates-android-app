package com.android.app.currency.exchange.rates.items;

public class MessageItem {
    private final int mImageResource;
    private final String mMessageDate;
    private final String mMessageTime;
    private final String mMessageCopyright;
    private final String mMessageTitle;
    private final String mMessageDescription;
    private final String mMessageLink;

    public MessageItem(int imageResource, String messageDate, String messageTime, String messageCopyright, String messageTitle, String messageDescription, String messageLink) {
        mImageResource = imageResource;
        mMessageDate = messageDate;
        mMessageTime = messageTime;
        mMessageCopyright = messageCopyright;
        mMessageTitle = messageTitle;
        mMessageDescription = messageDescription;
        mMessageLink = messageLink;
    }

    public int getImageResource() {
        return mImageResource;
    }

    public String getMessageDate() {
        return mMessageDate;
    }

    public String getMessageTime() {
        return mMessageTime;
    }

    public String getMessageCopyright() {
        return mMessageCopyright;
    }

    public String getMessageTitle() {
        return mMessageTitle;
    }

    public String getMessageDescription() {
        return mMessageDescription;
    }

    public String getMessageLink() {
        return mMessageLink;
    }

}
