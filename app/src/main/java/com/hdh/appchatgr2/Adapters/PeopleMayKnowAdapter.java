package com.hdh.appchatgr2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hdh.appchatgr2.Beans.RequestFriend;
import com.hdh.appchatgr2.Beans.User;
import com.hdh.appchatgr2.R;
import com.hdh.appchatgr2.Setup;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;


public class PeopleMayKnowAdapter extends BaseAdapter {
    Context context;
    ArrayList<User> listUser;

    public PeopleMayKnowAdapter(Context context, ArrayList<User> listUser) {
        this.context = context;
        this.listUser = listUser;
    }

    @Override
    public int getCount() {
        return listUser.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {




        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.item_add_friend,viewGroup,false);
        final User user = listUser.get(i);
        TextView tvNickName = view.findViewById(R.id.tv_username_add);
        CircleImageView ivAvatar = view.findViewById(R.id.iv_avatar_add);
        final Button btnAdd = view.findViewById(R.id.btn_add_friend);


        tvNickName.setText(user.getmName());

        Picasso.with(context).load(user.getmLinkImage()).into(ivAvatar);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToRequestList(user.getmId());
            }
        });

        DatabaseReference requestReference = FirebaseDatabase.getInstance().getReference().child("RequestFriends");
        requestReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (hasSentInvition(user, dataSnapshot)) {
                    btnAdd.setText("Sent");
                    btnAdd.setEnabled(false);
                } else {
                    btnAdd.setText("Add");
                    btnAdd.setEnabled(true);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        return view;
    }




    void addToRequestList(String idUser) {
        RequestFriend requestFriend = new RequestFriend();
        requestFriend.setmIdRequest(Setup.currentUser.getmId());
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("RequestFriends").child(idUser).child(Setup.currentUser.getmId()).setValue(requestFriend);
    }

    boolean hasSentInvition(final User userOther, DataSnapshot dataSnapshot) {
        RequestFriend requestFriend = dataSnapshot.child(userOther.getmId()).child(Setup.currentUser.getmId()).getValue(RequestFriend.class);
        if (requestFriend != null) {

            return true;
        }
        return false;
    }
}
