package com.nti56.scadashow.scadashow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.bean.Alarm;

import java.util.LinkedList;

import static com.nti56.scadashow.scadashow.R.id.alarm_list_item_layout;

/**
 * Created by chencheng on 2017/8/1.
 */

public class HomeByInfoAdapter extends BaseAdapter {

    private Context mContext = null;
    private LinkedList<Alarm> alarmLinkedList;

    private ImageView iv_icon;
    private TextView warehourse;
    private TextView type;
    private TextView stationno;
    private TextView errorcode;
    private TextView errorstring;
    private TextView time;
    private LinearLayout home_by_info_item_layout;

    public HomeByInfoAdapter(Context context, LinkedList<Alarm> alarmLinkedList) {
        this.mContext = context;
        this.alarmLinkedList = alarmLinkedList;
    }

    @Override
    public int getCount() {
        return alarmLinkedList.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmLinkedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.home_by_info_item, parent, false);
        Init(view);

        Alarm alarm = alarmLinkedList.get(position);
        if(alarm.GetSTATUS().equals("1")) {
            home_by_info_item_layout.setBackgroundResource(R.drawable.finished);
        }else{
            home_by_info_item_layout.setBackgroundResource(0);
        }
        if(alarm.GetTYPE().equals("堆垛机")){
            iv_icon.setBackgroundResource(R.drawable.img_ddj);
        }else if(alarm.GetTYPE().equals("AGV")){
            iv_icon.setBackgroundResource(R.drawable.img_agv);
        }else if(alarm.GetTYPE().equals("机械手")){
            iv_icon.setBackgroundResource(R.drawable.img_robot);
        }else{
            iv_icon.setBackgroundResource(R.drawable.img_csc);
        }
        warehourse.setText(alarm.GetWAREHOUSENAME()+"-->");
        type.setText(alarm.GetTYPE());
        stationno.setText("设备编号: "+alarm.GetSTATIONNO());
        errorcode.setText("故障代码: "+alarm.GetERRORCODE());
        errorstring.setText("故障描述: "+alarm.GetERRORSTRING());
        time.setText(alarm.GetERRORTIME());

        return view;
    }

    private void Init(View view) {
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        warehourse = (TextView) view.findViewById(R.id.warehourse);
        type = (TextView) view.findViewById(R.id.type);
        stationno = (TextView) view.findViewById(R.id.stationno);
        errorcode = (TextView) view.findViewById(R.id.errorcode);
        errorstring = (TextView) view.findViewById(R.id.errorstring);
        time = (TextView) view.findViewById(R.id.time);
        home_by_info_item_layout = (LinearLayout) view.findViewById(R.id.home_by_info_item_layout);
    }
}
