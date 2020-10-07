package com.example.androidlabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MessageListAdapter extends BaseAdapter {

    public static final int ITEM_VIEW_TYPE_SEND = 1;
    public static final int ITEM_VIEW_TYPE_RECEIVE = 2;

    private ArrayList<Message> dataList;
    private LayoutInflater mInflater;

    public MessageListAdapter(Context mContext, ArrayList<Message> dataList) {
        this.dataList = dataList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public Message getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        ViewHolderLeft holderLeft;
        ViewHolderRight holderRight;

        Message msg = getItem(i);
        int msgType = msg.getMsgType();

        if (msgType == ITEM_VIEW_TYPE_SEND) {
            if (convertView == null) {
                view = mInflater.inflate(R.layout.item_left_chat_list, null);
                holderLeft = new ViewHolderLeft();

                holderLeft.avatar = view.findViewById(R.id.img_avatar_left);
                holderLeft.send = view.findViewById(R.id.tv_msg_left);
                view.setTag(holderLeft);
            } else {
                view = convertView;
                holderLeft = (ViewHolderLeft) view.getTag();
            }
            holderLeft.send.setText(msg.getMsg());
            holderLeft.avatar.setImageResource(R.drawable.picture1);
        } else {
            if (convertView == null) {
                view = mInflater.inflate(R.layout.item_right_chat_list, null);
                holderRight = new ViewHolderRight();

                holderRight.avatar = view.findViewById(R.id.img_avatar_right);
                holderRight.receive = view.findViewById(R.id.tv_msg_right);
                view.setTag(holderRight);
            } else {
                view = convertView;
                holderRight = (ViewHolderRight) view.getTag();
            }
            holderRight.receive.setText(msg.getMsg());
            holderRight.avatar.setImageResource(R.drawable.picture2);
        }
        return view;
    }


    class ViewHolderLeft {
        TextView send;
        ImageView avatar;
    }


    class ViewHolderRight {
        TextView receive;
        ImageView avatar;
    }


}
