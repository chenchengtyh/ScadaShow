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

import static com.nti56.scadashow.scadashow.R.drawable.alarm;

/**
 * Created by chencheng on 2017/8/15.
 */

public class NowOrderAdapter extends BaseAdapter {

    private Context context;
    private LinkedList<OrderInfo> orderInfos;
    private TextView new_orderID;
    private TextView new_order_time;
    private TextView new_order_level;
    private TextView new_order_detail;

    public NowOrderAdapter(Context context, LinkedList<OrderInfo> orderInfos) {

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
                R.layout.fragment_now_order_item, parent, false);

        new_orderID = (TextView) view.findViewById(R.id.new_orderID);
        new_order_time = (TextView) view.findViewById(R.id.new_order_time);
        new_order_level = (TextView) view.findViewById(R.id.new_order_level);
        new_order_detail = (TextView) view.findViewById(R.id.new_order_detail);
        new_orderID.setText("维保工单号: " + orderInfos.get(position).GetORDERNUMBER());
        new_order_time.setText(orderInfos.get(position).GetCREATEDATE());
        new_order_level.setText(orderInfos.get(position).GetWAREHOUSEID() + "-->" + Changes(orderInfos.get(position).GetEQUIPMENTTYPE()));
        new_order_detail.setText("维保内容: " + orderInfos.get(position).GetODERDESCRIPTION());

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
