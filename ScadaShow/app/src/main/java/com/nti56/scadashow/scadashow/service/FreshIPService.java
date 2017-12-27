package com.nti56.scadashow.scadashow.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by chencheng on 2017/8/14.
 */

public class FreshIPService extends Service {

    private String IP = "";
    private int level;
    private String level_str = "强";
    private boolean quit;

    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder {

        public String getIP() {
            return IP;
        }

        public String getLevel() {
            return level_str;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("jochen", "-------FreshIPService.onCreate---------");

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!quit) {//循环获取IP服务地址,每隔一秒获取一次

                    //获取wifi服务
                    WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                    DhcpInfo info = wifiManager.getDhcpInfo();
                    String serverAddress = intToIp(info.serverAddress);
                    Log.d("jochen", "主机IP:" + serverAddress);
                    IP = serverAddress;
                    //判断wifi是否开启
                    if (!wifiManager.isWifiEnabled()) {
                        //wifiManager.setWifiEnabled(true);
                    }
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    int ipAddress = wifiInfo.getIpAddress();
                    //IP = intToIp(ipAddress);
                    Log.d("jochen", "本机IP:" + intToIp(ipAddress));
                    level = wifiInfo.getRssi();
                    //根据获得的信号强度发送信息
                    if (level <= 0 && level >= -50) {
                        level_str = "强";
                    } else if (level < -50 && level >= -70) {
                        level_str = "较好";
                    } else if (level < -70 && level >= -80) {
                        level_str = "一般";
                    } else if (level < -80 && level >= -100) {
                        level_str = "较差";
                    } else {
                        level_str = "无信号";
                    }


                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }
            }
        }).start();

    }

    private String intToIp(int i) {

        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
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
