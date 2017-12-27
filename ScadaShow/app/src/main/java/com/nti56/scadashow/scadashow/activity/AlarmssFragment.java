package com.nti56.scadashow.scadashow.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.bean.AlarmsStatic;
import com.nti56.scadashow.scadashow.utils.LinkWebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import uk.co.imallan.jellyrefresh.JellyRefreshLayout;
import uk.co.imallan.jellyrefresh.PullToRefreshLayout;

/**
 * Created by chencheng on 2017/8/9.
 */
public class AlarmssFragment extends Fragment implements View.OnClickListener {

    //    private LinearLayout now_runtime;
//    private LinearLayout now_faulttime;
//    private LinearLayout now_faultnumber;
    private LinearLayout his_runtime;
    private LinearLayout his_faulttime;
    private LinearLayout his_faultnumber;
    private LinearLayout his_product;
    private TextView now_runtime_text;
    private TextView now_faulttime_text;
    private TextView now_faultnumber_text;
    private TextView his_runtime_text;
    private TextView his_faulttime_text;
    private TextView his_faultnumber_text;
    private TextView his_product_text;
    private JellyRefreshLayout mJellyLayout;

    private String warehouse;
    private String type;
    private ProgressDialog dialog;
    public Gson gson = new Gson();
    LinkWebService linkWebService;
    private LinkedList<AlarmsStatic> alarmsstaticLinkedList;
    private String wsdls;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //now_runtime_text.setText(alarmsstaticLinkedList.get(0).GetNOW_RUNTIME().equals("") ? "无数据" : alarmsstaticLinkedList.get(0).GetNOW_RUNTIME());
                    now_faulttime_text.setText(alarmsstaticLinkedList.get(0).GetNOW_FAULTTIME().equals("") ? "无数据" : alarmsstaticLinkedList.get(0).GetNOW_FAULTTIME());
                    now_faultnumber_text.setText(alarmsstaticLinkedList.get(0).GetNOW_FAULTNUMBER().equals("") ? "无数据" : alarmsstaticLinkedList.get(0).GetNOW_FAULTNUMBER());
                    his_runtime_text.setText(alarmsstaticLinkedList.get(0).GetHIS_RUNTIME().equals("") ? "无数据" : alarmsstaticLinkedList.get(0).GetHIS_RUNTIME());
                    his_faulttime_text.setText(alarmsstaticLinkedList.get(0).GetHIS_FAULTTIME().equals("") ? "无数据" : alarmsstaticLinkedList.get(0).GetHIS_FAULTTIME());
                    his_faultnumber_text.setText(alarmsstaticLinkedList.get(0).GetHIS_FAULTNUMBER().equals("") ? "无数据" : alarmsstaticLinkedList.get(0).GetHIS_FAULTNUMBER());
                    //his_product_text.setText(alarmsstaticLinkedList.get(0).GetHIS_PRODUCTION().equals("") ? "无数据" : alarmsstaticLinkedList.get(0).GetHIS_PRODUCTION());
                    dialog.dismiss();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "刷新完成,无最新数据", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                case 3:
                    Toast.makeText(getActivity(), "网络异常", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    break;
                default:
                    break;

            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        warehouse = getArguments().getString("DetailData_WAREHOUSE");
        type = getArguments().getString("DetailData_TYPE");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_alarmss, container,
                false);

        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
        // 要访问的网址
        wsdls = mSharedPreferences.getString("service_url", "");

        linkWebService = new LinkWebService();

        init(view);

        GetDataFromService();//访问Webservice

