package com.benben.sw2025.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ashokvarma.bottomnavigation.BadgeItem;
import com.benben.sw2025.R;
import com.benben.sw2025.activity.DetailsEventActivity;
import com.benben.sw2025.adapter.HomeEventAdapter;
import com.benben.sw2025.entity.HomeEventEntity;
import com.benben.sw2025.tools.GetBenSharedPreferences;
import com.benben.sw2025.tools.ToastUtils;
import com.benben.sw2025.tools.Url;
import com.benben.sw2025.views.WheelView;
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

public class HomeEventFragment extends Fragment implements View.OnClickListener {

    private ListView listView;
    private HomeEventAdapter adapter;
    //申请办事服务按钮
    private TextView applyEvent;
    private LinearLayout headLinearLayout ;
    private RelativeLayout relative_show ;
    //办事数量
    private TextView text_count ;
    //时间排序
    private TextView text_time_sort ;
    //进行阶段
    private TextView text_select ;
    //进行阶段
    private String step = "";
    //时间排序
    private String order = "desc";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_event, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //实例化组件
        initView(view);
        //注册监听事件
        initListeners();
        //加载数据
        loadData();
    }

    private void loadData() {
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("step" , step );
        params.addQueryStringParameter("order" , order );
        String urls = String.format(Url.myEvent, GetBenSharedPreferences.getTicket(getActivity()));
        httpUtils.send(HttpRequest.HttpMethod.POST, urls, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("myEvent", responseInfo.result);
                JSONObject object = JSON.parseObject(responseInfo.result);
                String status_code = object.getString("return_code");
                switch (status_code) {
                    case "200":
                        JSONArray data = object.getJSONArray("data");
                        List<HomeEventEntity> list = JSON.parseArray(data.toJSONString(), HomeEventEntity.class);
                        adapter = new HomeEventAdapter(getActivity(), list);
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        //正确显示办事数量
                        text_count.setText("数量: " + list.size());

                        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                            private SparseArray recordSp = new SparseArray(0);
                            private int mCurrentfirstVisibleItem = 0;

                            @Override
                            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                            }

                            @Override
                            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                mCurrentfirstVisibleItem = firstVisibleItem;
                                View firstView = view.getChildAt(0);
                                if (null != firstView) {
                                    ItemRecod itemRecord = (ItemRecod) recordSp.get(firstVisibleItem);
                                    if (null == itemRecord) {
                                        itemRecord = new ItemRecod();
                                    }
                                    itemRecord.height = firstView.getHeight();
                                    itemRecord.top = firstView.getTop();
                                    recordSp.append(firstVisibleItem, itemRecord);
                                    int h = getScrollY();//滚动距离
                                    if (h <= headLinearLayout.getHeight()){
                                        relative_show.setVisibility(View.GONE);
                                    }else {
                                        relative_show.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            private int getScrollY() {
                                int height = 0;
                                for (int i = 0; i < mCurrentfirstVisibleItem; i++) {
                                    ItemRecod itemRecod = (ItemRecod) recordSp.get(i);
                                    height += itemRecod.height;
                                }
                                ItemRecod itemRecod = (ItemRecod) recordSp.get(mCurrentfirstVisibleItem);
                                if (null == itemRecod) {
                                    itemRecod = new ItemRecod();
                                }
                                return height - itemRecod.top;
                            }


                            class ItemRecod {
                                int height = 0;
                                int top = 0;
                            }
                        });
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                error.printStackTrace();
                ToastUtils.shortToast(getActivity(), "网络连接异常");
            }
        });
    }

    private void initListeners() {
        applyEvent.setOnClickListener(this);
        text_time_sort.setOnClickListener(this);
        text_select.setOnClickListener(this);
    }

    private void initView(View v) {
        headLinearLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.home_event_head_01, null);
        applyEvent = (TextView) headLinearLayout.findViewById(R.id.applyEvent);
        listView = (ListView) v.findViewById(R.id.listView);
        listView.setDivider(new ColorDrawable(Color.WHITE));
        listView.setDividerHeight(30);
        listView.addHeaderView(headLinearLayout);
        relative_show = (RelativeLayout)v.findViewById(R.id.relative_show ) ;

        text_count = (TextView)v.findViewById(R.id.text_count ) ;
        text_time_sort = (TextView)v.findViewById(R.id.text_time_sort ) ;
        text_select = (TextView)v.findViewById(R.id.text_select ) ;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.applyEvent:
                Intent intent = new Intent(getActivity() , DetailsEventActivity.class ) ;
                intent.putExtra("id" , "0" ) ;
                intent.putExtra("type" , "head" ) ;
                startActivity(intent);
                break;
            case R.id.text_time_sort :
                popUpWindow(text_time_sort) ;
                break;
            case R.id.text_select:
                popUpWindow(text_select) ;
                break;
            default:
                break;
        }
    }

    /**
     * 方法名：popUpWindow(List<SystemEntity> list) 弹出系统选择组件
     * 功能：  弹出系统选择组件
     * 参数：  List<SystemEntity>
     * 返回值：无
     */
    private void popUpWindow(final TextView textView) {

        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.common_window_wheel, null);
        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final WheelView picker = (WheelView) view.findViewById(R.id.wheel);

        if (textView == text_select){
            picker.addData("全部");
            picker.addData("待审核");
            picker.addData("审核通过");
            picker.addData("审核未过");
            picker.addData("已推送");
            picker.addData("已响应");
            picker.addData("正在办事");
            picker.addData("已完成");
            picker.addData("已评价");
            picker.addData("异常终止");
        }else {
            picker.addData("时间正序");
            picker.addData("时间倒叙");

        }
        TextView left = (TextView) view.findViewById(R.id.left);
        TextView right = (TextView) view.findViewById(R.id.right);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object centerItem = picker.getCenterItem();
                    switch (centerItem.toString()){
                        case "全部" :
                            step = "" ;
                            break;
                        case "待审核" :
                            step = "1" ;
                            break;
                        case "审核通过" :
                            step = "2" ;
                            break;
                        case "审核未过" :
                            step = "3" ;
                            break;
                        case "已推送" :
                            step = "4" ;
                            break;
                        case "已响应" :
                            step = "5" ;
                            break;
                        case "正在办事" :
                            step = "6" ;
                            break;
                        case "已完成" :
                            step = "7" ;
                            break;
                        case "已评价" :
                            step = "8" ;
                            break;
                        case "异常终止" :
                            step = "9" ;
                            break;
                        case "时间正序":
                            order = "asc" ;
                            break;
                        case "时间倒叙" :
                            order = "desc" ;
                            break;
                    }

                textView.setText(centerItem.toString());
                loadData();
                window.dismiss();
            }
        });

        //public void setLineColor(int lineColor);            //设置中间线条的颜色
        //public void setTextColor(int textColor);            //设置文字的颜色
        //public void setTextSize(float textSize);            //设置文字大小
        picker.setCenterItem(0);
        picker.setTextColor(Color.rgb(106, 201, 210));
        picker.setLineColor(Color.rgb(106, 201, 210));
        window.setFocusable(true);
        //点击空白的地方关闭PopupWindow
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(textView, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.4f);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 方法名：backgroundAlpha(float bgAlpha) 设置添加屏幕的背景透明度
     * 功能：  设置添加屏幕的背景透明度
     * 参数：  float
     * 返回值：无
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }


}
