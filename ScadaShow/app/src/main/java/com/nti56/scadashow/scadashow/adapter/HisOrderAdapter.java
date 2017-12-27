package com.nti56.scadashow.scadashow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.bean.Alarm;
import com.nti56.scadashow.scadashow.bean.OrderInfo;

import java.util.LinkedList;

/**
 * Created by chencheng on 2017/8/15.
 */

public class HisOrderAdapter extends BaseAdapter {

    private Context context;
    private LinkedList<OrderInfo> orderInfos;
    private TextView his_orderID;
    private TextView his_order_time;
    private TextView his_order_level;
    private TextView his_order_detail;

    public HisOrderAdapter(Context context, LinkedList<OrderInfo> orderInfos) {

        this.context = context;
        this.orderInfos = orderInfos;

    }

    @Override
    public int getCount() {
        return orderInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return orderInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.fragment_his_order_item, parent, false);
        his_orderID = (TextView) view.findViewById(R.id.his_orderID);
        his_order_time = (TextView) view.findViewById(R.id.his_order_time);
        his_order_level = (TextView) view.findViewById(R.id.his_order_level);
        his_order_detail = (TextView) view.findViewById(R.id.his_order_detail);
        his_orderID.setText("维保工单号: " + orderInfos.get(position).GetORDERNUMBER());
        his_order_time.setText(orderInfos.get(position).GetCREATEDATE());
        his_order_level.setText(orderInfos.get(position).GetWAREHOUSEID() + "-->" + Changes(orderInfos.get(position).GetEQUIPMENTTYPE()));
        his_order_detail.setText("维保内容: " + orderInfos.get(position).GetODERDESCRIPTION());

        return view;
    }

    private String Changes(String s) {

        String type;

        if (s.equals("SC")) {
            type = "堆垛机";
        } else if (s.equals("AGV")) {
            type = "AGV";
        } else if (s.equals("STA")) {
            type = "输送机";
        } else if (s.equals("CSC")) {
            type = "穿梭车";
        } else if (s.equals("JBJ")) {
            type = "夹抱机";
        } else if (s.equals("ROBOT")) {
            type = "机械手";
        } else if (s.equals("MPJ")) {
            type = "码盘机";
        } else if (s.equals("CPINFJ")) {
            type = "成品入库分拣";
        } else if (s.equals("SJJ")) {
            type = "升降机";
        } else if (s.equals("OUTSTA")) {
            type = "出库输送机";
        } else if (s.equals("INSTA")) {
            type = "入库输送机";
        } else {
            type = "拆盘机";
        }

        return type;

    }
}
