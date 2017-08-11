package com.benben.sw2025.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.sw2025.R;
import com.benben.sw2025.tools.GlideLoader;
import com.benben.sw2025.tools.ToastUtils;
import com.benben.sw2025.views.WheelView;
import com.benben.sw2025.views.XEditText;
import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;

import java.util.List;

public class RegisterExpertActivity extends BenBenActivity implements View.OnClickListener {

    private ImageView img_pre;
    private TextView text_common_title;


    //专家名称
    private XEditText x_edit_enterprise_name;
    //专家类别
    private RelativeLayout relative_02;
    private TextView text02;
    //擅长问题
    private RelativeLayout relative_03;
    private TextView text03;
    //服务地区
    private RelativeLayout relative_04;
    private TextView text04;
    //介绍
    private XEditText x_edit_enterprise_des;
    //上传凭证
    private FrameLayout frame06;
    private ImageView image_show06, image_add06;
    //上传宣传照片
    private FrameLayout frame07;
    private ImageView image_show07, image_add07;
    //底部按钮
    private TextView text_submit;

    //凭证文件地址
    private String path01;
    //宣传照片文件地址
    private String path02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_register_expert);
    }

    @Override
    public void initViews() {
        img_pre = (ImageView) findViewById(R.id.img_pre);
        text_common_title = (TextView) findViewById(R.id.text_common_title);

        x_edit_enterprise_name = (XEditText) findViewById(R.id.x_edit_enterprise_name);
        x_edit_enterprise_des = (XEditText) findViewById(R.id.x_edit_enterprise_des);
        relative_02 = (RelativeLayout) findViewById(R.id.relative_02);
        relative_03 = (RelativeLayout) findViewById(R.id.relative_03);
        relative_04 = (RelativeLayout) findViewById(R.id.relative_04);
        text02 = (TextView) findViewById(R.id.text02);
        text03 = (TextView) findViewById(R.id.text03);
        text04 = (TextView) findViewById(R.id.text04);
        text_submit = (TextView) findViewById(R.id.text_submit);
        frame06 = (FrameLayout) findViewById(R.id.frame06);
        frame07 = (FrameLayout) findViewById(R.id.frame07);
        image_show06 = (ImageView) findViewById(R.id.image_show06);
        image_show07 = (ImageView) findViewById(R.id.image_show07);
        image_add06 = (ImageView) findViewById(R.id.image_add06);
        image_add07 = (ImageView) findViewById(R.id.image_add07);

    }

    @Override
    public void initListeners() {
        img_pre.setOnClickListener(this);
        relative_02.setOnClickListener(this);
        relative_03.setOnClickListener(this);
        relative_04.setOnClickListener(this);
        frame06.setOnClickListener(this);
        frame07.setOnClickListener(this);
        text_submit.setOnClickListener(this);
    }

    @Override
    public void initData() {
        text_common_title.setText("专家认证");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_pre:
                finish();
                break;
            case R.id.relative_02:
                popUpWindow(relative_02);
                break;
            case R.id.relative_03:
                popUpWindow(relative_03);
                break;
            case R.id.relative_04:
                popUpWindow(relative_04);
                break;
            case R.id.frame06:
                selectPic(1);
                break;
            case R.id.frame07:
                selectPic(2);
                break;
            case R.id.text_submit:
                if (isCanSubmit()){
                    ToastUtils.shortToast(RegisterExpertActivity.this , "可以提交");
                }
                break;
            default:
                break;
        }
    }

    private boolean isCanSubmit() {
        if (TextUtils.isEmpty(x_edit_enterprise_name.getText().toString().trim())){
            ToastUtils.shortToast(RegisterExpertActivity.this , "请输入专家名称");
            return false ;
        }
        if (TextUtils.isEmpty(text02.getText().toString().trim())){
            ToastUtils.shortToast(RegisterExpertActivity.this , "请选择专家类别");
            return false ;
        }
        if (TextUtils.isEmpty(text03.getText().toString().trim())){
            ToastUtils.shortToast(RegisterExpertActivity.this , "请选择擅长问题");
            return false ;
        }
        if (TextUtils.isEmpty(text04.getText().toString().trim())){
            ToastUtils.shortToast(RegisterExpertActivity.this , "请选择服务地区");
            return false ;
        }
        if (TextUtils.isEmpty(x_edit_enterprise_des.getText().toString().trim())){
            ToastUtils.shortToast(RegisterExpertActivity.this , "请输入介绍");
            return false ;
        }
        if (TextUtils.isEmpty(path01)){
            ToastUtils.shortToast(RegisterExpertActivity.this , "请上传凭证");
            return false ;
        }
        if (TextUtils.isEmpty(path02)){
            ToastUtils.shortToast(RegisterExpertActivity.this , "请上传宣传照片");
            return false ;
        }
        return true;
    }

    private void selectPic(int a) {
        //单选
        ImageConfig imageConfig = new ImageConfig.Builder(
                new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.titleBlue))
                .titleBgColor(getResources().getColor(R.color.titleBlue))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                .singleSelect()
                .showCamera()
                .requestCode(a)
                .build();
        ImageSelector.open(RegisterExpertActivity.this, imageConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Glide.with(RegisterExpertActivity.this)
                    .load(pathList.get(0))
                    .into(image_show06);
            image_add06.setVisibility(View.GONE);
            path01 = pathList.get(0) ;
        }else if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            Glide.with(RegisterExpertActivity.this)
                    .load(pathList.get(0))
                    .into(image_show07);
            path02 = pathList.get(0) ;
            image_add07.setVisibility(View.GONE);
        }
    }

    private void popUpWindow(final RelativeLayout relative) {

        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.common_window_wheel, null);
        final PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        final WheelView picker = (WheelView) view.findViewById(R.id.wheel);

        switch (relative.getId()) {
            case R.id.relative_02:
                picker.addData("机构1");
                picker.addData("机构2");
                picker.addData("机构3");
                picker.addData("机构4");
                picker.addData("机构5");
                break;
            case R.id.relative_03:
                picker.addData("擅长问题1");
                picker.addData("擅长问题2");
                picker.addData("擅长问题3");
                picker.addData("擅长问题4");
                picker.addData("擅长问题5");
                break;
            case R.id.relative_04:
                picker.addData("全国");
                picker.addData("北京");
                picker.addData("上海");
                picker.addData("天津");
                picker.addData("重庆");
                picker.addData("河北");
                picker.addData("山西");
                picker.addData("内蒙古");
                picker.addData("辽宁");
                picker.addData("吉林");
                picker.addData("黑龙江");
                picker.addData("江苏");
                picker.addData("浙江");
                picker.addData("安徽");
                picker.addData("福建");
                picker.addData("江西");
                picker.addData("山东");
                picker.addData("河南");
                picker.addData("湖北");
                picker.addData("湖南");
                picker.addData("广东");
                picker.addData("广西");
                picker.addData("海南");
                picker.addData("四川");
                picker.addData("贵州");
                picker.addData("云南");
                picker.addData("西藏");
                picker.addData("陕西");
                picker.addData("甘肃");
                picker.addData("青海");
                picker.addData("宁夏");
                picker.addData("新疆");
                picker.addData("台湾");
                picker.addData("香港");
                picker.addData("澳门");
                break;
            default:
                break;
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
                switch (relative.getId()) {
                    case R.id.relative_02:
                        text02.setText(centerItem.toString());
                        break;
                    case R.id.relative_03:
                        text03.setText(centerItem.toString());
                        break;
                    case R.id.relative_04:
                        text04.setText(centerItem.toString());
                        break;
                    default:
                        break;
                }
                window.dismiss();
            }
        });
        picker.setCenterItem(0);
        picker.setTextColor(Color.rgb(106, 201, 210));
        picker.setLineColor(Color.rgb(106, 201, 210));
        window.setFocusable(true);
        //点击空白的地方关闭PopupWindow
        window.setBackgroundDrawable(new BitmapDrawable());
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        // 在底部显示
        window.showAtLocation(relative, Gravity.BOTTOM, 0, 0);
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
}
