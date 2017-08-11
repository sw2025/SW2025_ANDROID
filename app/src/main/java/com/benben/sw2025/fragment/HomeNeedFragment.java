package com.benben.sw2025.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.benben.sw2025.R;
import com.benben.sw2025.adapter.HomeNeedAdapter;
import com.benben.sw2025.entity.HomeNeedEntity;
import com.benben.sw2025.tools.GetBenSharedPreferences;
import com.benben.sw2025.tools.ToastUtils;
import com.benben.sw2025.tools.Url;
import com.jauker.widget.BadgeView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.List;

/**
 * Created by 牛海丰 on 2017/7/18.
 */

public class HomeNeedFragment extends Fragment implements View.OnClickListener {

    private ListView listView ;
    private HomeNeedAdapter adapter ;

    private TextView text01 , text02 , text03 ,text04 , text05 , text06 , text_head_left , text_head_right ;

    private FrameLayout frame01 ,frame02 , frame03 , frame04 , frame05 , frame06 ;

    private String select = "1" ;

    private TextView text_submit ;

    private BadgeView badgeView1,badgeView2,badgeView3,badgeView4,badgeView5,badgeView6 ;


    @Override
    public void onResume() {
        super.onResume();
        loadData() ;
    }

    private void loadData() {
        String urls = String.format(Url.myNeed, GetBenSharedPreferences.getTicket(getActivity())) ;
        HttpUtils httpUtils = new HttpUtils() ;
        RequestParams params = new RequestParams() ;
        httpUtils.send(HttpRequest.HttpMethod.POST, urls, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("myNeed" , responseInfo.result ) ;
                JSONObject object = JSON.parseObject(responseInfo.result);
                String return_code = object.getString("return_code");
                switch (return_code){
                    case "200" :
                        JSONArray data = object.getJSONArray("data");
                        List<HomeNeedEntity> list = JSON.parseArray(data.toJSONString(), HomeNeedEntity.class);
                        adapter = new HomeNeedAdapter(getActivity() , list ) ;
                        listView.setAdapter(adapter );
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                ToastUtils.shortToast(getActivity() , "网络连接异常");
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_need, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //实例化组件
        initView(view);
        //注册监听事件
        initListeners();
        //加载数据
        initData() ;

        Log.e("token" , GetBenSharedPreferences.getTicket(getActivity() )) ;
    }

    private void initData() {
        /**
         * 加载虚拟数据
         */
        badgeView1 = new com.jauker.widget.BadgeView(getActivity());
        badgeView1.setTargetView(text01);
        badgeView1.setBadgeGravity(Gravity.CENTER_HORIZONTAL );
        badgeView1.setBadgeMargin(22 , 0 , 0 , 0);
        badgeView1.setBadgeCount(1);

        badgeView2 = new com.jauker.widget.BadgeView(getActivity());
        badgeView2.setTargetView(text02);
        badgeView2.setBadgeGravity(Gravity.CENTER_HORIZONTAL );
        badgeView2.setBadgeMargin(22 , 0 , 0 , 0);
        badgeView2.setBadgeCount(2);

        badgeView3 = new com.jauker.widget.BadgeView(getActivity());
        badgeView3.setTargetView(text03);
        badgeView3.setBadgeGravity(Gravity.CENTER_HORIZONTAL );
        badgeView3.setBadgeMargin(22 , 0 , 0 , 0);
        badgeView3.setBadgeCount(3);

        badgeView4 = new com.jauker.widget.BadgeView(getActivity());
        badgeView4.setBadgeGravity(Gravity.CENTER_HORIZONTAL );
        badgeView4.setBadgeMargin(22 , 0 , 0 , 0);
        badgeView4.setTargetView(text04);
        badgeView4.setBadgeCount(4);


        badgeView5 = new BadgeView(getActivity());
        badgeView5.setTargetView(text05);
        badgeView5.setBadgeGravity(Gravity.CENTER_HORIZONTAL );
        badgeView5.setBadgeMargin(22 , 0 , 0 , 0);
        badgeView5.setBadgeCount(5);

        badgeView6 = new BadgeView(getActivity());
        badgeView6.setTargetView(text06);
        badgeView6.setBadgeGravity(Gravity.CENTER_HORIZONTAL );
        badgeView6.setBadgeMargin(22 , 0 , 0 , 0);
        badgeView6.setBadgeCount(6);

        select = "1" ;
        text_head_left.setTextColor(Color.rgb(0,167,237));
        text_head_right.setTextColor(Color.rgb(102,102,102));
        frame01.setVisibility(View.VISIBLE);
        frame02.setVisibility(View.VISIBLE);
        frame03.setVisibility(View.VISIBLE);
        frame04.setVisibility(View.VISIBLE);
        frame05.setVisibility(View.GONE);
        frame06.setVisibility(View.GONE);

    }

    private void initListeners() {
        text_head_left.setOnClickListener(this);
        text_head_right.setOnClickListener(this);
        frame01.setOnClickListener(this);
        frame02.setOnClickListener(this);
        frame03.setOnClickListener(this);
        frame04.setOnClickListener(this);
        frame05.setOnClickListener(this);
        frame06.setOnClickListener(this);
    }

    private void initView(View v) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_need_head, null);
        frame01 = (FrameLayout) view.findViewById(R.id.frame01 ) ;
        frame02 = (FrameLayout) view.findViewById(R.id.frame02 ) ;
        frame03 = (FrameLayout) view.findViewById(R.id.frame03 ) ;
        frame04 = (FrameLayout) view.findViewById(R.id.frame04 ) ;
        frame05 = (FrameLayout) view.findViewById(R.id.frame05 ) ;
        frame06 = (FrameLayout) view.findViewById(R.id.frame06 ) ;
        text01 = (TextView)view.findViewById(R.id.text01 ) ;
        text02 = (TextView)view.findViewById(R.id.text02 ) ;
        text03 = (TextView)view.findViewById(R.id.text03 ) ;
        text04 = (TextView)view.findViewById(R.id.text04 ) ;
        text05 = (TextView)view.findViewById(R.id.text05 ) ;
        text06 = (TextView)view.findViewById(R.id.text06 ) ;
        text_head_right = (TextView)view.findViewById(R.id.text_head_right ) ;
        text_head_left = (TextView)view.findViewById(R.id.text_head_left ) ;
        text_submit = (TextView)view.findViewById(R.id.text_submit ) ;

        listView = (ListView)v.findViewById(R.id.listView ) ;
        listView.setDividerHeight(0);
        listView.addHeaderView(view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_head_left :
                if ("1".equals(select)){
                    Log.e("ben" , "别点了。。。") ;
                }else{
                    select = "1" ;
                    text_head_left.setTextColor(Color.rgb(0,167,237));
                    text_head_right.setTextColor(Color.rgb(102,102,102));
                    frame01.setVisibility(View.VISIBLE);
                    frame02.setVisibility(View.VISIBLE);
                    frame03.setVisibility(View.VISIBLE);
                    frame04.setVisibility(View.VISIBLE);
                    frame05.setVisibility(View.GONE);
                    frame06.setVisibility(View.GONE);
                }
                break;
            case R.id.text_head_right :
                if ("2".equals(select)){
                    Log.e("ben" , "别点了。。。") ;
                }else{
                    select = "2" ;
                    text_head_left.setTextColor(Color.rgb(102,102,102));
                    text_head_right.setTextColor(Color.rgb(0,167,237));
                    frame01.setVisibility(View.GONE);
                    frame02.setVisibility(View.GONE);
                    frame03.setVisibility(View.GONE);
                    frame04.setVisibility(View.GONE);
                    frame05.setVisibility(View.VISIBLE);
                    frame06.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.frame01 :
                ToastUtils.shortToast(getActivity() , "分类搜索01");
                break;
            case R.id.frame02 :
                ToastUtils.shortToast(getActivity() , "分类搜索02");
                break;
            case R.id.frame03 :
                ToastUtils.shortToast(getActivity() , "分类搜索03");
                break;
            case R.id.frame04 :
                ToastUtils.shortToast(getActivity() , "分类搜索04");
                break;
            case R.id.frame05 :
                ToastUtils.shortToast(getActivity() , "分类搜索05");
                break;
            case R.id.frame06 :
                ToastUtils.shortToast(getActivity() , "分类搜索06");
                break;
            case R.id.text_submit:
                ToastUtils.shortToast(getActivity() , "发布我的需求");
                break;
            default:
                break;
        }
    }


}
