package com.benben.sw2025.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.benben.sw2025.R;

public class HomeExpertActivity extends BenBenActivity implements View.OnClickListener {

    private TextView text_change ;
    private BottomNavigationBar bottomNavigationBar; //底部导航
    private LinearLayout ll_content; //一级Fragment容器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_home_expert);
    }

    @Override
    public void initViews() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        text_change = (TextView)findViewById(R.id.text_change ) ;
    }

    @Override
    public void initListeners() {
        text_change.setOnClickListener(this);
    }

    @Override
    public void initData() {
        //底部导航特性设置
        bottomNavigationBar.setAutoHideEnabled(false);//自动隐藏
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        //底部导航颜色设置
        bottomNavigationBar.setBarBackgroundColor(R.color.bottom_bg); //导航背景颜色
        bottomNavigationBar.setInActiveColor(R.color.bottom_unselected); //未选中时的颜色
        bottomNavigationBar.setActiveColor(R.color.bottom_selected); //选中时的颜色

        //添加底部导航图标和文字
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.nav_01, R.string.home_event))
                .addItem(new BottomNavigationItem(R.mipmap.nav_02, R.string.home_consult))
                .addItem(new BottomNavigationItem(R.mipmap.nav_03, R.string.home_need))
                .addItem(new BottomNavigationItem(R.mipmap.nav_05, R.string.home_me))
                .initialise();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果按下的是返回键，并且没有重复
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            overridePendingTransition(R.anim.two_in, R.anim.two_out);
            return false;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_change:
                finish();
                overridePendingTransition(R.anim.two_in, R.anim.two_out);
                break;
            default:
                break;
        }
    }
}
