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
import com.nti56.scadashow.scadashow.activity.AlarmFragment;
import com.nti56.scadashow.scadashow.bean.Alarm;

import java.util.LinkedList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by chencheng on 2017/7/28.
 */

public class AlarmFragmentAdapter extends BaseAdapter {

    private Context mContext = null;
    private LinkedList<Alarm> alarmLinkedList;

    private ImageView iv_icon;
    private TextView warehourse;
    private TextView type;
    private TextView stationno;
    private TextView errorcode;
    private TextView errorstring;
    private TextView time;
    private LinearLayout alarm_list_item_layout;
    private LinearLayout layout_usernames;
    private TextView usernames;
    private ImageView img_flag;

    public AlarmFragmentAdapter(Context context, LinkedList<Alarm> alarmLinkedList) {
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
                R.layout.fragment_alarm_list_item, parent, false);
        Init(view);

        Alarm alarm = alarmLinkedList.get(position);
        if (alarm.GetSTATUS().equals("1")) {
            //alarm_list_item_layout.setBackgroundResource(R.drawable.finished);
        } else {
            alarm_list_item_layout.setBackgroundResource(0);
        }
        if (alarm.GetTYPE().equals("SC")) {
            iv_icon.setBackgroundResource(R.drawable.img_ddj);
        } else if (alarm.GetTYPE().equals("AGV")) {
            iv_icon.setBackgroundResource(R.drawable.img_agv);
        } else if (alarm.GetTYPE().equals("ROBOT")) {
            iv_icon.setBackgroundResource(R.drawable.img_robot);
        } else if (alarm.GetTYPE().equals("CSC")) {
            iv_icon.setBackgroundResource(R.drawable.img_csc);
        } else {
            iv_icon.setBackgroundResource(R.drawable.img_nopic);
        }

        if (alarm.GetSTATUS().equals("2")) {
            img_flag.setBackgroundResource(R.drawable.rc_alarm_nervers);
        } else {
            img_flag.setBackgroundResource(R.drawable.rc_alarm_ings);
            layout_usernames.setVisibility(View.VISIBLE);
            usernames.setText("员工( "+alarm.GetUSERNAME()+" )正在处理中...");
        }
        warehourse.setText(alarm.GetWAREHOUSENAME() + "-->");
        type.setText(Changes(alarm.GetTYPE()));
        stationno.setText("设备编号: " + alarm.GetSTATIONNO());
        errorcode.setText("故障代码: " + alarm.GetERRORCODE());
        errorstring.setText("故障描述: " + alarm.GetERRORSTRING());
        time.setText(alarm.GetERRORTIME());

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
        } else if(s.equals("CPINFJ")){
            type = "成品入库分拣";
        } else if(s.equals("SJJ")){
            type = "升降机";
        } else if(s.equals("OUTSTA")){
            type = "出库输送机";
        } else if(s.equals("INSTA")){
            type = "入库输送机";
        }else {
            type = "拆盘机";
        }

        return type;

    }

    private void Init(View view) {
        img_flag = (ImageView) view.findViewById(R.id.img_flag);
        iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
        warehourse = (TextView) view.findViewById(R.id.warehourse);
        type = (TextView) view.findViewById(R.id.type);
        stationno = (TextView) view.findViewById(R.id.stationno);
        errorcode = (TextView) view.findViewById(R.id.errorcode);
        errorstring = (TextView) view.findViewById(R.id.errorstring);
        time = (TextView) view.findViewById(R.id.time);
        alarm_list_item_layout = (LinearLayout) view.findViewById(R.id.alarm_list_item_layout);
        layout_usernames = (LinearLayout) view.findViewById(R.id.layout_usernames);
        usernames = (TextView) view.findViewById(R.id.usernames);
    }
}
