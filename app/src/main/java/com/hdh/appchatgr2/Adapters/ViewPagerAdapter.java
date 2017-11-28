package com.hdh.appchatgr2.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.hdh.appchatgr2.Fragments.FragmentContact;
import com.hdh.appchatgr2.Fragments.FragmentFindFriend;
import com.hdh.appchatgr2.Fragments.FragmentProfile;
import com.hdh.appchatgr2.Fragments.FragmentRecent;

import java.util.ArrayList;



public class ViewPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list = new ArrayList<>();
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        list.add(new FragmentRecent());
        list.add(new FragmentFindFriend());
        list.add(new FragmentContact());
        list.add(new FragmentProfile());
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
