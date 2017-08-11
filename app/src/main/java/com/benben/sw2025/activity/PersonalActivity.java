package com.benben.sw2025.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.sw2025.R;
import com.benben.sw2025.tools.GlideLoader;
import com.benben.sw2025.views.MyIconImageView;
import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends BenBenActivity implements View.OnClickListener {

    private ImageView img_pre ;
    private TextView text_common_title ;

    private ArrayList<String> pathList = new ArrayList<String>() ;

    private RelativeLayout relative_icon ;
    private MyIconImageView image_icon ;
    private TextView text_nickName ;
    private TextView text_phone ;

    private String name , phone , avatar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_personal);
    }

    @Override
    public void initViews() {
        img_pre = (ImageView)findViewById(R.id.img_pre ) ;
        text_common_title = (TextView)findViewById(R.id.text_common_title ) ;
        image_icon = (MyIconImageView) findViewById(R.id.image_icon ) ;
        relative_icon = (RelativeLayout) findViewById(R.id.relative_icon ) ;
        text_nickName = (TextView)findViewById(R.id.text_nickName ) ;
        text_phone = (TextView)findViewById(R.id.text_phone ) ;
    }

    @Override
    public void initListeners() {
        img_pre.setOnClickListener(this);
        relative_icon.setOnClickListener(this);
    }

    @Override
    public void initData() {
        text_common_title.setText("个人信息");
        Intent intent = getIntent() ;
        name = intent.getStringExtra("name");
        avatar = intent.getStringExtra("avatar");
        phone = intent.getStringExtra("phone");
        if (!TextUtils.isEmpty(name)){
            text_nickName.setText(name);
        }else {
            text_nickName.setText("未设置");
            text_nickName.setTextColor(Color.rgb(28,124,255));
        }
        text_phone.setText(phone);
        Glide.with(PersonalActivity.this)
                //.load(Url.FileIP + avatar)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502352548081&di=4f8bd5b819d36a1d531812cb05198965&imgtype=0&src=http%3A%2F%2Fzhidao.yxlady.com%2FUploads%2Fproblem_caina%2F14477431636887.jpg")
                .into(image_icon);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_pre :
                finish();
                break;
            case R.id.relative_icon :
//                //单选
//                ImageConfig imageConfig = new ImageConfig.Builder(
//                        new GlideLoader())
//                        .steepToolBarColor(getResources().getColor(R.color.titleBlue))
//                        .titleBgColor(getResources().getColor(R.color.titleBlue))
//                        .titleSubmitTextColor(getResources().getColor(R.color.white))
//                        .titleTextColor(getResources().getColor(R.color.white))
//                        // 开启单选   （默认为多选）  (单选 为 singleSelect)
//                        .singleSelect()
//                        //.crop()
//                        // 开启拍照功能 （默认开启）
//                        //.showCamera()
//                        .requestCode(200)
//                        .build();
//                ImageSelector.open(PersonalActivity.this, imageConfig);
                pathList.clear();
                ImageConfig imageConfig = new ImageConfig.Builder(
                        // GlideLoader 可用自己用的缓存库
                        new GlideLoader())
                        // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
                        .steepToolBarColor(getResources().getColor(R.color.titleBlue))
                        // 标题的背景颜色 （默认黑色）
                        .titleBgColor(getResources().getColor(R.color.titleBlue))
                        // 提交按钮字体的颜色  （默认白色）
                        .titleSubmitTextColor(getResources().getColor(R.color.white))
                        // 标题颜色 （默认白色）
                        .titleTextColor(getResources().getColor(R.color.white))
                        // 开启多选   （默认为多选）  (单选 为 singleSelect)
                        .singleSelect()
                        //裁剪
                        //.crop()
                        // 多选时的最大数量   （默认 9 张）
                        .mutiSelectMaxSize(1)
                        // 已选择的图片路径
                        .pathList(pathList)
                        // 拍照后存放的图片路径（默认 /temp/picture）
                        .filePath("/temp")
                        // 开启拍照功能 （默认开启）
                        .showCamera()
                        .requestCode(200)
                        .build();
                ImageSelector.open(PersonalActivity.this, imageConfig);
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Glide.with(PersonalActivity.this)
                    .load(pathList.get(0))
                    .into(image_icon);
        }
    }



}
