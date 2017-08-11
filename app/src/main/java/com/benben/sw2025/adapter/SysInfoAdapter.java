package com.benben.sw2025.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.sw2025.R;
import com.benben.sw2025.entity.SysInfoEntity;

import java.util.Collection;
import java.util.List;

/**
 * Created by 牛海丰 on 2017/8/10.
 */

public class SysInfoAdapter extends BaseAdapter {
    private Context context;
    private List<SysInfoEntity> list;

    public SysInfoAdapter() {
    }

    public SysInfoAdapter(Context context, List<SysInfoEntity> list) {
        super();
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.sys_info_items, parent, false);
            holder = new ViewHolder();
            holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            holder.image_red = (ImageView) convertView.findViewById(R.id.image_red);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text_time.setText(list.get(position).getSendtime().substring(0,16));
        switch (list.get(position).getState()){
            case "0" :
                holder.image_red.setVisibility(View.VISIBLE);
                break;
            case "1" :
                holder.image_red.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
        return convertView;
    }

    static class ViewHolder {
        TextView text_time ;
        ImageView image_red ;
    }

    public void addAll(Collection<? extends SysInfoEntity> collection) {
        list.addAll(collection);
        notifyDataSetChanged();

    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}
