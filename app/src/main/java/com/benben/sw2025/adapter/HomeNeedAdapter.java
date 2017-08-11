package com.benben.sw2025.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.sw2025.R;
import com.benben.sw2025.entity.HomeNeedEntity;

import java.util.Collection;
import java.util.List;

/**
 * Created by 牛海丰 on 2017/8/11.
 */

public class HomeNeedAdapter extends BaseAdapter {
    private Context context;
    private List<HomeNeedEntity> list;

    public HomeNeedAdapter() {
    }

    public HomeNeedAdapter(Context context, List<HomeNeedEntity> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.home_need_items, parent, false);
            holder = new ViewHolder();
            holder.type01 = (TextView) convertView.findViewById(R.id.type01);
            holder.text_time = (TextView) convertView.findViewById(R.id.text_time);
            holder.text_type = (TextView) convertView.findViewById(R.id.text_type);
            holder.image_collect = (ImageView) convertView.findViewById(R.id.image_collect);
            holder.image_need = (ImageView) convertView.findViewById(R.id.image_need);
            holder.text_brief = (TextView) convertView.findViewById(R.id.text_brief);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.type01.setText(list.get(position).getDomain1());
        holder.text_time.setText(list.get(position).getNeedtime().substring(0,16));
        holder.text_type.setText("需求类别：" + list.get(position).getDomain2());

        holder.image_need.setImageResource(R.mipmap.ic_launcher);

        holder.text_brief.setText(list.get(position).getBrief());


        return convertView;
    }

    static class ViewHolder {
        TextView type01 ;
        TextView text_time ;
        TextView text_type ;
        ImageView image_collect ;
        ImageView image_need ;
        TextView text_brief ;
    }

    public void addAll(Collection<? extends HomeNeedEntity> collection) {
        list.addAll(collection);
        notifyDataSetChanged();

    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}
