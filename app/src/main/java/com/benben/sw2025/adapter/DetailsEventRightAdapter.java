package com.benben.sw2025.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.sw2025.R;
import com.benben.sw2025.activity.DetailsEventActivity;
import com.benben.sw2025.entity.HomeEventEntity;

import java.util.Collection;
import java.util.List;

/**
 * Created by 牛海丰 on 2017/7/19.
 */

public class DetailsEventRightAdapter extends BaseAdapter {
    private Context context;
    private List<HomeEventEntity> list;

    public DetailsEventRightAdapter() {
    }

    public DetailsEventRightAdapter(Context context, List<HomeEventEntity> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.details_event_items, parent, false);
            holder = new ViewHolder();
            holder.text_step = (TextView) convertView.findViewById(R.id.text_step);
            holder.text_title = (TextView) convertView.findViewById(R.id.text_title);
            holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        switch (list.get(position).getConfigid()){
            case "1" :
                holder.text_step.setText("1");
                holder.text_title.setText("申请办事服务");
                break;
            case "2" :
                holder.text_step.setText("2");
                holder.text_title.setText("审核通过");
                break;
            case "3" :
                holder.text_step.setText("3");
                holder.text_title.setText("审核未通过");
                break;
            case "4" :
                holder.text_step.setText("4");
                holder.text_title.setText("已推送");
                break;
            case "5" :
                holder.text_step.setText("5");
                holder.text_title.setText("已响应");
                break;
            case "6" :
                holder.text_step.setText("6");
                holder.text_title.setText("正在办事");
                break;
            case "7" :
                holder.text_step.setText("7");
                holder.text_title.setText("已完成");
                break;
            case "8" :
                holder.text_step.setText("8");
                holder.text_title.setText("已评价");
                break;
            case "9" :
                holder.text_step.setText("9");
                holder.text_title.setText("异常终止");
                break;
            default:
                break;
        }
        holder.text_time.setText(list.get(position).getEventtime());
        return convertView;
    }

    static class ViewHolder {
        TextView text_step ;
        TextView text_title ;
        TextView text_time ;
    }

    public void addAll(Collection<? extends HomeEventEntity> collection) {
        list.addAll(collection);
        notifyDataSetChanged();

    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}
