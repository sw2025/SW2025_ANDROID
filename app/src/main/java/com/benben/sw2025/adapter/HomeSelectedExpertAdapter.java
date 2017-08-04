package com.benben.sw2025.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.sw2025.R;
import com.benben.sw2025.entity.HomeSelectExpertEntity;
import com.lidroid.xutils.BitmapUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 牛海丰 on 2017/8/3.
 */

public class HomeSelectedExpertAdapter extends BaseAdapter {
    private Context context;
    private List<HomeSelectExpertEntity> list;
    private Set<String> set ;

    public HomeSelectedExpertAdapter() {
    }

    public HomeSelectedExpertAdapter(Context context, List<HomeSelectExpertEntity> list , Set<String> set ) {
        super();
        this.context = context;
        this.list = list;
        this.set = set ;
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

        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.select_expert_item, parent, false);
            holder = new ViewHolder();
            holder.relative_show = (RelativeLayout) convertView.findViewById(R.id.relative_show);
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            holder.text_nickName = (TextView) convertView.findViewById(R.id.text_nickName);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text_nickName.setText(list.get(position).getExpertname());
        BitmapUtils bitmapUtils = new BitmapUtils(context) ;
        bitmapUtils.display(holder.img_icon , list.get(position).getShowimage() );

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.relative_show.isSelected()){
                    holder.relative_show.setSelected(false);
                    set.remove(list.get(position).getExpertid()) ;
                }else {
                    holder.relative_show.setSelected(true);
                    set.add(list.get(position).getExpertid()) ;
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        RelativeLayout relative_show ;
        ImageView img_icon ;
        TextView text_nickName ;
    }

    public void addAll(Collection<? extends HomeSelectExpertEntity> collection) {
        list.addAll(collection);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
}
