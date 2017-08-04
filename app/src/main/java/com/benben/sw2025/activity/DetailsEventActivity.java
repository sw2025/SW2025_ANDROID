package com.benben.sw2025.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.benben.sw2025.R;
import com.benben.sw2025.adapter.DetailsEventRightAdapter;
import com.benben.sw2025.adapter.EventTypeAdapter;
import com.benben.sw2025.adapter.EventTypeAdapter02;
import com.benben.sw2025.entity.EventTypeEntity;
import com.benben.sw2025.entity.HomeEventEntity;
import com.benben.sw2025.tools.GetBenSharedPreferences;
import com.benben.sw2025.tools.ToastUtils;
import com.benben.sw2025.tools.Url;
import com.benben.sw2025.views.MyListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.Collections;
import java.util.List;

public class DetailsEventActivity extends BenBenActivity implements View.OnClickListener {

    private ImageView img_pre ;
    private TextView text_common_title ;
    private ImageView common_right ;
    private DrawerLayout drawer ;

    private ScrollView scrollView ;
    // 展示第几阶段
    private TextView text_ben , text_line234 ;
    // view
    private FrameLayout frame_view_01 , frame_view_234  , frame_view_06;
    // 01 选择问题分类
    private TextView text_select ;
    // 01 问题描述
    private EditText edit_des ;
    // 01系统分配
    private TextView text_default_expert ;
    // 01指定专家
    private TextView text_select_expert ;
    // 01提交
    private TextView textView_submit ;

    //02类别1；2
    private TextView text_type01 , text_type02 ;
    //02描述
    private TextView text_des ;
    //02专家选择类型
    private TextView text_expert_type ;

    //03专家选择
    private LinearLayout linear_select_expert ;
    //02,03,04提交显示按钮
    private TextView text234_submit ;

    //06办事详情
    private TextView text_detail_event ;
    //06提交
    private TextView text_06_submit ;

    private String id ;

    private List<HomeEventEntity> list ;

    private MyListView listView ;
    private TextView data_view ;

    private boolean isSelectExpert = false ;

    private List<EventTypeEntity> listType ;
    private EventTypeAdapter adapterType ;
    private EventTypeAdapter02 adapterType02 ;

    private String expertId ;
    private String type01 ;
    private String type02 ;

