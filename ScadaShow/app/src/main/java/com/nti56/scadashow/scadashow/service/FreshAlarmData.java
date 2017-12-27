package com.nti56.scadashow.scadashow.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.activity.LoginActivity;
import com.nti56.scadashow.scadashow.activity.MainActivity;
import com.nti56.scadashow.scadashow.bean.OrderInfo;
import com.nti56.scadashow.scadashow.utils.LinkWebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by chencheng on 2017/8/23.
 */

public class FreshAlarmData extends Service {

    LinkWebService linkWebService;

    private boolean quit = false;

    private boolean firstfresh = false;//第一次进入程序刷新数据标志，只用一次

    private FreshAlarmData.MyBinder binder = new FreshAlarmData.MyBinder();

    public class MyBinder extends Binder {

        public String getData() {
            SharedPreferences sp = getSharedPreferences("FreshAlarmData", Context.MODE_PRIVATE);
            String save_data = sp.getString("fresh_alar_mdata", "");
            return save_data;
        }

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);//将要跳转的界面
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                    mBuilder.setContentTitle("SCADA数据监控")//设置通知栏标题
                            .setContentText("您有新状态,请及时刷新") //设置通知栏显示内容
                            .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//	.setNumber(number) //设置通知集合的数量
                            .setTicker("SCADA数据监控") //通知首次出现在通知栏，带上升动画效果的
                            .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                            .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
//	.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                            .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                            .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                            //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                            .setSmallIcon(R.drawable.icons_daniel);//设置通知小ICON
                    //利用PendingIntent来包装我们的intent对象,使其延迟跳转
                    PendingIntent intentPend = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    mBuilder.setContentIntent(intentPend);
                    NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                    manager.notify(0, mBuilder.build());
                    SharedPreferences msp = getSharedPreferences("FreshAlarmData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = msp.edit();
                    editor.putString("fresh_alar_mdata", (String) msg.obj);
                    editor.putInt("fresh_alar_flag", 1);
                    editor.commit();
                    break;
                case 2:
                    break;
                default:
                    break;
            }

        }
    };

    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        linkWebService = new LinkWebService();
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!quit) {
                    WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    WifiInfo info = wifi.getConnectionInfo();
                    String mac = info.getMacAddress();
                    String appflag = "1";
                    SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                    // 要访问的网址
                    String wsdl = mSharedPreferences.getString("service_url", "");
                    // webservice 的功能名称
                    String method = "GetAlarm";
                    // 设置传入参数
                    Map<String, Object> params = new HashMap<>();
                    String jsons = "[{\"mac\":" + mac + ",\"appflag\":" + appflag + ",\"ID\":}]";
                    params.put("qrCode", jsons);
                    try {
                        String obj = linkWebService.LinkSoapWebservice(wsdl, method, params);
                        if (!obj.equals("")) {
                            obj = obj.replace("{\"test\":", "");
                            obj = obj.substring(0, obj.length() - 1);
                            SharedPreferences sp = getSharedPreferences("FreshAlarmData", Context.MODE_PRIVATE);
                            String save_data = sp.getString("fresh_alar_mdata", "");
                            if (!firstfresh) {
                                SharedPreferences msp = getSharedPreferences("FreshAlarmData", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = msp.edit();
                                editor.putString("fresh_alar_mdata", obj);
                                editor.commit();
                                firstfresh = true;

                            } else {
                                if (!obj.equals(save_data)) {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    msg.obj = obj;
                                    mHandler.sendMessage(msg);
                                } else {

                                }
                            }

                        }

                        GetDataOrder();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void GetDataOrder() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                // 要访问的网址
                String wsdl = mSharedPreferences.getString("service_url", "");
                // webservice 的功能名称
                String method = "getOrder";
                // 设置传入参数
                Map<String, Object> params = new HashMap<>();

                try {
                    String obj = linkWebService.LinkSoapWebservice(wsdl, method, params);

                    if (!obj.equals("")) {
                        obj = obj.replace("{\"test\":", "");
                        obj = obj.substring(0, obj.length() - 1);
                        SharedPreferences msp = getSharedPreferences("FreshAlarmData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = msp.edit();
                        editor.putString("fresh_order_data", obj);
                        editor.commit();
                    }

                } catch (Exception e) {

                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit = true;
    }
}
