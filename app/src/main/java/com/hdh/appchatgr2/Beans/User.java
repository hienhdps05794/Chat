package com.hdh.appchatgr2.Beans;

/**
 * Created by ASUS on 11/20/2017.
 */

public class User {

    public static final String DEFAULT_AVATAR = "https://firebasestorage.googleapis.com/v0/b/appchatgr" +
            "2-e25eb.appspot.com/o/anonymous.jpg?alt=media&token=d7a2e26d-0d2d-4cd9-a6fa-247066b26731";
    public static final String NOT_UP_DATE_YET="Chưa cập nhật";

    private String mId;
    private String mName;
    private String mLinkImage;
    private boolean mIsOnline;
    private String mProvince;
    private String mOccupation;
    private String mBirth;

    public User(String mId, String mName, String mLinkImage, boolean mIsOnline, String mProvince, String mOccupation,String mBirth) {
        this.mId = mId;
        this.mName = mName;
        this.mLinkImage = mLinkImage;
        this.mIsOnline = mIsOnline;
        this.mProvince = mProvince;
        this.mOccupation = mOccupation;
        this.mBirth=mBirth;
    }

    public User() {
    }

    public String getmProvince() {
        return mProvince;
    }

    public void setmProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getmOccupation() {
        return mOccupation;
    }

    public void setmOccupation(String mOccupation) {
        this.mOccupation = mOccupation;
    }

    public String getmBirth() {
        return mBirth;
    }

    public void setmBirth(String mBirth) {
        this.mBirth = mBirth;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmLinkImage() {
        return mLinkImage;
    }

    public void setmLinkImage(String mLinkImage) {
        this.mLinkImage = mLinkImage;
    }

    public boolean ismIsOnline() {
        return mIsOnline;
    }

    public void setmIsOnline(boolean mIsOnline) {
        this.mIsOnline = mIsOnline;
    }
}
