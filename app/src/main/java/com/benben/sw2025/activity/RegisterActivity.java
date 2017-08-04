package com.benben.sw2025.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benben.sw2025.R;
import com.benben.sw2025.application.MyApplication;
import com.benben.sw2025.tools.ToastUtils;
import com.benben.sw2025.tools.Url;
import com.benben.sw2025.tools.getPhoneInfo;
import com.benben.sw2025.views.XEditText;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends BenBenActivity implements View.OnClickListener {

    //手机号
    private XEditText edit_userName ;
    //验证码
    private XEditText edit_smsCode ;
    //获取验证码按钮
    private TextView text_getSmsCode ;
    //密码
    private XEditText edit_pwd ;
    //角色显示
    private TextView text_role ;
    //角色下拉按钮
    private ImageView img_pullDown ;
    //角色帮助图片
    private RelativeLayout relative_ask ;
    //登录按钮
    private Button btn_login ;

    private ProgressDialog myProgressDialog ;
    private SharedPreferences loginCode ;
    private SharedPreferences isLogin ;

    private RelativeLayout relative_one ;
    private TextView text_two ;

    private String action;
    //发送验证阿60s后，才可以再次发送。
    private int recLen =60;

    private ProgressDialog dialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initViews() {
        edit_userName = (XEditText)findViewById(R.id.edit_userName ) ;
        edit_smsCode = (XEditText)findViewById(R.id.edit_smsCode ) ;
        text_getSmsCode = (TextView)findViewById(R.id.text_getSmsCode ) ;
        edit_pwd = (XEditText)findViewById(R.id.edit_pwd ) ;
        text_role = (TextView)findViewById(R.id.text_role ) ;
        img_pullDown = (ImageView)findViewById(R.id.img_pullDown ) ;
        relative_ask = (RelativeLayout)findViewById(R.id.relative_ask ) ;
        btn_login = (Button)findViewById(R.id.btn_login ) ;

        relative_one = (RelativeLayout)findViewById(R.id.relative_one ) ;
        text_two = (TextView)findViewById(R.id.text_two  ) ;
    }

    @Override
    public void initListeners() {
        text_getSmsCode.setOnClickListener(this);
        img_pullDown.setOnClickListener(this);
        relative_ask.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void initData() {
        edit_userName.setSeparator(" ");
        edit_userName.setPattern(new int[]{3, 4, 4});
        Intent intent =getIntent() ;
        String type = intent.getStringExtra("type");
        switch (type){
            case "1" :
                action = "register" ;
                btn_login.setText("注册");
                break;
            case "2" :
                action = "forgot" ;
                btn_login.setText("找回密码");
                relative_one.setVisibility(View.GONE);
                text_two.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_getSmsCode :
                //获取验证码
                registerSmsCode() ;
                break;
            case R.id.img_pullDown:
                changeText_role() ;
                break;
            case R.id.relative_ask:
                ToastUtils.shortToast(this , "help!");
                break;
            case R.id.btn_login:
                toRegister() ;
            default:
                break;
        }
    }

    private void toRegister() {
        final String phoneNumber = edit_userName.getText().toString().replace(" ","");
        if (!TextUtils.isEmpty(phoneNumber)){
            final String muserName = phoneNumber ;
            //判断输入的账号是否是一个真实有效的手机号
            if (muserName.matches("^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$")){
                //判断验证码是否为空
                if (!TextUtils.isEmpty(edit_smsCode.getText().toString().replace(" ", ""))){
                    String smsCode = edit_smsCode.getText().toString().replace(" ", "") ;
                    //判断密码是否为空
                    if (!TextUtils.isEmpty(edit_pwd.getText().toString())){
                        String mpwd = edit_pwd.getText().toString();
                        if (mpwd.length() < 6 ){
                            ToastUtils.longToast(RegisterActivity.this, "请至少输入6位密码");
                        }else if (mpwd.length() > 16){
                            ToastUtils.longToast(RegisterActivity.this , "至多输入16位密码");
                        }else {

                                /* 显示ProgressDialog */
                                    //在开始进行网络连接时显示进度条对话框
                                    dialog = ProgressDialog.show(this , "提示" , "数据提交中，请稍后...");
                                    dialog.setCancelable(false);// 不可以用“返回键”取消
                                    dialog.show();
                                    //进行网络请求
                                    HttpUtils utils = new HttpUtils();
                                    RequestParams params = new RequestParams();
                                    //将用户名和密码封装到Post体里面
                                    params.addBodyParameter("phone" , muserName);
                                    params.addBodyParameter("password" , mpwd );
                                    params.addBodyParameter("verifycode" , smsCode );
                                    params.addBodyParameter("role" ,text_role.getText().toString() );
                                    params.addBodyParameter("imei" , getPhoneInfo.getIMEI(RegisterActivity.this)  );
                                    params.addBodyParameter("action" , action );
                                    //发送请求
                                    utils.send( HttpRequest.HttpMethod.POST , Url.register  , params, new RequestCallBack<String>() {
                                        //失败回调
                                        @Override
                                        public void onSuccess(ResponseInfo<String> responseInfo) {
                                            Log.e("register", responseInfo.result) ;
                                            if (dialog!=null){
                                                dialog.dismiss();
                                            }
                                            try {
                                                JSONObject jsonObject = new JSONObject(responseInfo.result);
                                                final String error_code = jsonObject.getString("error_code");
                                                if ("0".equals(error_code)){
                                                    JSONArray data = jsonObject.getJSONArray("data");
                                                    String return_code = data.getJSONObject(0).getString("return_code");
                                                    switch(return_code){
                                                        case "200" :
                                                            final String ticket = data.getJSONObject(0).getString("return_data");
                                                            loginCode = getSharedPreferences("loginCode" , MODE_PRIVATE );
                                                            isLogin = getSharedPreferences("isLogin" , MODE_PRIVATE ) ;
                                                            loginCode.edit().putString("loginCode", ticket).commit();
                                                            isLogin.edit().putBoolean("isLogin", true).commit();
                                                            finish();
                                                            MyApplication.finishSingleActivityByClass(LoginActivity.class ) ;
                                                            if ("register".equals(action)){
                                                                ToastUtils.shortToast(RegisterActivity.this, "注册成功");
                                                            }else {
                                                                ToastUtils.shortToast(RegisterActivity.this, "找回密码成功");
                                                            }
                                                            break;

                                                        case "401":
                                                            ToastUtils.shortToast(RegisterActivity.this , "参数错误");
                                                            break;
                                                        case "402":
                                                            ToastUtils.shortToast(RegisterActivity.this , "验证码错误");
                                                            break;
                                                        case "403":
                                                            ToastUtils.shortToast(RegisterActivity.this, "该账号已注册，请直接登录");
                                                            break;
                                                        case "404":
                                                            ToastUtils.shortToast(RegisterActivity.this, "服务器异常，请稍后重试");
                                                            break;
                                                        case "405":
                                                            ToastUtils.shortToast(RegisterActivity.this, "该账号未注册，请注册账号");
                                                            break;
                                                        case "406":
                                                            ToastUtils.shortToast(RegisterActivity.this, "服务器异常，请稍后重试");
                                                            break;
                                                        case "407":
                                                            ToastUtils.shortToast(RegisterActivity.this , "action参数错误");
                                                            break;
                                                    }
                                                }else {
                                                    ToastUtils.shortToast(RegisterActivity.this , "服务器异常，请稍后重试。");
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        //成功回掉
                                        @Override
                                        public void onFailure(HttpException error, String msg) {
                                            if (dialog!=null){
                                                dialog.dismiss();
                                            }
                                            error.printStackTrace();
                                            ToastUtils.shortToast(RegisterActivity.this , "网络连接异常");
                                        }
                                    });

                        }

                    }else {
                        ToastUtils.longToast(RegisterActivity.this, "请输入密码");
                    }

                }else{
                    ToastUtils.longToast(RegisterActivity.this, "请输入验证码");
                }

            }else {
                ToastUtils.longToast(RegisterActivity.this , "请输入正确的手机号码");
            }
        }else {
            ToastUtils.longToast(RegisterActivity.this, "请输入手机号");
        }
    }

    private void changeText_role() {
        if (("专家").equals(text_role.getText().toString())){
            text_role.setText("企业");
        }else {
            text_role.setText("专家");
        }
    }

    final Handler handler = new Handler(){
        public void handleMessage(Message msg){         // handle message
            switch (msg.what) {
                case 1:
                    recLen--;
                    text_getSmsCode.setText(recLen + " s");
                    if(recLen > 0){
                        Message message = handler.obtainMessage(1);
                        handler.sendMessageDelayed(message, 1000);      // send message
                    }else{
                        text_getSmsCode.setOnClickListener(RegisterActivity.this);
                        text_getSmsCode.setText("获取验证码");
                    }
            }
            super.handleMessage(msg);
        }
    };

    private void registerSmsCode() {
        final String phoneNumber = edit_userName.getText().toString().replace(" ","");
        if (!TextUtils.isEmpty(phoneNumber)){
            String muserName = phoneNumber ;
            //判断输入的账号是否是一个真实有效的手机号
            if (muserName.matches("^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$")){

                /* 显示ProgressDialog */
                //在开始进行网络连接时显示进度条对话框
                myProgressDialog = ProgressDialog.show(this, "提示", "获取短信验证码，请稍后...");
                myProgressDialog.setCancelable(false);// 不可以用“返回键”取消
                myProgressDialog.show();

                //请求smsCode
                HttpUtils httpUtils = new HttpUtils() ;
                httpUtils.configCurrentHttpCacheExpiry(1000) ;
                RequestParams params = new RequestParams() ;
                params.addQueryStringParameter("phone", muserName);
                params.addQueryStringParameter("action", action );

                httpUtils.send(HttpRequest.HttpMethod.POST, Url.getSmsCode, params , new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        myProgressDialog.dismiss();
                        Log.e("getSmsCode", responseInfo.result);
                        try {
                            JSONObject jsonObject = new JSONObject(responseInfo.result);
                            final String status_code = jsonObject.getString("return_code");
                            switch (status_code) {
                                case "200":
                                    ToastUtils.longToast(RegisterActivity.this, "验证码发送成功");
                                    Message message = handler.obtainMessage(1);     // Message
                                    handler.sendMessageDelayed(message, 1000);
                                    text_getSmsCode.setOnClickListener(null);
                                    break;
                                case "401":
                                    ToastUtils.longToast(RegisterActivity.this, "参数不正确");
                                    break;
                                case "404":
                                    ToastUtils.longToast(RegisterActivity.this, "该账号被冻结");
                                    break;
                                case "405":
                                    ToastUtils.longToast(RegisterActivity.this, "该账户已注册，请直接登录");
                                    break;
                                case "406":
                                    ToastUtils.longToast(RegisterActivity.this, "该账户未注册，请注册");
                                    break;
                                case "503":
                                    ToastUtils.longToast(RegisterActivity.this, "服务器错误，验证码发送失败");
                                    break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        myProgressDialog.dismiss();
                        error.printStackTrace();
                        ToastUtils.longToast(RegisterActivity.this, "网络连接异常");
                    }
                }) ;

            }else {
                ToastUtils.longToast(RegisterActivity.this , "请输入正确的手机号码");
            }
        }else {
            ToastUtils.longToast(RegisterActivity.this, "请输入手机号");
        }
    }


}
