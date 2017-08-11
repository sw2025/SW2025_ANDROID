package com.benben.sw2025.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.benben.sw2025.R;
import com.benben.sw2025.activity.PersonalActivity;
import com.benben.sw2025.activity.RegisterEnterpriseActivity;
import com.benben.sw2025.activity.RegisterExpertActivity;
import com.benben.sw2025.activity.SysInformationActivity;
import com.benben.sw2025.tools.GetBenSharedPreferences;
import com.benben.sw2025.tools.ToastUtils;
import com.benben.sw2025.tools.Url;
import com.benben.sw2025.views.MyIconImageView;
import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageLoader;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * Created by 牛海丰 on 2017/7/18.
 */


public class HomeMeFragment extends Fragment implements View.OnClickListener {

    private TextView text_info , text_safe , text_enterprise , text_expert ,  text_set ;
    private RelativeLayout relative_icon ;

    private TextView text_tel , text_nickName ;
    private MyIconImageView img_icon ;
    private String avatar , phone , name ;
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
        text_nickName = (TextView) v.findViewById(R.id.text_nickName ) ;
        text_tel = (TextView) v.findViewById(R.id.text_tel ) ;
        img_icon = (MyIconImageView) v.findViewById(R.id.img_icon ) ;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData() ;
    }

    private void loadData() {
        String urls = String.format(Url.authMe, GetBenSharedPreferences.getTicket(getActivity()));
        HttpUtils httpUtils = new HttpUtils()  ;
        RequestParams params = new RequestParams() ;
        httpUtils.send(HttpRequest.HttpMethod.POST, urls, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("authMe" , responseInfo.result ) ;
                JSONArray objects = JSON.parseArray(responseInfo.result);
                JSONObject jsonObject = objects.getJSONObject(0);
                String return_code = jsonObject.getString("return_code");
                switch (return_code){
                    case "200" :
                        JSONObject user = jsonObject.getJSONObject("user");
                        name = user.getString("name");
                        phone = user.getString("phone");
                        avatar = user.getString("avatar");
                        if (!TextUtils.isEmpty(phone)) {
                            text_tel.setText(phone);
                        }
                        if (!TextUtils.isEmpty(name)) {
                            text_nickName.setText("昵称： " + name);
                        }
                        if (!TextUtils.isEmpty(avatar)){
                            Glide.with(getActivity())
                                    //.load(Url.FileIP + avatar)
                                    .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502352548081&di=4f8bd5b819d36a1d531812cb05198965&imgtype=0&src=http%3A%2F%2Fzhidao.yxlady.com%2FUploads%2Fproblem_caina%2F14477431636887.jpg")
                                    .into(img_icon);
                        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_info :
                Intent intent01 = new Intent(getActivity() , SysInformationActivity.class ) ;
                startActivity(intent01);
                break;
            case R.id.text_safe :
                ToastUtils.shortToast(getActivity() , "我的账户");
                break;
            case R.id.text_enterprise :
                //ToastUtils.shortToast(getActivity() , "认证企业");
                Intent intent02 = new Intent(getActivity() , RegisterEnterpriseActivity.class ) ;
                startActivity(intent02);
                break;
            case R.id.text_expert :
                //ToastUtils.shortToast(getActivity() , "认证专家");
                Intent intent03 = new Intent(getActivity() , RegisterExpertActivity.class ) ;
                startActivity(intent03);
                break;
            case R.id.text_set :
                ToastUtils.shortToast(getActivity() , "设置");
                break;
            case R.id.relative_icon :
                //ToastUtils.shortToast(getActivity() , "更改个人信息");
                Intent intent = new Intent(getActivity() , PersonalActivity.class ) ;
                intent.putExtra("name" ,name ) ;
                intent.putExtra("phone" ,phone ) ;
                intent.putExtra("avatar" ,avatar ) ;
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}

