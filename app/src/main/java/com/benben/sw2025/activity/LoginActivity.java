package com.benben.sw2025.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.benben.sw2025.R;
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

public class LoginActivity extends BenBenActivity implements View.OnClickListener {
    //用户名
    private XEditText edit_userName ;
    //密码
    private XEditText edit_pwd ;
    //登录按钮
    private Button btn_login ;
    //我要注册
    private TextView text_register ;
    //忘记密码
    private TextView text_findPwd ;
    //进度条
    private ProgressDialog dialog ;
    //sp
    private SharedPreferences loginCode ;
    private SharedPreferences isLogin ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initViews() {
        edit_userName = (XEditText)findViewById(R.id.edit_userName ) ;
        edit_pwd = (XEditText)findViewById(R.id.edit_pwd ) ;
        btn_login = (Button)findViewById(R.id.btn_login ) ;
        text_register = (TextView)findViewById(R.id.text_register ) ;
        text_findPwd = (TextView)findViewById(R.id.text_findPwd ) ;
    }

    @Override
    public void initListeners() {
        btn_login.setOnClickListener(this);
        text_register.setOnClickListener(this);
        text_findPwd.setOnClickListener(this);
    }

    @Override
    public void initData() {
        edit_userName.setSeparator(" ");
        edit_userName.setPattern(new int[]{3, 4, 4});
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.pre :
//                finish();
//                break;
            case R.id.text_findPwd :
                Intent intent01 = new Intent(LoginActivity.this , RegisterActivity.class ) ;
                intent01.putExtra("type" , "2" ) ;
                startActivity(intent01);
                break;
            case R.id.text_register :
                Intent intent = new Intent(LoginActivity.this , RegisterActivity.class ) ;
                intent.putExtra("type" , "1" ) ;
                startActivity(intent);
                break;
            case R.id.btn_login:
                submit() ;
                break;
            default:
                break;
        }
    }

    private void submit() {
        final String replace = edit_userName.getText().toString().replace(" ", "");
        if (judgePwd(replace)){
            //显示dialog
            showDialog() ;
            //进行网络请求
            loadData() ;
        }
    }

    private void loadData() {
        String imei = getPhoneInfo.getIMEI(LoginActivity.this);
        HttpUtils utils = new HttpUtils();
        RequestParams params = new RequestParams();
        //将用户名和密码封装到Post体里面
        params.addQueryStringParameter("phone" , edit_userName.getText().toString().replace(" ","").trim() );
        params.addQueryStringParameter("password" , edit_pwd.getText().toString().trim() );
        params.addQueryStringParameter("imei" ,  imei );
        Log.e("imei" ,imei ) ;
        //发送请求
        utils.send(HttpRequest.HttpMethod.POST, Url.login, params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Log.e("Login", responseInfo.result) ;
                //隐藏dialog
                hiddenDialog() ;
                //处理result
                dealResult(responseInfo.result) ;
            }

            @Override
            public void onFailure(HttpException error, String msg) {
                //隐藏dialog
                hiddenDialog() ;
                //打印失败毁掉的log
                error.printStackTrace();
                //提示用户
                ToastUtils.shortToast(LoginActivity.this , "网络连接异常");
            }
        });
    }

    private void dealResult(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            final String error_code = jsonObject.getString("error_code");
            if ("0".equals(error_code)){
                JSONArray data = jsonObject.getJSONArray("data");
                String return_code = data.getJSONObject(0).getString("return_code");
                switch(return_code){
                    case "200" :
                        //将sp存储
                        initSp(jsonObject) ;
                        //登陆成功，跳转到主页面,并关闭此页面
                        finish();
                        ToastUtils.shortToast(LoginActivity.this  , "登录成功");
                        break;
                    case "401":
                        ToastUtils.shortToast(LoginActivity.this, "参数错误，请重新登录");
                        break;
                    case "402":
                        ToastUtils.shortToast(LoginActivity.this , "账号不存在");
                        break;
                    case "403":
                        ToastUtils.shortToast(LoginActivity.this, "账号被冻结");
                        break;
                    case "404":
                        ToastUtils.shortToast(LoginActivity.this, "用户名或密码错误");
                        break;
                    case "405":
                        ToastUtils.shortToast(LoginActivity.this , "IMEI参数缺失");
                        break;
                }
            }else {
                ToastUtils.shortToast(LoginActivity.this , "服务器异常，请稍后重试。");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initSp(JSONObject jsonObject) throws JSONException {
        JSONArray data = jsonObject.getJSONArray("data");
        String return_data = data.getJSONObject(0).getString("return_data");

        loginCode = getSharedPreferences("loginCode" , MODE_PRIVATE );
        isLogin = getSharedPreferences("isLogin" , MODE_PRIVATE ) ;

        loginCode.edit().putString("loginCode", return_data).commit();
        isLogin.edit().putBoolean("isLogin", true).commit();
    }

    private void hiddenDialog() {
        if (dialog!=null){
            dialog.dismiss();
        }
    }

    private void showDialog() {
        //在开始进行网络连接时显示进度条对话框
        dialog = ProgressDialog.show(this, "提示", "正在登录，请稍后。。。");
        dialog.setCancelable(false);// 不可以用“返回键”取消
        dialog.show();
    }

    private boolean judgePwd(String edit_phoneNumber) {
        if (TextUtils.isEmpty(edit_phoneNumber)){
            ToastUtils.longToast(LoginActivity.this, "请输入手机号");
            return false ;
        }
        if (!edit_phoneNumber.matches("^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$")){
            ToastUtils.longToast(LoginActivity.this , "请输入正确的手机号码");
            return false ;
        }
        if (TextUtils.isEmpty(edit_pwd.getText().toString())){
            ToastUtils.longToast(LoginActivity.this , "请输入密码");
            return false ;
        }
        if (edit_pwd.getText().toString().length() < 6){
            ToastUtils.longToast(LoginActivity.this, "请至少输入6位密码");
            return false ;
        }
        if (edit_pwd.getText().toString().length() > 16){
            ToastUtils.longToast(LoginActivity.this, "至多输入16位密码");
            return false ;
        }
        return true ;
    }
}
