package com.benben.sw2025.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.benben.sw2025.R;
import com.benben.sw2025.fragment.HomeConsultFragment;
import com.benben.sw2025.fragment.HomeEventFragment;
import com.benben.sw2025.fragment.HomeExpertFragment;
import com.benben.sw2025.fragment.HomeMeFragment;
import com.benben.sw2025.fragment.HomeNeedFragment;
import com.benben.sw2025.tools.GetBenSharedPreferences;
import com.benben.sw2025.tools.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeEnterpriseActivity extends BenBenActivity implements View.OnClickListener {

    private HomeEventFragment homeEventFragment ;
    private HomeConsultFragment homeConsultFragment ;
    private HomeNeedFragment homeNeedFragment ;
    private HomeExpertFragment homeExpertFragment ;
    private HomeMeFragment homeMeFragment ;
    private BottomNavigationBar bottomNavigationBar; //底部导航
    private LinearLayout ll_content; //一级Fragment容器
    private ImageView img_change ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionsUtil.checkAndRequestPermissions(this);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initViews() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        img_change = (ImageView) findViewById(R.id.img_change);
    }

    @Override
    public void initListeners() {
        img_change.setOnClickListener(this);
        homeEventFragment = new HomeEventFragment() ;
        homeConsultFragment = new HomeConsultFragment() ;
        homeNeedFragment = new HomeNeedFragment();
        homeExpertFragment = new HomeExpertFragment() ;
        homeMeFragment = new HomeMeFragment() ;

        final List<Fragment> listFragment = new ArrayList<>() ;
        listFragment.add(0 , homeEventFragment );
        listFragment.add(1 , homeConsultFragment );
        listFragment.add(2 , homeNeedFragment );
        listFragment.add(3 , homeExpertFragment );
        listFragment.add(3 , homeMeFragment );
        //底部导航监听
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {

            @Override
            public void onTabSelected(int position) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (position) {
                    case 0:
                        ActiveFragment(listFragment,transaction,homeEventFragment);
                        break;
                    case 1:
                        ActiveFragment(listFragment,transaction,homeConsultFragment);
                        break;
                    case 2:
                        ActiveFragment(listFragment,transaction,homeNeedFragment);
                        break;
                    case 3:
                        ActiveFragment(listFragment,transaction,homeExpertFragment);
                        break;
                    case 4:
                        ActiveFragment(listFragment,transaction,homeMeFragment);
                        break;
                    default:
                        ActiveFragment(listFragment,transaction,homeEventFragment);
                        break;
                }
                transaction.commit();
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
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
//                .addItem(new BottomNavigationItem(R.mipmap.home, R.string.home_home))
//                .addItem(new BottomNavigationItem(R.mipmap.fabu, R.string.home_publish))
//                .addItem(new BottomNavigationItem(R.mipmap.shoucang_54, R.string.home_collect))
//                .addItem(new BottomNavigationItem(R.mipmap.wode, R.string.home_me))
                .addItem(new BottomNavigationItem(R.mipmap.nav_01, R.string.home_event))
                .addItem(new BottomNavigationItem(R.mipmap.nav_02, R.string.home_consult))
                .addItem(new BottomNavigationItem(R.mipmap.nav_03, R.string.home_need))
                .addItem(new BottomNavigationItem(R.mipmap.nav_04, R.string.home_expert))
                .addItem(new BottomNavigationItem(R.mipmap.nav_05, R.string.home_me))
                .initialise();

        //默认选择首页
        SetDefaultFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_change :
                Intent intent = new Intent(HomeEnterpriseActivity.this , HomeExpertActivity.class ) ;
                startActivity(intent);
                overridePendingTransition(R.anim.in , R.anim.out );
                break;
            default:
                break;
        }
    }

    private void SetDefaultFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.ll_content, homeEventFragment);
        transaction.commit() ;
    }

    private void ActiveFragment(List<Fragment> listFragment, FragmentTransaction transaction, Fragment fragment) {
        for (int i = 0; i < listFragment.size(); i++) {
            if (listFragment.get(i).isVisible()){
                if (fragment.isAdded()){
                    transaction.hide(listFragment.get(i)).show(fragment) ;
                }else {
                    transaction.hide(listFragment.get(i)).add(R.id.ll_content , fragment) ;
                }
                break;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!GetBenSharedPreferences.getIsLogin(this)){
            Intent intent = new Intent(this , LoginActivity.class ) ;
            startActivity(intent);
        }
    }
}
