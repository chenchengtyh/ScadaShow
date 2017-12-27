package com.nti56.scadashow.scadashow.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.bean.APaddr;
import com.nti56.scadashow.scadashow.bean.OrderInfo;
import com.nti56.scadashow.scadashow.service.FreshIPService;
import com.nti56.scadashow.scadashow.utils.LinkWebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.imallan.jellyrefresh.JellyRefreshLayout;
import uk.co.imallan.jellyrefresh.PullToRefreshLayout;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by chencheng on 2017/8/11.
 */

public class InspectinspectActivity extends AppCompatActivity {

    private Button ins_btn;
    private TextView ip_info;
    private JellyRefreshLayout mJellyLayout;
    private ListView ip_listview;
    List<String> data = new ArrayList<>();
    List<String> data_submit = new ArrayList<>();
    LinkedList<APaddr> apaddr;
    public Gson gson = new Gson();
    private TextView text_title;
    private Button submits;
    private Button backs;
    private LinkWebService linkWebService;
    private ProgressDialog dialog;
    private int poi = 777;

    FreshIPService.MyBinder binder;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (FreshIPService.MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    ip_info.setText("当前信号:  " + binder.getLevel() + "\n" + "仓库区域:  " + Change(getConnectedWifiMacAddress(InspectinspectActivity.this)) + "\nMAC: " + getConnectedWifiMacAddress(InspectinspectActivity.this));
                    dialog.dismiss();
                    break;
                case 2:
                    ip_listview.setAdapter(new ArrayAdapter<String>(InspectinspectActivity.this, android.R.layout.simple_expandable_list_item_1, data));
                    dialog.dismiss();
                    break;
                case 3:
                    Toast.makeText(InspectinspectActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case 4:
                    Toast.makeText(InspectinspectActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case 5:
                    Toast.makeText(InspectinspectActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    //dialog.dismiss();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspectinspect);

        ip_info = (TextView) findViewById(R.id.ip_info);
        ip_listview = (ListView) findViewById(R.id.ip_listview);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("巡更");
        backs = (Button) findViewById(R.id.backs);
        backs.setVisibility(View.VISIBLE);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submits = (Button) findViewById(R.id.submits);
        submits.setVisibility(View.VISIBLE);
        submits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(InspectinspectActivity.this,"您点击了提交",Toast.LENGTH_SHORT).show();

                SubmitData();
            }
        });
        if (!WifiCheck()) {
            Toast.makeText(InspectinspectActivity.this, "wifi未连接，请确认wifi是否开启!", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, FreshIPService.class);
        bindService(intent, conn, Service.BIND_AUTO_CREATE);//启动后台刷新IP服务

        GetAPaddr(); //获取IP对应区域

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_GRANTED) {
//// 获取wifi连接需要定位权限,没有获取权限
//            ActivityCompat.requestPermissions(this, new String[]{
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_WIFI_STATE,
//            }, 22);
//
//        }

        ins_btn = (Button) findViewById(R.id.ins_btn);
        ins_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(InspectinspectActivity.this,"巡检",Toast.LENGTH_SHORT).show();
                if (!WifiCheck()) {
                    Toast.makeText(InspectinspectActivity.this, "wifi未连接，请确认wifi是否开启!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断是否已巡更
                if (CheckHasIns()) {
                    new AlertDialog.Builder(InspectinspectActivity.this)
                            .setTitle("提醒")
                            .setMessage("该仓库已巡更完成,是否更新巡更时间?")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String time = time();
                                    data.set(poi, "仓库区域:  " + Change(getConnectedWifiMacAddress(InspectinspectActivity.this)) + "\n时   间:  " + time);
                                    data_submit.set(poi, Change(getConnectedWifiMacAddress(InspectinspectActivity.this)) + ";" + time + ",");
                                    poi = 777;
                                    Message msg = new Message();
                                    msg.what = 2;
                                    mHandler.sendMessage(msg);
                                }
                            }).create().show();
                } else {
                    String time = time();
                    data.add("仓库区域:  " + Change(getConnectedWifiMacAddress(InspectinspectActivity.this)) + "\n时   间:  " + time);
                    data_submit.add(Change(getConnectedWifiMacAddress(InspectinspectActivity.this)) + ";" + time + ",");
                    Message msg = new Message();
                    msg.what = 2;
                    mHandler.sendMessage(msg);
                }

            }
        });
        mJellyLayout = (JellyRefreshLayout) findViewById(R.id.jelly_refresh_ins);
        mJellyLayout.setPullToRefreshListener(new PullToRefreshLayout.PullToRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                pullToRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                        mJellyLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        View loadingView = LayoutInflater.from(this).inflate(R.layout.view_loading, null);
        mJellyLayout.setLoadingView(loadingView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    private boolean CheckHasIns() {

        boolean checkhasIns = false;
        if (data_submit.size() == 0) {
            return checkhasIns;
        } else {
            String macaddress = Change(getConnectedWifiMacAddress(InspectinspectActivity.this));
            for (int i = 0; i < data_submit.size(); i++) {
                if (data_submit.get(i).indexOf(macaddress) != -1) {
                    checkhasIns = true;
                    poi = i;
                }
            }
            return checkhasIns;
        }
    }

    //获取AP地址
    private void GetAPaddr() {

        dialog = ProgressDialog.show(InspectinspectActivity.this, null, "正在加载中,请稍等...",
                true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                linkWebService = new LinkWebService();
                SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                // 要访问的网址
                String wsdl = mSharedPreferences.getString("service_url", "");
                // webservice 的功能名称
                String method = "getAP";
                // 设置传入参数
                Map<String, Object> params = new HashMap<>();

                try {
                    String obj = linkWebService.LinkSoapWebservice(wsdl, method, params);
                    if (!obj.equals("")) {
                        obj = obj.replace("{\"test\":", "");
                        obj = obj.substring(0, obj.length() - 1);
                        apaddr = new LinkedList<>();
                        //apaddr = null;
                        java.lang.reflect.Type type = new TypeToken<APaddr>() {
                        }.getType();
                        JSONArray arr = new JSONArray(obj);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject temp = (JSONObject) arr.get(i); // 传入字符串
                            APaddr apaddrInstance = gson
                                    .fromJson(temp.toString(), type);
                            if (!apaddr.contains(apaddrInstance)) {
                                apaddr.addLast(apaddrInstance);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 5;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();

    }

    //提交巡更数据
    private void SubmitData() {

        dialog = ProgressDialog.show(InspectinspectActivity.this, null, "正在提交中,请稍等...",
                true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                linkWebService = new LinkWebService();
                SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                // 要访问的网址
                String wsdl = mSharedPreferences.getString("service_url", "");

                // webservice 的功能名称
                String method = "submitPatrol";

                SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                String user_name = sp.getString("user_name", "");

                if (wsdl.equals("") || user_name.equals("")) {
                    Message msg = new Message();
                    msg.what = 4;
                    mHandler.sendMessage(msg);
                    return;
                }
                String patroladdress = "";
                for (int i = 0; i < data_submit.size(); i++) {
                    patroladdress += data_submit.get(i);
                }
                if (!patroladdress.equals("")) {
                    patroladdress.substring(0, patroladdress.length() - 1);
                } else {
                    Message msg = new Message();
                    msg.what = 4;
                    mHandler.sendMessage(msg);
                    return;
                }
                // 设置传入参数
                Map<String, Object> params = new HashMap<>();
                String jsons = "[{\"patroladdress\":" + patroladdress + ",\"patrolperson\":" + user_name + "}]";
                params.put("qrCode", jsons);

                try {
                    String obj = linkWebService.LinkSoapWebservice(wsdl, method, params);
                    if (obj.equals("888")) {
                        Message msg = new Message();
                        msg.what = 3;
                        mHandler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 4;
                        mHandler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }

            }


        }).start();
    }

    public static String getConnectedWifiMacAddress(Context context) {

        String connectedWifiMacAddress = null;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> wifiList;

        if (wifiManager != null) {
            wifiList = wifiManager.getScanResults();
            WifiInfo info = wifiManager.getConnectionInfo();
            if (wifiList != null && info != null) {
                for (int i = 0; i < wifiList.size(); i++) {
                    ScanResult result = wifiList.get(i);
                    if (info.getBSSID().equals(result.BSSID)) {
                        connectedWifiMacAddress = result.BSSID;
                    }
                }
            }
        }
        if (connectedWifiMacAddress != null) {
            return connectedWifiMacAddress.toUpperCase(Locale.ENGLISH);

        } else {
            return "null";
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 22:
                if (grantResults.length > 0) {
                    //initData();
                    getConnectedWifiMacAddress(InspectinspectActivity.this);
                }
                break;
        }
    }

    //根据IP获取区域位置,如果没有,则显示IP在区域外
    private String Change(String ip) {

        String address = "";

        if (apaddr != null) {
            for (int i = 0; i < apaddr.size(); i++) {
                if (ip.equals(apaddr.get(i).getIPADDRESS())) {
                    address = apaddr.get(i).getADDRESS();
                }
            }
            if (address.equals("")) {
                return "区域外";
            } else {
                return address;
            }

        } else {
            return null;
        }


    }


    //检查Wifi是否开启
    private boolean WifiCheck() {

        //获取wifi服务
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            return false;
        } else {
            return true;
        }
    }

    //获取当前时间
    public String time() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("jochen", "----------onDestroy------------");
        unbindService(conn);
    }
}
