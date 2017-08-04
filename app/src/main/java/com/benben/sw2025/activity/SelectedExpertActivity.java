package com.benben.sw2025.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.benben.sw2025.R;
import com.benben.sw2025.adapter.HomeEventAdapter;
import com.benben.sw2025.adapter.HomeSelectedExpertAdapter;
import com.benben.sw2025.entity.HomeEventEntity;
import com.benben.sw2025.entity.HomeSelectExpertEntity;
import com.benben.sw2025.tools.GetBenSharedPreferences;
import com.benben.sw2025.tools.ToastUtils;
import com.benben.sw2025.tools.Url;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectedExpertActivity extends BenBenActivity implements View.OnClickListener {

    private ImageView img_pre ;
    private TextView text_common_title ;
    private GridView gridView ;
    private HomeSelectedExpertAdapter adapter ;
    private List<HomeSelectExpertEntity> list ;

    private TextView text_save ;

    private Set set = new HashSet() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData() ;
    }

    private void loadData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        String urls = String.format(Url.myexpert, GetBenSharedPreferences.getTicket(SelectedExpertActivity.this));
        httpUtils.send(HttpRequest.HttpMethod.POST, urls , params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("myexpert", responseInfo.result);
                JSONObject object = JSON.parseObject(responseInfo.result);
                String status_code = object.getString("return_code");
                switch (status_code) {
                    case "200":
                        JSONArray data = object.getJSONArray("data");
                        list = JSON.parseArray(data.toJSONString(), HomeSelectExpertEntity.class);
                        adapter = new HomeSelectedExpertAdapter( SelectedExpertActivity.this , list , set ) ;
                        gridView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                ToastUtils.shortToast(SelectedExpertActivity.this, "网络连接异常");
            }
        });
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_selected_expert);
    }

    @Override
    public void initViews() {
        img_pre = (ImageView)findViewById(R.id.img_pre)  ;
        text_common_title = (TextView)findViewById(R.id.text_common_title)  ;
        gridView = (GridView) findViewById(R.id.gridView)  ;
        text_save = (TextView) findViewById(R.id.text_save)  ;
    }

    @Override
    public void initListeners() {
        img_pre.setOnClickListener(this);
        text_save.setOnClickListener(this);
    }

    @Override
    public void initData() {
        text_common_title.setText("选择专家");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_pre :
                finish();
                break;
            case R.id.text_save :
                toSave() ;
                break;
            default:
                break;
        }
    }

    private void toSave() {
        String replace = set.toString().replace("[", "");
        String replace1 = replace.replace("]", "");
        if (!TextUtils.isEmpty(replace1)){
            Intent intent = new Intent() ;
            intent.putExtra("expertid" , replace1 ) ;
            setResult(RESULT_OK, intent);
            finish();
        }else{
            ToastUtils.shortToast(SelectedExpertActivity.this , "请选择至少一个专家");
        }

    }
}
