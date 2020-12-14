package com.android.app.currency.exchange.rates.items;

public class MessageItem {
    private int mImageResource;
    private String mMessageDate;
    private String mMessageTime;
    private String mMessageCopyright;
    private String mMessageTitle;
    private String mMessageDescription;
    private String mMessageLink;

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
