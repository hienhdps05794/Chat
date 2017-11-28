package com.hdh.appchatgr2.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hdh.appchatgr2.Adapters.PeopleMayKnowAdapter;
import com.hdh.appchatgr2.Adapters.RequestAdapter;
import com.hdh.appchatgr2.Beans.RequestFriend;
import com.hdh.appchatgr2.Beans.User;
import com.hdh.appchatgr2.R;
import com.hdh.appchatgr2.Setup;
import com.hdh.appchatgr2.Utility;

import java.util.ArrayList;
import java.util.Random;


public class FragmentFindFriend extends Fragment {
    private ListView lv_mayknow,lv_request;
    private PeopleMayKnowAdapter peopleMayKnowAdapter;
    private RequestAdapter requestAdapter;
    private ArrayList<User> userListMayKnow=new ArrayList<>();
    private ArrayList<User> listRequests=new ArrayList<>();
    private ArrayList<String> listIdRequests=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_friend,container,false);
        initControl(view);
        initDisplay();
        return view;
    }
    private void initDisplay() {
        initListView();
        updateDataForListViewMayKnow();
        updateDataForListViewRequest();
    }
    private void initListView(){
        peopleMayKnowAdapter=new PeopleMayKnowAdapter(getContext(),userListMayKnow);
        lv_mayknow.setAdapter(peopleMayKnowAdapter);
        requestAdapter=new RequestAdapter(getContext(),listRequests);
        lv_request.setAdapter(requestAdapter);
    }
    private void initControl(View view) {
        lv_mayknow=view.findViewById(R.id.lv_mayknow);
        lv_request=view.findViewById(R.id.lv_friend_request);

    }
    private void updateDataForListViewMayKnow(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userReference=databaseReference.child("Users");

        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Random random = new Random();
                boolean take=random.nextBoolean();
                String uid=dataSnapshot.getKey();
                if (userListMayKnow.size()<10 && !Setup.currentUser.getmId().equals(uid)){
                    User user= dataSnapshot.getValue(User.class);
                    userListMayKnow.add(user);
                    peopleMayKnowAdapter.notifyDataSetChanged();
                    Utility.setListViewHeightBasedOnChildren(lv_mayknow);

                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateDataForListViewRequest(){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference requestReference=databaseReference.child("RequestFriends").child(Setup.currentUser.getmId());
        final DatabaseReference userReference=databaseReference.child("Users");
        requestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listIdRequests.clear();
                listRequests.clear();
                Iterable<DataSnapshot> snapshots=dataSnapshot.getChildren();
                for (DataSnapshot snap :snapshots){
                    listIdRequests.add(snap.getValue(RequestFriend.class).getmIdRequest());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (int i= 0;i<listIdRequests.size();i++){
                    if (dataSnapshot.getKey().equals(listIdRequests.get(i))){
                        listRequests.add(dataSnapshot.getValue(User.class));
                        requestAdapter.notifyDataSetChanged();
                        Utility.setListViewHeightBasedOnChildren(lv_request);

                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        userListMayKnow.clear();
        listRequests.clear();
        listIdRequests.clear();
    }
}
