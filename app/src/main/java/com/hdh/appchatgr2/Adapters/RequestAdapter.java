package com.hdh.appchatgr2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hdh.appchatgr2.Beans.User;
import com.hdh.appchatgr2.R;
import com.hdh.appchatgr2.Setup;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class RequestAdapter extends BaseAdapter {
    Context context;
    private ArrayList<User> listRequests;

    public RequestAdapter(Context context, ArrayList<User> listRequests) {
        this.context = context;
        this.listRequests = listRequests;
    }

    @Override
    public int getCount() {
        return listRequests.size();
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
        view=layoutInflater.inflate(R.layout.item_request_friend,viewGroup,false);

        final User user = listRequests.get(i);

        CircleImageView ivAvatar = view.findViewById(R.id.iv_avatar_request);
        TextView tvNickName =view.findViewById(R.id.tv_username_request);
        Button btnAccept=view.findViewById(R.id.btn_accept_request);
        Button btnDeny=view.findViewById(R.id.btn_deny_request);

        tvNickName.setText(user.getmName());

        Picasso.with(context).load(user.getmLinkImage()).into(ivAvatar);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRequets(user.getmId());
            }
        });
        return view;
    }


    private void deleteRequets(String idRequest){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("RequestFriends").child(Setup.currentUser.getmId());
        databaseReference.child(idRequest).removeValue();
    }
}
