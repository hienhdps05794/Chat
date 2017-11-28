package com.hdh.appchatgr2.Beans;

/**
 * Created by ASUS on 11/26/2017.
 */

public class Recent {
    private String mName;
    private int mLinkImage;
    private String mLastMess;

    public Recent(String mName, int mLinkImage, String mLastMess) {
        this.mName = mName;
        this.mLinkImage = mLinkImage;
        this.mLastMess = mLastMess;
    }

    public Recent() {
    }

    public String getmName() {
    
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmLinkImage() {
        return mLinkImage;
    }

    public void setmLinkImage(int mLinkImage) {
        this.mLinkImage = mLinkImage;
    }

    public String getmLastMess() {
        return mLastMess;
    }

    public void setmLastMess(String mLastMess) {
        this.mLastMess = mLastMess;
    }
}
