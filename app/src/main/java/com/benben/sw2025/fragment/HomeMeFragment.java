package com.benben.sw2025.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.sw2025.R;
import com.benben.sw2025.tools.ToastUtils;

/**
 * Created by 牛海丰 on 2017/7/18.
 */


public class HomeMeFragment extends Fragment implements View.OnClickListener {

    private TextView text_info , text_safe , text_enterprise , text_expert ,  text_set ;
    private RelativeLayout relative_icon ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_me, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //实例化组件
        initView(view);
        //注册监听事件
        initListeners();
    }

    private void initListeners() {
        text_info.setOnClickListener(this);
        text_safe.setOnClickListener(this);
        text_enterprise.setOnClickListener(this);
        text_expert.setOnClickListener(this);
        text_set.setOnClickListener(this);
        relative_icon.setOnClickListener(this);
    }

    private void initView(View v) {
        text_info = (TextView)v.findViewById(R.id.text_info ) ;
        text_safe = (TextView)v.findViewById(R.id.text_safe ) ;
        text_enterprise = (TextView)v.findViewById(R.id.text_enterprise ) ;
        text_expert = (TextView)v.findViewById(R.id.text_expert ) ;
        text_set = (TextView)v.findViewById(R.id.text_set ) ;
        relative_icon = (RelativeLayout) v.findViewById(R.id.relative_icon ) ;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_info :
                ToastUtils.shortToast(getActivity() , "系统消息");
                break;
            case R.id.text_safe :
                ToastUtils.shortToast(getActivity() , "我的账户");
                break;
            case R.id.text_enterprise :
                ToastUtils.shortToast(getActivity() , "认证企业");
                break;
            case R.id.text_expert :
                ToastUtils.shortToast(getActivity() , "认证专家");
                break;
            case R.id.text_set :
                ToastUtils.shortToast(getActivity() , "设置");
                break;
            case R.id.relative_icon :
                ToastUtils.shortToast(getActivity() , "更改个人信息");
                break;
            default:
                break;
        }
    }


}

