package com.nti56.scadashow.scadashow.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.adapter.AlarmFragmentAdapter;
import com.nti56.scadashow.scadashow.bean.Alarm;
import com.nti56.scadashow.scadashow.interfaces.FragmentCallBack;
import com.nti56.scadashow.scadashow.interfaces.ScadaApplication;
import com.nti56.scadashow.scadashow.utils.LinkWebService;
import com.roughike.bottombar.BottomBarTab;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import uk.co.imallan.jellyrefresh.JellyRefreshLayout;
import uk.co.imallan.jellyrefresh.PullToRefreshLayout;

import static com.nti56.scadashow.scadashow.R.id.rmb_pwd;


/**
 * Created by chencheng on 2017/7/27.
 */
public class AlarmFragment extends Fragment {

    private Button bt1;

    private String data;
    public Gson gson = new Gson();
    private ListView listview;
    private JellyRefreshLayout mJellyLayout;
    FragmentCallBack fragmentCallBack = null;
    ScadaApplication sapps;
    LinkWebService linkWebService;
    private LinkedList<Alarm> alarmLinkedList;
    private ProgressDialog dialog;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    SharedPreferences msp = getActivity().getSharedPreferences("FreshAlarmData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = msp.edit();
                    editor.putString("fresh_alar_mdata", (String) msg.obj);
                    editor.commit();
                    dialog.dismiss();
                    AlarmFragmentAdapter alarmFragmentAdapter = new AlarmFragmentAdapter(getActivity(), alarmLinkedList);
                    listview.setAdapter(alarmFragmentAdapter);
                    break;
                case 2:
                    Toast.makeText(getActivity(), "刷新无数据", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case 3:
                    Toast.makeText(getActivity(), "网络异常,请稍后重试", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        data = getArguments().getString("PageData");
        fragmentCallBack = (FragmentCallBack) getActivity();
        //sapps = (ScadaApplication) getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container,
                false);
        bt1 = (Button) view.findViewById(R.id.button2);

        listview = (ListView) view.findViewById(R.id.listview);

        linkWebService = new LinkWebService();
//        dialog = ProgressDialog.show(getActivity(), null, "正在刷新，请稍候...",
//                true, false);
        GetDataFromService();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Alarm alarm = alarmLinkedList.get(position);
                Intent intent = new Intent();
                intent.setClass(getActivity(),
                        AlarmDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("DetailData", alarm);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });

        mJellyLayout = (JellyRefreshLayout) view.findViewById(R.id.jelly_refresh);
        mJellyLayout.setPullToRefreshListener(new PullToRefreshLayout.PullToRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                pullToRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Bundle bd = new Bundle();
                        //bd.putString("tabs", "AlarmFragment");
                        //bd.putInt("counts", 5);
                        //fragmentCallBack.callbackFunc(bd);
                        GetDataFromService();
                        mJellyLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        View loadingView = LayoutInflater.from(getActivity()).inflate(R.layout.view_loading, null);
        mJellyLayout.setLoadingView(loadingView);
        return view;
    }

    private void GetDataFromService() {

        dialog = ProgressDialog.show(getActivity(), null, "正在刷新，请稍候...",
                true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                WifiInfo info = wifi.getConnectionInfo();
                String mac = info.getMacAddress();
                String appflag = "1";
                SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
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
                        alarmLinkedList = new LinkedList<>();
                        java.lang.reflect.Type type = new TypeToken<Alarm>() {
                        }.getType();
                        JSONArray arr = new JSONArray(obj);
                        Log.d("jochen", "alarmFragment=" + arr.length());
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject temp = (JSONObject) arr.get(i); // 传入字符串
                            Alarm alarmInstance = gson
                                    .fromJson(temp.toString(), type);
                            if (!alarmLinkedList.contains(alarmInstance)) {
                                alarmLinkedList.addLast(alarmInstance);
                            }
                        }
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = obj;
                        mHandler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 3;
                    mHandler.sendMessage(msg);
                }
            }
        }).start();

    }

}
