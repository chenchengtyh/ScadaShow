package com.nti56.scadashow.scadashow.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.utils.LinkWebService;
import com.nti56.scadashow.scadashow.utils.LogcatHelper;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by chencheng on 2017/8/15.
 */
public class LoginActivity extends AppCompatActivity {

    private Button login;
    private CheckBox rmb_pwd;
    private EditText login_username_edt;
    private EditText login_password_edt;
    private LinkWebService linkWebService;
    private TextView edit_url;
    private String user_name;
    private String user_pwd;
    private String obj;//返回结果
    private ProgressDialog dialog;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    Toast.makeText(LoginActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case 2:
                    Toast.makeText(LoginActivity.this, obj, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case 3:
                    Toast.makeText(LoginActivity.this, obj, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case 4:
                    if (rmb_pwd.isChecked()) {
                        SharedPreferences mSharedPreferences = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString("name", obj);
                        editor.putString("user_name", user_name);
                        editor.putString("user_pwd", user_pwd);
                        editor.commit();
                    }
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent();
                    it.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(it);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                break;
            case 2:
                //LogcatHelper.getInstance(MainActivity.this);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("jochen", "---Login onCreate()----");

        LogcatHelper.getInstance(this).start();
        linkWebService = new LinkWebService();

        rmb_pwd = (CheckBox) findViewById(R.id.rmb_pwd);
        login_username_edt = (EditText) findViewById(R.id.login_username_edt);
        login_password_edt = (EditText) findViewById(R.id.login_password_edt);
        edit_url = (TextView) findViewById(R.id.edit_url);

        if (rmb_pwd.isChecked()) {

            SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
            String user_name = sp.getString("user_name", "");//如果取不到值就取后面的""
            String user_pwd = sp.getString("user_pwd", "");
            login_username_edt.setText(user_name);
            login_password_edt.setText(user_pwd);
        }

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                initData();//保存地址
                dialog = ProgressDialog.show(LoginActivity.this, null, "正在登录，请稍候...",
                        true, false);
                user_name = login_username_edt.getText().toString().trim();
                user_pwd = login_password_edt.getText().toString().trim();

                if (user_name.equals("") || user_pwd.equals("")) {
                    Toast.makeText(LoginActivity.this, "用户名密码不能为空!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
                            ActivityCompat.requestPermissions(LoginActivity.this,
                                    new String[]{Manifest.permission.INTERNET},
                                    1);
                        } else {
                            // 要访问的网址
                            //String wsdl = "http://192.168.8.99:8067/WebService1.asmx";
                            SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                            // 要访问的网址
                            String wsdl = mSharedPreferences.getString("service_url", "");
                            // webservice 的功能名称
                            String method = "Login";
                            // 设置传入参数
                            Map<String, Object> params = new HashMap<>();
                            String jsons = "[{\"user\":" + user_name + ",\"password\":" + "" + user_pwd + "}]";
                            params.put("qrCode", jsons);
                            try {
                                obj = linkWebService.LinkSoapWebservice(wsdl, method, params);
                                if (obj.equals("")) {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    mHandler.sendMessageDelayed(msg, 3000);
                                    return;
                                }
                                if (obj.indexOf("不存在") >= 0) {
                                    Message msg = new Message();
                                    msg.what = 2;
                                    mHandler.sendMessageDelayed(msg, 3000);

                                } else if (obj.indexOf("密码错误！") >= 0) {
                                    Message msg = new Message();
                                    msg.what = 3;
                                    mHandler.sendMessageDelayed(msg, 3000);
                                } else {
                                    Message msg = new Message();
                                    msg.what = 4;
                                    mHandler.sendMessageDelayed(msg, 3000);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Message msg = new Message();
                                msg.what = 1;
                                mHandler.sendMessageDelayed(msg, 3000);
                                //Toast.makeText(LoginActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }).start();

            }
        });

        edit_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(LoginActivity.this);
                SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                inputServer.setText(mSharedPreferences.getString("service_url", ""));

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("更改服务地址").setIcon(R.drawable.icons_daniel).setView(inputServer)
                        .setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString("service_url", inputServer.getText().toString().trim());
                        editor.commit();
                    }
                });
                builder.show();
            }
        });

    }

    private void initData() {

        //String service_url = "http://192.168.8.99:8067/WebService1.asmx";
        String service_url = "http://192.168.26.2:8088/WebService1.asmx";

        //初始化服务器地址
        SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
        String service_url_save = mSharedPreferences.getString("service_url", "");
        if (service_url_save.equals("")) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString("service_url", service_url);
            editor.commit();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogcatHelper.getInstance(this).stop();
    }
}

