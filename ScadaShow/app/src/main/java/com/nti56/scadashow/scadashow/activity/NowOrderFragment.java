package com.nti56.scadashow.scadashow.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.adapter.NowOrderAdapter;
import com.nti56.scadashow.scadashow.adapter.NowOrderingAdapter;
import com.nti56.scadashow.scadashow.bean.Alarm;
import com.nti56.scadashow.scadashow.bean.OrderInfo;
import com.nti56.scadashow.scadashow.interfaces.ScadaApplication;
import com.nti56.scadashow.scadashow.utils.LinkWebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import uk.co.imallan.jellyrefresh.JellyRefreshLayout;
import uk.co.imallan.jellyrefresh.PullToRefreshLayout;

import static android.R.attr.data;
import static com.nti56.scadashow.scadashow.R.drawable.alarm;
import static com.nti56.scadashow.scadashow.R.id.listview_his_order;
import static com.nti56.scadashow.scadashow.R.id.listview_ordering;

/**
 * Created by chencheng on 2017/8/15.
 */
public class NowOrderFragment extends Fragment {

    private ListView listview_new_order;
    //private ListView listview_ordering;
    ScadaApplication apps;

    private LinkWebService linkWebService;
    private LinkedList<OrderInfo> orderInfoLinkedList;
    public Gson gson = new Gson();
    private JellyRefreshLayout mJellyLayout;
    private ProgressDialog dialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        apps = (ScadaApplication) getActivity().getApplication();
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    NowOrderAdapter nowOrderAdapter = new NowOrderAdapter(getActivity(), orderInfoLinkedList);
                    listview_new_order.setAdapter(nowOrderAdapter);
                    dialog.dismiss();
                    break;
                case 2:
                    dialog.dismiss();
                    break;
                default:
                    break;
            }

        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_order, container,
                false);
        listview_new_order = (ListView) view.findViewById(R.id.listview_new_order);
        //listview_ordering = (ListView) view.findViewById(R.id.listview_ordering);
        linkWebService = new LinkWebService();
        GetDataFromService();

        //NowOrderingAdapter nowOrderingAdapter = new NowOrderingAdapter(getActivity(), getData2(apps.getData()));
        //listview_ordering.setAdapter(nowOrderingAdapter);

        listview_new_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent();
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("OrderDetail", orderInfoLinkedList.get(position));
                it.putExtras(mBundle);
                it.setClass(getActivity(), OrderDetailActivity.class);
                startActivity(it);
            }
        });

//        listview_ordering.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent it = new Intent();
//                it.setClass(getActivity(),OrderDetailActivity.class);
//                startActivity(it);
//            }
//        });
        mJellyLayout = (JellyRefreshLayout) view.findViewById(R.id.jelly_refresh_new_order);
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

        dialog = ProgressDialog.show(getActivity(), null, "正在加载中,请稍等...",
                true, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
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
                        orderInfoLinkedList = new LinkedList<>();
                        java.lang.reflect.Type type = new TypeToken<OrderInfo>() {
                        }.getType();
                        JSONArray arr = new JSONArray(obj);
                        Log.d("jochen", "NowOrderFragment=" + arr.length());
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject temp = (JSONObject) arr.get(i); // 传入字符串
                            OrderInfo orderInfoInstance = gson
                                    .fromJson(temp.toString(), type);
                            if (!orderInfoLinkedList.contains(orderInfoInstance)) {
                                orderInfoLinkedList.addLast(orderInfoInstance);
                            }
                        }
                        orderInfoLinkedList = getData(orderInfoLinkedList);
                        Message msg = new Message();
                        msg.what = 1;
                        //msg.obj = obj;
                        mHandler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    }

                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private LinkedList<OrderInfo> getData(LinkedList<OrderInfo> data) {

        LinkedList<OrderInfo> order = new LinkedList<>();
        if (data.size() == 0) {
            return null;
        } else {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).GetORDERSTATUS().equals("新建")) {
                    order.addLast(data.get(i));
                }
            }
            return order;
        }

    }

}
