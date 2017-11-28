package com.hdh.appchatgr2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hdh.appchatgr2.Beans.Contact;
import com.hdh.appchatgr2.Beans.Recent;
import com.hdh.appchatgr2.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS on 11/18/2017.
 */

public class RecentAdapter extends BaseAdapter {
    private List<Recent> mRencentUsersList;
    private Context mContext;



    public RecentAdapter(List<Recent> mRencentUsersList, Context mContext) {
        this.mRencentUsersList = mRencentUsersList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mRencentUsersList.size();
    }

    @Override
    public Recent getItem(int position) {
        return mRencentUsersList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null){
            LayoutInflater inflater =  (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_recent, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tv_lastMess_recent = rowView.findViewById(R.id.tv_last_mess_recent_fragment);
            viewHolder.tv_nameUser_recent = rowView.findViewById(R.id.tv_name_recent_fragment);
            viewHolder.iv_icon_user_recent = rowView.findViewById(R.id.iv_user_recent_fragment);
            rowView.setTag(viewHolder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();
        Recent recent = mRencentUsersList.get(position);
        holder.tv_nameUser_recent.setText(recent.getmName());
        holder.tv_lastMess_recent.setText(recent.getmLastMess());
        holder.iv_icon_user_recent.setImageResource(recent.getmLinkImage());


        return rowView;
    }
    class ViewHolder{
        public TextView tv_nameUser_recent;
        public TextView tv_lastMess_recent;
        public CircleImageView iv_icon_user_recent;

    }
}
