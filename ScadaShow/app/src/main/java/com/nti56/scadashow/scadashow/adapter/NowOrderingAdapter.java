package com.nti56.scadashow.scadashow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.bean.Alarm;

import java.util.LinkedList;

/**
 * Created by chencheng on 2017/8/15.
 */

public class NowOrderingAdapter extends BaseAdapter {

    private Context context;
    private LinkedList<Alarm> alarms;
    private TextView orderID;
    private TextView order_time;
    private TextView order_level;
    private TextView order_detail;


    public NowOrderingAdapter(Context context, LinkedList<Alarm> alarms) {

        this.context = context;
        this.alarms = alarms;

    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.fragment_ordering_item, parent, false);

        orderID = (TextView) view.findViewById(R.id.orderID);
        order_time = (TextView) view.findViewById(R.id.order_time);
        order_level = (TextView) view.findViewById(R.id.order_level);
        order_detail = (TextView) view.findViewById(R.id.order_detail);
        orderID.setText("工单ID:"+alarms.get(position).GetID().substring(0,5));
        order_time.setText(alarms.get(position).GetERRORTIME());
        order_level.setText("报警等级:高");
        order_detail.setText("报警内容:"+alarms.get(position).GetERRORSTRING());

        return view;
    }
}
