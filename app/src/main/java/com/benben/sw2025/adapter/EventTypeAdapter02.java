package com.benben.sw2025.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.benben.sw2025.R;
import com.benben.sw2025.entity.EventTypeEntity;

import java.util.Collection;
import java.util.List;

/**
 * Created by 牛海丰 on 2017/8/3.
 */

public class EventTypeAdapter02 extends BaseAdapter{
    private Context context;
    private List<EventTypeEntity> list;
    private int position ;

    public EventTypeAdapter02(){}

    public EventTypeAdapter02(Context context, List<EventTypeEntity> list , int position) {
        super();
        this.context = context;
        this.list = list;
        this.position = position ;
    }

    @Override
    public int getCount() {
        return list.get(position).getBenben().size();
    }

    @Override
    public Object getItem(int a) {
        return list.get(position).getBenben().get(a);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int a, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_type_item_01, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(list.get(position).getBenben().get(a).getDomainname());
        return convertView;
    }

    static class ViewHolder {
        TextView textView ;
    }

    public void addAll(Collection<? extends EventTypeEntity> collection) {
        list.addAll(collection);
        notifyDataSetChanged();

    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}
