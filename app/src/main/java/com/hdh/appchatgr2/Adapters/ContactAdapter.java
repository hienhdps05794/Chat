package com.hdh.appchatgr2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hdh.appchatgr2.Beans.Contact;
import com.hdh.appchatgr2.Beans.RequestFriend;
import com.hdh.appchatgr2.Beans.User;
import com.hdh.appchatgr2.R;
import com.hdh.appchatgr2.Setup;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS on 11/28/2017.
 */

public class ContactAdapter extends BaseAdapter {
    private Context mContext;
    private List<Contact> mContactList;

    public ContactAdapter(Context mContext, List<Contact> mContactList) {
        this.mContext = mContext;
        this.mContactList = mContactList;
    }

    @Override
    public int getCount() {
        return mContactList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_contact, parent, false);



            ViewHolder viewHolder = new ViewHolder();
            viewHolder.iv_online_user_contact_fragment = convertView.findViewById(R.id.iv_online_user_contact_fragment);
            viewHolder.tv_name_user_contact_fragment = convertView.findViewById(R.id.tv_name_user_contact_fragment);
            viewHolder.iv_user_contact_fragment = convertView.findViewById(R.id.iv_user_contact_fragment);
            rowView.setTag(viewHolder);

        }
        User user = mContactList.get(position);
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.tv_name_user_contact_fragment.setText(user.getmName());
        Picasso.with(mContext).load(user.getmLinkImage()).into(holder.iv_user_contact_fragment);

        return convertView;

    }

    void addToRequestList(String idUser) {
        Contact contact = new Contact();
        contact.setmIdFriend(Setup.currentUser.getmId());
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("Contact").child(idUser).child(Setup.currentUser.getmId()).setValue(contact);
    }

    class ViewHolder{
         CircleImageView iv_user_contact_fragment;
         TextView tv_name_user_contact_fragment;
         View iv_online_user_contact_fragment;
    }//sdadasdsdfsdsfasdfasdfsdf
}
