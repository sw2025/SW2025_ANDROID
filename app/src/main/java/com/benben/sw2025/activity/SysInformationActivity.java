package com.benben.sw2025.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.benben.sw2025.R;
import com.benben.sw2025.adapter.SysInfoAdapter;
import com.benben.sw2025.entity.SysInfoEntity;
import com.benben.sw2025.tools.GetBenSharedPreferences;
import com.benben.sw2025.tools.ToastUtils;
import com.benben.sw2025.tools.Url;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.List;

public class SysInformationActivity extends BenBenActivity implements View.OnClickListener {

    private ImageView img_pre ;
    private TextView text_common_title ;
    private ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData() ;
    }

    private void loadData() {
        String urls = String.format(Url.mySysInfo, GetBenSharedPreferences.getTicket(SysInformationActivity.this )) ;
        HttpUtils httpUtils = new HttpUtils() ;
        RequestParams params = new RequestParams() ;
        httpUtils.send(HttpRequest.HttpMethod.POST, urls, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("mySysInfo" , responseInfo.result ) ;
                com.alibaba.fastjson.JSONObject object = JSON.parseObject(responseInfo.result);
                String return_code = object.getString("return_code");
                switch (return_code){
                    case "200":
                        JSONArray data = object.getJSONArray("data");
                        List<SysInfoEntity> list = JSON.parseArray(data.toJSONString(), SysInfoEntity.class);
                        SysInfoAdapter adapter = new SysInfoAdapter(SysInformationActivity.this , list ) ;
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                ToastUtils.shortToast(SysInformationActivity.this , "网络连接异常");
            }
        }) ;
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_sys_information);
    }

    @Override
    public void initViews() {
        img_pre = (ImageView)findViewById(R.id.img_pre ) ;
        text_common_title = (TextView) findViewById(R.id.text_common_title ) ;
        listView = (ListView) findViewById(R.id.listView ) ;
    }

    @Override
    public void initListeners() {
        img_pre.setOnClickListener(this);
    }

    @Override
    public void initData() {
        text_common_title.setText("我的消息");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_pre :
                finish();
                break;
            default:
                break;

        }
    }
}
