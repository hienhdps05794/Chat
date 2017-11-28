package com.hdh.appchatgr2.Beans;

/**
 * Created by namcb1998 on 23/11/2017.
 */

public class RoomChat {
    private String mIdRoom;
    private String mID1;
    private String mID2;

    public RoomChat(String mIdRoom, String mID1, String mID2) {
        this.mIdRoom = mIdRoom;
        this.mID1 = mID1;
        this.mID2 = mID2;
    }

    public RoomChat() {
    }

    public String getmIdRoom() {
        return mIdRoom;
    }

    public void setmIdRoom(String mIdRoom) {
        this.mIdRoom = mIdRoom;
    }

    public String getmID1() {
        return mID1;
    }

    public void setmID1(String mID1) {
        this.mID1 = mID1;
    }

    public String getmID2() {
        return mID2;
    }

    public void setmID2(String mID2) {
        this.mID2 = mID2;
    }
}