        mJellyLayout.setPullToRefreshListener(new PullToRefreshLayout.PullToRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                pullToRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

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

        dialog = ProgressDialog.show(getActivity(), null, "正在加载，请稍候...",
                true, false); //加载提示框

        new Thread(new Runnable() {
            @Override
            public void run() {


                // webservice 的功能名称
                String methods = "getRuntime";
                // 设置传入参数

                Date dt = new Date();
                SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");//获取当前日期

                Map<String, Object> paramss = new HashMap<>();

                if (warehouse.equals("") || type.equals("")) {
                    return;
                }

                String jsons = "[{\"WAREHOUSEID\":" + warehouse + ",\"EQUIPMENTTYPE\":" + ChangeType(type) + ",\"statisticstype\":,\"t_date\":" + sd.format(dt).toString() + "}]";
                //String jsons = "[{\"WAREHOUSEID\":" + "成品库" + ",\"EQUIPMENTTYPE\":" + "SC" + ",\"statisticstype\":111,\"t_date\":" + "2017/08/30" + "}]";
                paramss.put("qrCode", jsons);
                try {
                    String obj = linkWebService.LinkSoapWebservice(wsdls, methods, paramss);
                    if (!obj.equals("") && (!obj.equals("888"))) {
                        //obj = "{\"test\":[{\"TOTALFAULTTIME\":\"48\",\"T_DATE\":\"2017/11/1 0:00:00\"},{\"TOTALFAULTTIME\":\"24\",\"T_DATE\":\"2017/11/2 0:00:00\"},{\"TOTALFAULTTIME\":\"48\",\"T_DATE\":\"2017/11/3 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/4 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/5 0:00:00\"},{\"TOTALFAULTTIME\":\"8\",\"T_DATE\":\"2017/11/6 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/7 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/8 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/9 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/10 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/11 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/12 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/13 0:00:00\"},{\"TOTALFAULTTIME\":\"0\",\"T_DATE\":\"2017/11/15 0:00:00\"},{\"TOTALFAULTTIME\":\"0\",\"T_DATE\":\"2017/11/16 0:00:00\"},{\"TOTALFAULTTIME\":\"0\",\"T_DATE\":\"2017/11/17 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/18 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/19 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/20 0:00:00\"},{\"TOTALFAULTTIME\":\"16\",\"T_DATE\":\"2017/11/21 0:00:00\"},{\"TOTALFAULTTIME\":\"0\",\"T_DATE\":\"2017/11/22 0:00:00\"},{\"TOTALFAULTTIME\":\"208\",\"T_DATE\":\"2017/11/1 0:00:00\"},{\"TOTALFAULTTIME\":\"83\",\"T_DATE\":\"2017/11/2 0:00:00\"},{\"TOTALFAULTTIME\":\"166\",\"T_DATE\":\"2017/11/3 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/4 0:00:00\"},{\"TOTALFAULTTIME\":\"83\",\"T_DATE\":\"2017/11/5 0:00:00\"},{\"TOTALFAULTTIME\":\"190\",\"T_DATE\":\"2017/11/6 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/7 0:00:00\"},{\"TOTALFAULTTIME\":\"108\",\"T_DATE\":\"2017/11/8 0:00:00\"},{\"TOTALFAULTTIME\":\"127\",\"T_DATE\":\"2017/11/9 0:00:00\"},{\"TOTALFAULTTIME\":\"127\",\"T_DATE\":\"2017/11/10 0:00:00\"},{\"TOTALFAULTTIME\":\"76\",\"T_DATE\":\"2017/11/11 0:00:00\"},{\"TOTALFAULTTIME\":\"76\",\"T_DATE\":\"2017/11/12 0:00:00\"},{\"TOTALFAULTTIME\":\"76\",\"T_DATE\":\"2017/11/13 0:00:00\"},{\"TOTALFAULTTIME\":\"0\",\"T_DATE\":\"2017/11/15 0:00:00\"},{\"TOTALFAULTTIME\":\"0\",\"T_DATE\":\"2017/11/16 0:00:00\"},{\"TOTALFAULTTIME\":\"0\",\"T_DATE\":\"2017/11/17 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/18 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/19 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/20 0:00:00\"},{\"TOTALFAULTTIME\":\"14\",\"T_DATE\":\"2017/11/21 0:00:00\"},{\"TOTALFAULTTIME\":\"0\",\"T_DATE\":\"2017/11/22 0:00:00\"}]}";
                        obj = obj.replace("{\"test\":", "");
                        obj = obj.substring(0, obj.length() - 1);
                        alarmsstaticLinkedList = new LinkedList<>();
                        java.lang.reflect.Type type = new TypeToken<AlarmsStatic>() {
                        }.getType();
                        JSONArray arr = new JSONArray(obj);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject temp = (JSONObject) arr.get(i); // 传入字符串
                            AlarmsStatic alarmInstance = gson
                                    .fromJson(temp.toString(), type);
                            if (!alarmsstaticLinkedList.contains(alarmInstance)) {
                                alarmsstaticLinkedList.addLast(alarmInstance);
                            }
                        }
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessageDelayed(msg, 3000);
                    } else {
                        Message msg = new Message();
                        msg.what = 2;
                        mHandler.sendMessageDelayed(msg, 3000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = 3;
                    mHandler.sendMessageDelayed(msg, 3000);
                }
            }
        }).start();

    }

    private String ChangeType(String type) {

        String type_zh = "";

        if (type.indexOf("机器人") >= 0) {
            type_zh = "AGV";
        } else if (type.indexOf("堆垛机") >= 0) {
            type_zh = "SC";
        } else if (type.indexOf("输送机") >= 0){
            type_zh = "STA";
        } else if (type.indexOf("成品入库分拣") >= 0){
            type_zh = "CPINFJ";
        } else if (type.indexOf("升降机") >= 0){
            type_zh = "SSJ";
        } else if (type.indexOf("机械手") >= 0){
            type_zh = "ROBOT";
        } else if (type.indexOf("夹抱机") >= 0){
            type_zh = "JBJ";
        } else if (type.indexOf("入库输送机") >= 0){
            type_zh = "INSTA";
        } else if (type.indexOf("拆盘机") >= 0){
            type_zh = "CPJ";
        } else if (type.indexOf("穿梭车") >= 0){
            type_zh = "CSC";
        } else if (type.indexOf("码盘机") >= 0){
            type_zh = "MPJ";
        } else if (type.indexOf("出库输送机") >= 0){
            type_zh = "OUTSTA";
        }

        return type_zh;

    }

    private void init(View view) {

//        now_runtime = (LinearLayout) view.findViewById(now_runtime);
//        now_faulttime = (LinearLayout) view.findViewById(now_faulttime);
//        now_faultnumber = (LinearLayout) view.findViewById(now_faultnumber);
        his_faulttime = (LinearLayout) view.findViewById(R.id.his_faulttime);
        his_runtime = (LinearLayout) view.findViewById(R.id.his_runtime);
        his_faultnumber = (LinearLayout) view.findViewById(R.id.his_faultnumber);
        his_product = (LinearLayout) view.findViewById(R.id.his_product);
        //now_runtime.setOnClickListener(this);
        //now_faulttime.setOnClickListener(this);
        his_faulttime.setOnClickListener(this);
        his_faultnumber.setOnClickListener(this);
        his_product.setOnClickListener(this);
        //now_faultnumber.setOnClickListener(this);
        his_runtime.setOnClickListener(this);

        //now_runtime_text = (TextView) view.findViewById(R.id.now_runtime_text);
        now_faulttime_text = (TextView) view.findViewById(R.id.now_faulttime_text);
        now_faultnumber_text = (TextView) view.findViewById(R.id.now_faultnumber_text);
        his_runtime_text = (TextView) view.findViewById(R.id.his_runtime_text);
        his_faulttime_text = (TextView) view.findViewById(R.id.his_faulttime_text);
        his_faultnumber_text = (TextView) view.findViewById(R.id.his_faultnumber_text);
        //his_product_text = (TextView) view.findViewById(R.id.his_product_text);
        mJellyLayout = (JellyRefreshLayout) view.findViewById(R.id.jelly_refresh_type_alarmss);

    }

    @Override
    public void onClick(View v) {

        Intent it = new Intent();
        switch (v.getId()) {
//            case now_runtime:
//                it.setClass(getActivity(), ChartActivity.class);
//                startActivity(it);
//                break;
//            case now_faulttime:
//                it.setClass(getActivity(), ChartActivity.class);
//                startActivity(it);
//                break;
//            case now_faultnumber:
//                it.setClass(getActivity(), ChartActivity.class);
//                startActivity(it);
//                break;
            case R.id.his_runtime:
                Bundle hf = new Bundle();
                hf.putString("DetailData_WAREHOUSE", warehouse);
                hf.putString("DetailData_TYPE", type);
                hf.putString("Statistics_TYPE", "his_runtime");
                hf.putString("Select_TYPE", "历史运行时长");
                it.putExtras(hf);
                it.setClass(getActivity(), ChartActivity.class);
                startActivity(it);
                break;
            case R.id.his_faulttime:
                Bundle hr = new Bundle();
                hr.putString("DetailData_WAREHOUSE", warehouse);
                hr.putString("DetailData_TYPE", type);
                hr.putString("Statistics_TYPE", "his_faulttime");
                hr.putString("Select_TYPE", "历史故障时长");
                it.putExtras(hr);
                it.setClass(getActivity(), ChartActivity.class);
                startActivity(it);
                break;
            case R.id.his_faultnumber:
                Bundle hfa = new Bundle();
                hfa.putString("DetailData_WAREHOUSE", warehouse);
                hfa.putString("DetailData_TYPE", type);
                hfa.putString("Statistics_TYPE", "his_faultnumber");
                hfa.putString("Select_TYPE", "历史故障次数");
                it.putExtras(hfa);
                it.setClass(getActivity(), ChartActivity.class);
                startActivity(it);
                break;
            case R.id.his_product:
                Bundle hp = new Bundle();
                hp.putString("DetailData_WAREHOUSE", warehouse);
                hp.putString("DetailData_TYPE", type);
                hp.putString("Statistics_TYPE", "1");
                hp.putString("Select_TYPE", "历史产量");
                it.putExtras(hp);
                it.setClass(getActivity(), ChartActivity.class);
                startActivity(it);
                break;
            default:
                break;
        }

    }
}
