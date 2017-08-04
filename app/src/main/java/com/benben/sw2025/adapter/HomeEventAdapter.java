package com.benben.sw2025.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.sw2025.R;
import com.benben.sw2025.activity.DetailsEventActivity;
import com.benben.sw2025.entity.HomeEventEntity;

import java.util.Collection;
import java.util.List;

/**
 * Created by 牛海丰 on 2017/7/19.
 */

public class HomeEventAdapter extends BaseAdapter {
    private Context context;
    private List<HomeEventEntity> list;

    public HomeEventAdapter() {
    }

    public HomeEventAdapter(Context context, List<HomeEventEntity> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.home_event_items, parent, false);
            holder = new ViewHolder();
            holder.type01 = (TextView) convertView.findViewById(R.id.type01);
            holder.type02 = (TextView) convertView.findViewById(R.id.type02);
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.brief = (TextView) convertView.findViewById(R.id.brief);
            holder.text_config = (TextView) convertView.findViewById(R.id.text_config);
            holder.relative_item_head = (RelativeLayout) convertView.findViewById(R.id.relative_item_head);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.type01.setText(list.get(position).getDomain1());
        holder.type02.setText(list.get(position).getDomain2());
        holder.time.setText(list.get(position).getEventtime().substring(0,10));
        holder.brief.setText(list.get(position).getBrief());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , DetailsEventActivity.class ) ;
                intent.putExtra("type" , "" ) ;
                intent.putExtra("id" , list.get(position).getEventid() ) ;
                context.startActivity(intent);
            }
        });
        switch (list.get(position).getConfigid()){
            case "1":
                holder.text_config.setBackgroundResource(R.mipmap.green_flag);
                holder.text_config.setText("1");
                holder.relative_item_head.setBackgroundColor(Color.rgb( 47 , 216 , 375 ));
                break;
            case "2":
                holder.text_config.setBackgroundResource(R.mipmap.green_flag);
                holder.text_config.setText("2");
                holder.relative_item_head.setBackgroundColor(Color.rgb( 26 , 182 , 147));
                break;
            case "3":
                holder.text_config.setBackgroundResource(R.mipmap.green_flag);
                holder.text_config.setText("3");
                holder.relative_item_head.setBackgroundColor(Color.rgb( 26 , 12 , 147));
                break;
            case "4":
                holder.text_config.setBackgroundResource(R.mipmap.green_flag);
                holder.text_config.setText("4");
                holder.relative_item_head.setBackgroundColor(Color.rgb( 126 , 182 , 17));
                break;
            case "5":
                holder.text_config.setBackgroundResource(R.mipmap.green_flag);
                holder.text_config.setText("5");
                holder.relative_item_head.setBackgroundColor(Color.rgb( 86 , 82 , 147));
                break;
            case "6":
                holder.text_config.setBackgroundResource(R.mipmap.green_flag);
                holder.text_config.setText("6");
                holder.relative_item_head.setBackgroundColor(Color.rgb( 26 , 282 , 247));
                break;
            case "7":
                holder.text_config.setBackgroundResource(R.mipmap.green_flag);
                holder.text_config.setText("7");
                holder.relative_item_head.setBackgroundColor(Color.rgb( 26 , 182 , 247));
                break;
            case "8":
                holder.text_config.setBackgroundResource(R.mipmap.green_flag);
                holder.text_config.setText("8");
                holder.relative_item_head.setBackgroundColor(Color.rgb( 26 , 282 , 147));
                break;
            case "9":
                holder.text_config.setBackgroundResource(R.mipmap.green_flag);
                holder.text_config.setText("9");
                holder.relative_item_head.setBackgroundColor(Color.rgb( 26 , 182 , 147));
                break;

        }
        return convertView;
    }

    static class ViewHolder {
        TextView type01 ;
        TextView type02 ;
        TextView time ;
        TextView brief ;
        TextView text_config ;
        RelativeLayout relative_item_head ;
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