    //页面置灰不可点击部分第二阶段到第六阶段
    private LinearLayout linear_black_02 ,linear_black_03 , linear_black_04 ,linear_black_05, linear_black_06 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent() ;
        String type = intent.getStringExtra("type");
        switch (type){
            case "head" :
                frame_view_01.setVisibility(View.VISIBLE);
                frame_view_234.setVisibility(View.GONE);
                frame_view_06.setVisibility(View.GONE);
                linear_black_02.setVisibility(View.VISIBLE);
                linear_black_03.setVisibility(View.VISIBLE);
                linear_black_04.setVisibility(View.VISIBLE);
                linear_black_05.setVisibility(View.VISIBLE);
                linear_black_06.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                //加载选择办事类型数据
                loadSelectEventTypeData() ;
                break;
            default:
                loadData(id) ;
                break;
        }

    }

    private void loadSelectEventTypeData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        httpUtils.send(HttpRequest.HttpMethod.GET, Url.eventType, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("eventType", responseInfo.result);
                JSONObject object = JSON.parseObject(responseInfo.result);
                String status_code = object.getString("return_code");
                switch (status_code) {
                    case "200":
                        JSONArray data = object.getJSONArray("data");
                        listType = JSON.parseArray(data.toJSONString(), EventTypeEntity.class);
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                ToastUtils.shortToast(DetailsEventActivity.this, "网络连接异常");
            }
        });
    }

    private void loadData(String id ) {
        // TODO: 2017/7/24  写完项目需要删除掉
        ToastUtils.shortToast(DetailsEventActivity.this , "应该加载数据了..." + "id是" + id );

        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("eventid" , id );
        String urls = String.format(Url.detailsEvent, GetBenSharedPreferences.getTicket(DetailsEventActivity.this));
        httpUtils.send(HttpRequest.HttpMethod.POST, urls, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("detailsEvent", responseInfo.result);
                JSONObject object = JSON.parseObject(responseInfo.result);
                String status_code = object.getString("return_code");
                String ben = object.getString("ben");
                String count = object.getString("count");
                switch (status_code) {
                    case "200":
                        JSONArray data = object.getJSONArray("data");
                        list = JSON.parseArray(data.toJSONString(), HomeEventEntity.class);
                        switch (list.get(0).getConfigid()){
                            case "1" :
                                show1to3("正在审核" ,ben) ;
                                break;
                            case "2" :
                                show1to3("审核通过" ,ben) ;
                                break;
                            case "3" :
                                show1to3("审核未通过" ,ben) ;
                                break;
                            case "4" :
                                show4("系统已经邀请" + count +"名专家" ,ben  ) ;
                                break;
                            case "5" :
                                show5("托管佣金",ben,count) ;
                                break;
                            case "6" :
                                ToastUtils.shortToast(DetailsEventActivity.this , "正在办事");
                                break;
                            case "7" :
                                show7() ;
                                break;
                            case "8" :
                                ToastUtils.shortToast(DetailsEventActivity.this , "已经评价");
                                break;
                            case "9" :
                                ToastUtils.shortToast(DetailsEventActivity.this , "异常终止");
                                break;
                            default:
                                break;
                        }
                        List<HomeEventEntity> list01 = list.subList(1 , list.size() ) ;
                        if (list01.size() == 0){
                            listView.setVisibility(View.GONE);
                            data_view.setVisibility(View.VISIBLE);
                        }else{
                            Collections.sort(list01) ;
                            DetailsEventRightAdapter adapter = new DetailsEventRightAdapter(DetailsEventActivity.this , list01 ) ;
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        break;
                    default:
                        break;

                }
            }

            private void show7() {
                frame_view_01.setVisibility(View.GONE);
                frame_view_234.setVisibility(View.GONE);
                frame_view_06.setVisibility(View.VISIBLE);
                linear_black_02.setVisibility(View.GONE);
                linear_black_03.setVisibility(View.GONE);
                linear_black_04.setVisibility(View.GONE);
                linear_black_05.setVisibility(View.GONE);
                linear_black_06.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }

            private void show5(String srt, String ben,String count ) {
                frame_view_01.setVisibility(View.GONE);
                frame_view_234.setVisibility(View.VISIBLE);
                frame_view_06.setVisibility(View.GONE);
                linear_black_02.setVisibility(View.GONE);
                linear_black_03.setVisibility(View.GONE);
                linear_black_04.setVisibility(View.GONE);
                linear_black_05.setVisibility(View.VISIBLE);
                linear_black_06.setVisibility(View.VISIBLE);
                text_type01.setText(list.get(0).getDomain1());
                text_type02.setText(list.get(0).getDomain2());
                text_des.setText(list.get(0).getBrief());
                text_expert_type.setText(ben+ "---已有" + count + "专家响应，请选择。");
                text234_submit.setText(srt);
                text_ben.setText("4");
                text_line234.setText("专家响应");
                linear_select_expert.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
            }

            private void show4(String srt, String ben ) {
                frame_view_01.setVisibility(View.GONE);
                frame_view_234.setVisibility(View.VISIBLE);
                frame_view_06.setVisibility(View.GONE);
                linear_black_02.setVisibility(View.GONE);
                linear_black_03.setVisibility(View.GONE);
                linear_black_04.setVisibility(View.VISIBLE);
                linear_black_05.setVisibility(View.VISIBLE);
                linear_black_06.setVisibility(View.VISIBLE);
                text_type01.setText(list.get(0).getDomain1());
                text_type02.setText(list.get(0).getDomain2());
                text_des.setText(list.get(0).getBrief());
                text_expert_type.setText(ben);
                text234_submit.setText(srt);
                text_ben.setText("3");
                text_line234.setText("发出邀请");
                scrollView.setVisibility(View.VISIBLE);
            }

            private void show1to3(String srt , String ben) {
                frame_view_01.setVisibility(View.GONE);
                frame_view_234.setVisibility(View.VISIBLE);
                frame_view_06.setVisibility(View.GONE);
                linear_black_02.setVisibility(View.GONE);
                linear_black_03.setVisibility(View.VISIBLE);
                linear_black_04.setVisibility(View.VISIBLE);
                linear_black_05.setVisibility(View.VISIBLE);
                linear_black_06.setVisibility(View.VISIBLE);
                text_type01.setText(list.get(0).getDomain1());
                text_type02.setText(list.get(0).getDomain2());
                text_des.setText(list.get(0).getBrief());
                text_expert_type.setText(ben);
                text234_submit.setText(srt);
                text_ben.setText("2");
                text_line234.setText("系统资料审核");
                scrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                ToastUtils.shortToast( DetailsEventActivity.this, "网络连接异常");
            }
        });

    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_details_event);
    }

    @Override
    public void initViews() {
        scrollView = (ScrollView)findViewById(R.id.scrollView ) ;
        frame_view_01 = (FrameLayout) findViewById(R.id.frame_view_01 ) ;
        frame_view_234 = (FrameLayout)findViewById(R.id.frame_view_234 ) ;
        frame_view_06 = (FrameLayout) findViewById(R.id.frame_view_06 ) ;
        text_select = (TextView)findViewById(R.id.text_select ) ;
        edit_des = (EditText)findViewById(R.id.edit_des ) ;
        textView_submit = (TextView)findViewById(R.id.textView_submit ) ;
        text_select_expert = (TextView)findViewById(R.id.text_select_expert ) ;
        text_default_expert = (TextView)findViewById(R.id.text_default_expert ) ;
        text_type01 = (TextView)findViewById(R.id.text_type01 ) ;
        text_type02 = (TextView)findViewById(R.id.text_type02 ) ;
        text_des = (TextView)findViewById(R.id.text_des ) ;
        text_expert_type = (TextView)findViewById(R.id.text_expert_type ) ;
        linear_select_expert = (LinearLayout) findViewById(R.id.linear_select_expert ) ;
        text234_submit = (TextView) findViewById(R.id.text234_submit ) ;
        text_detail_event = (TextView) findViewById(R.id.text_detail_event ) ;
        text_06_submit = (TextView) findViewById(R.id.text_06_submit ) ;
        linear_black_02 = (LinearLayout) findViewById(R.id.linear_black_02 ) ;
        linear_black_03 = (LinearLayout) findViewById(R.id.linear_black_03 ) ;
        linear_black_04 = (LinearLayout) findViewById(R.id.linear_black_04 ) ;
        linear_black_05 = (LinearLayout) findViewById(R.id.linear_black_05 ) ;
        linear_black_06 = (LinearLayout) findViewById(R.id.linear_black_06 ) ;
        img_pre = (ImageView)findViewById(R.id.img_pre ) ;
        text_common_title = (TextView)findViewById(R.id.text_common_title) ;
        common_right = (ImageView)findViewById(R.id.common_right ) ;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        text_ben = (TextView) findViewById(R.id.text_ben);
        text_line234 = (TextView) findViewById(R.id.text_line234);

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view)  ;
        View headerView = navigationView.getHeaderView(0);

        listView = (MyListView)headerView.findViewById(R.id.listView ) ;
        data_view = (TextView)headerView.findViewById(R.id.data_view ) ;
        listView.setDivider(null);
        listView.setDividerHeight(0);

    }

    @Override
    public void initListeners() {
        img_pre.setOnClickListener(this);
        common_right.setOnClickListener(this);
        text_select.setOnClickListener(this);
        textView_submit.setOnClickListener(this);
        text_select_expert.setOnClickListener(this);
        text_default_expert.setOnClickListener(this);
        text_type01.setOnClickListener(this);
        text_type02.setOnClickListener(this);
        text234_submit.setOnClickListener(this);
        text_detail_event.setOnClickListener(this);
        text_06_submit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent() ;
        String type = intent.getStringExtra("type");
        switch (type){
            case "head" :
                text_common_title.setText("申请办事服务");
                common_right.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                data_view.setVisibility(View.VISIBLE);
                break;
            default:
                text_common_title.setText("办事进度");
                common_right.setVisibility(View.VISIBLE);
                id = intent.getStringExtra("id") ;
                ToastUtils.shortToast(DetailsEventActivity.this , id );
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(setDrawerGravity())) {
            drawer.closeDrawer(setDrawerGravity());
        } else {
            super.onBackPressed();
        }
    }

    private int setDrawerGravity() {
        return Gravity.RIGHT;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_pre :
                finish();
                break;
            case R.id.common_right :
                drawer.openDrawer(setDrawerGravity());
                break;
            case R.id.text_detail_event:
                ToastUtils.shortToast(DetailsEventActivity.this , "办事详情");
                break;
            case R.id.text_select :
                showSelectType() ;
                break;
            case R.id.textView_submit :
                goSubmitApply() ;
                break;
            case R.id.text_select_expert :
                selectExpert01(text_select_expert) ;
                break;
            case R.id.text_default_expert :
                selectExpert02(text_default_expert) ;
                break;
            default:
                break;
        }
    }

    private void showSelectType() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.select_event_type, null);
        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ListView listView01 = (ListView) view.findViewById(R.id.listView01);
        final ListView listView02 = (ListView) view.findViewById(R.id.listView02);
        adapterType = new EventTypeAdapter(DetailsEventActivity.this , listType ) ;
        listView01.setAdapter(adapterType);
        adapterType.notifyDataSetChanged();
        listView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                adapterType02 = new EventTypeAdapter02( DetailsEventActivity.this , listType , position ) ;
                listView02.setAdapter(adapterType02);
                adapterType02.notifyDataSetChanged();
                listView02.setVisibility(View.VISIBLE);

                listView02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {
                        text_select.setText(listType.get(position).getDomainname() + "-" + listType.get(position).getBenben().get(index).getDomainname());
                        type01 = listType.get(position).getDomainname() ;
                        type02 = listType.get(position).getBenben().get(index).getDomainname() ;
                        window.dismiss();
                    }
                });
            }
        });

        window.setFocusable(true);
        //点击空白的地方关闭PopupWindow
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置popWindow的显示和消失动画
        //window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(listView01, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.4f);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    private void selectExpert02(TextView text_default_expert) {
        if (text_select_expert.isSelected()){
            text_default_expert.setSelected(true);
            text_select_expert.setSelected(false);
            isSelectExpert = true ;
        }else if (text_default_expert.isSelected()){
            text_default_expert.setSelected(false);
            isSelectExpert = false ;
        }else {
            text_default_expert.setSelected(true);
            isSelectExpert = true ;
        }
    }

    private void selectExpert01(TextView text_select_expert) {
        if (text_default_expert.isSelected()){
            text_select_expert.setSelected(true);
            text_default_expert.setSelected(false);
            isSelectExpert = true ;
            goSelectedExpertActivity() ;
        }else if (text_select_expert.isSelected()){
            text_select_expert.setSelected(false);
            isSelectExpert = false ;
        }else {
            text_select_expert.setSelected(true);
            isSelectExpert = true ;
            goSelectedExpertActivity() ;
        }

    }

    private void goSelectedExpertActivity() {
        Intent intent = new Intent(DetailsEventActivity.this , SelectedExpertActivity.class ) ;
        startActivityForResult(intent , 0 );
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                Bundle b = data.getExtras();
                expertId = b.getString("expertid");
                break;
            default:
                ToastUtils.shortToast(DetailsEventActivity.this , "请重新选择专家" );
                text_select_expert.setSelected(false);
                isSelectExpert = false ;
                break;
        }
    }

    private void goSubmitApply() {
        if (isSub()){
            final ProgressDialog dialog = new ProgressDialog(DetailsEventActivity.this) ;
            dialog.setTitle("正在提交");
            dialog.show();
            HttpUtils httpUtils = new HttpUtils();
            RequestParams params = new RequestParams();
            params.addBodyParameter("type01" , type01);
            params.addBodyParameter("type02" , type02);
            params.addBodyParameter("brief" , edit_des.getText().toString().trim() );
            if (text_default_expert.isSelected()){
                params.addBodyParameter("select" , "0");
            }else {
                params.addBodyParameter("select" , "1");
                params.addBodyParameter("expert" , expertId );
            }

            String urls = String.format(Url.eventApply, GetBenSharedPreferences.getTicket(DetailsEventActivity.this));
            httpUtils.send(HttpRequest.HttpMethod.POST, urls, params, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    dialog.dismiss();
                    Log.e("eventApply", responseInfo.result);
                    JSONObject object = JSON.parseObject(responseInfo.result);
                    String status_code = object.getString("return_code");
                    switch (status_code) {
                        case "200":
                            ToastUtils.shortToast(DetailsEventActivity.this , "发布成功");
                            String eventid = object.getString("eventid");
                            loadData(eventid);
                            break;
                        default:
                            break;

                    }
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                    dialog.dismiss();
                    error.printStackTrace();
                    ToastUtils.shortToast(DetailsEventActivity.this, "网络连接异常");
                }
            });
        }
    }

    private boolean isSub() {
        if ("全部".equals(text_select.getText().toString())){
            ToastUtils.shortToast(DetailsEventActivity.this , "请选择问题分类");
            return false ;
        }
        if (TextUtils.isEmpty(edit_des.getText().toString().trim())){
            ToastUtils.shortToast(DetailsEventActivity.this , "请输入问题的描述");
            return false ;
        }
        if (!isSelectExpert){
            ToastUtils.shortToast(DetailsEventActivity.this , "请选择任意专家选择方式");
            return false ;
        }
        return true;
    }

}
