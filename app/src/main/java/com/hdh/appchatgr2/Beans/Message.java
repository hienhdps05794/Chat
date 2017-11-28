package com.hdh.appchatgr2.Beans;

/**
 * Created by namcb1998 on 23/11/2017.
 */

public class Message {
    String mContent;
    long mTime;
    String mIdSend;

    public Message(String mContent, long mTime, String mIdSend) {
        this.mContent = mContent;
        this.mTime = mTime;
        this.mIdSend = mIdSend;
    }

    public Message() {
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public String getmIdSend() {
        return mIdSend;
    }

    public void setmIdSend(String mIdSend) {
        this.mIdSend = mIdSend;
    }
}
