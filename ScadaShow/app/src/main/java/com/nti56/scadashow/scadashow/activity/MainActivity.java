package com.nti56.scadashow.scadashow.activity;


import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.bean.Alarm;
import com.nti56.scadashow.scadashow.interfaces.FragmentCallBack;
import com.nti56.scadashow.scadashow.interfaces.ScadaApplication;
import com.nti56.scadashow.scadashow.service.FreshAlarmData;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements FragmentCallBack {

    private TextView messageView;
    private TextView title;
    private Button bt1;
    private Button bt2;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private BottomBar bottomBar;
    private ImageView zxing;
    private ScadaApplication sapp;

    private FreshAlarmData.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (FreshAlarmData.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);

        } else {
            //LogcatHelper.getInstance(this).start();
        }

        messageView = (TextView) findViewById(R.id.messageView);
        title = (TextView) findViewById(R.id.text_title);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        zxing = (ImageView) findViewById(R.id.zxing);
        zxing.setVisibility(View.VISIBLE);
        zxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                } else {
                    Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                    startActivityForResult(openCameraIntent, 0);
                }

            }
        });
        fm = getSupportFragmentManager();
        sapp = (ScadaApplication) getApplication();
        //LinkedList<Alarm> alarmLinkedList = sapp.getData();
        //Toast.makeText(MainActivity.this,"数据长度="+alarmLinkedList.get(0).GetWAREHOUSENAME(),Toast.LENGTH_SHORT).show();

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                //messageView.setText(TabMessage.get(tabId, false));
                //Toast.makeText(getApplicationContext(),""+tabId,Toast.LENGTH_SHORT).show();
                switch (tabId) {
                    case R.id.tab_favorites:
                        title.setText("资产");
                        ft = fm.beginTransaction();
                        HomeFragment hf = new HomeFragment();
                        Bundle hfb = new Bundle();
                        hfb.putString("PageData", "HomeFragment");
                        hf.setArguments(hfb);
                        ft.replace(R.id.frame_container, hf, "HomeFragment");
                        ft.commit();
                        break;
                    case R.id.tab_nearby:
                        BottomBarTab nearby = bottomBar.getTabWithId(R.id.tab_nearby);
                        nearby.setBadgeCount(0);
                        title.setText("报警");
                        ft = fm.beginTransaction();
                        AlarmFragment af = new AlarmFragment();
                        Bundle afb = new Bundle();
                        afb.putString("PageData", "AlarmFragment");
                        af.setArguments(afb);
                        ft.replace(R.id.frame_container, af, "AlarmFragment");
                        ft.commit();
                        break;
                    case R.id.tab_friends:
                        BottomBarTab order = bottomBar.getTabWithId(R.id.tab_friends);
                        order.setBadgeCount(0);
                        title.setText("工单");
                        ft = fm.beginTransaction();
                        OrderFragment of = new OrderFragment();
                        Bundle ofb = new Bundle();
                        ofb.putString("PageData", "OrderFragment");
                        of.setArguments(ofb);
                        ft.replace(R.id.frame_container, of, "OrderFragment");
                        ft.commit();
                        break;
                    case R.id.tab_user:
                        BottomBarTab ufbb = bottomBar.getTabWithId(R.id.tab_user);
                        ufbb.setBadgeCount(0);
                        title.setText("用户");
                        ft = fm.beginTransaction();
                        UserFragment uf = new UserFragment();
                        Bundle ufb = new Bundle();
                        ufb.putString("PageData", "UserFragment");
                        uf.setArguments(ufb);
                        ft.replace(R.id.frame_container, uf, "UserFragment");
                        ft.commit();
                        break;
                    default:
                        break;
                }

            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                //Toast.makeText(getApplicationContext(), TabMessage.get(tabId, true), Toast.LENGTH_LONG).show();
            }
        });

        //启动后台刷新数据服务
        Intent intent = new Intent();
        intent.setClass(this, FreshAlarmData.class);
        bindService(intent, conn, Service.BIND_AUTO_CREATE);

        SharedPreferences sp = getSharedPreferences("FreshAlarmData", Context.MODE_PRIVATE);
        String save_data = sp.getString("fresh_alar_mdata", "");
        String save_order = sp.getString("fresh_order_data", "");
        if (!save_data.equals("")) {

            try {
                JSONArray arr = new JSONArray(save_data);
                BottomBarTab nearby = bottomBar.getTabWithId(R.id.tab_nearby);
                nearby.setBadgeCount(arr.length());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if (!save_order.equals("")) {

            try {
                JSONArray arr = new JSONArray(save_order);
                BottomBarTab order = bottomBar.getTabWithId(R.id.tab_friends);
                order.setBadgeCount(arr.length());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

//        BottomBarTab favorites = bottomBar.getTabWithId(R.id.tab_favorites);
//        BottomBarTab friends = bottomBar.getTabWithId(R.id.tab_friends);

//        favorites.setBadgeCount(10);
//        friends.setBadgeCount(7);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                Intent openCameraIntent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            case 2:
                //LogcatHelper.getInstance(MainActivity.this);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(MainActivity.this, "扫码功能敬请期待.内容为:" + scanResult, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);//关闭服务
        //LogcatHelper.getInstance(this).stop();//关闭打印
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("SCADA数据监控平台")
                    .setIcon(R.drawable.icons_daniel)
                    .setCancelable(false)
                    .setMessage("确定退出吗？")
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int arg1) {
                                    // TODO Auto-generated method stub
                                    finish();
                                }

                            }).setNegativeButton("取消", null);
            builder.show();

            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    @Override
    public void callbackFunc(Bundle bd) {

        String tabs = bd.getString("tabs");
        int counts = bd.getInt("counts");
        switch (tabs) {
            case "HomeFragment":
                BottomBarTab af = bottomBar.getTabWithId(R.id.tab_nearby);
                af.setBadgeCount(counts);
                break;
            case "AlarmFragment":
                BottomBarTab uf = bottomBar.getTabWithId(R.id.tab_user);
                uf.setBadgeCount(counts);
                break;
            case "OrderFragment":
                BottomBarTab of = bottomBar.getTabWithId(R.id.tab_friends);
                of.setBadgeCount(counts);
                break;
            case "UserFragment":
                BottomBarTab hf = bottomBar.getTabWithId(R.id.tab_favorites);
                hf.setBadgeCount(counts);
                break;
            default:
                break;
        }
    }
}
