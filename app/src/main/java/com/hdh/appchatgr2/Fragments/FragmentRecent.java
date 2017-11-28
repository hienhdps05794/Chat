package com.hdh.appchatgr2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hdh.appchatgr2.Activities.ChatActivity;
import com.hdh.appchatgr2.Adapters.RecentAdapter;
import com.hdh.appchatgr2.Beans.Recent;
import com.hdh.appchatgr2.R;
import com.hdh.appchatgr2.Setup;

import java.util.ArrayList;
import java.util.List;

public class FragmentRecent extends Fragment{
    private List<Recent> recentsList;
    private ListView lv_recent;
    private View.OnClickListener mOnClickListener;
    private Context mContext;
    private RecentAdapter mRecentAdapter;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recent,container,false);

        initControl();
        initData();
        initDisplay();
        initEvent();
        return view;
    }

    private void initData() {
        mContext = getContext().getApplicationContext();
        recentsList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Recent recent = new Recent("Seiko Five",R.drawable.avatar_demo, "Hello I'm Test");
            recentsList.add(recent);
        }
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recent recent = (Recent) getView().getTag();
                Setup.recentUser = recent;

            }
        };

    }

    private void initDisplay() {
        mRecentAdapter = new RecentAdapter(recentsList, mContext);
        lv_recent.setAdapter(mRecentAdapter);
    }

    private void initEvent() {
        lv_recent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setTag(Setup.currentUser);
                Toast.makeText(mContext, "Okay", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, ChatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initControl() {
        lv_recent =  view.findViewById(R.id.lv_recent_fragment);
    }
}
