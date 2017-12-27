package com.nti56.scadashow.scadashow.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.moxun.tagcloudlib.view.TagsAdapter;
import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.activity.HomeByInfoActivity;
import com.nti56.scadashow.scadashow.bean.Alarm;
import com.nti56.scadashow.scadashow.interfaces.ScadaApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.nti56.scadashow.scadashow.R.id.order_pic;

/**
 * Created by chencheng on 2017/7/27.
 */

public class HomeFragmenAdapter extends BaseAdapter {

    Context mContext;
    private TextView home_list_item;
    private TextView list_item_detail;
    private ImageView imageview_home_list_item;

    private TextView home_list_item2;
    private TextView list_item_detail2;
    private ImageView imageview_home_list_item2;

    private LinearLayout home_layout1;
    private LinearLayout home_layout2;

    private LinearLayout home_list_item_layout;

    private String[] datas;


    public HomeFragmenAdapter(Context mContext, String[] datas) {
        this.mContext = mContext;
        this.datas = datas;
    }


    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.fragment_home_list_item, parent, false);
        InitView(view);

        if (position % 2 != 1) {
            home_layout1.setVisibility(View.VISIBLE);
            home_layout2.setVisibility(View.GONE);

            home_list_item.setText(datas[position]);
            list_item_detail.setText(datas[position] + "设备信息");
            if (datas[position] == "成品综合库") {
                imageview_home_list_item.setBackgroundResource(R.drawable.img_robot);
            } else if (datas[position] == "辅料平衡库") {
                imageview_home_list_item.setBackgroundResource(R.drawable.img_agv);
            } else {
                imageview_home_list_item.setBackgroundResource(R.drawable.img_ddj);
            }
        } else {

            home_layout2.setVisibility(View.VISIBLE);
            home_layout1.setVisibility(View.GONE);

            home_list_item2.setText(datas[position]);
            list_item_detail2.setText(datas[position] + "设备信息");
            if (datas[position] == "成品综合库") {
                imageview_home_list_item2.setBackgroundResource(R.drawable.img_robot);
            } else if (datas[position] == "辅料平衡库") {
                imageview_home_list_item2.setBackgroundResource(R.drawable.img_agv);
            } else {
                imageview_home_list_item2.setBackgroundResource(R.drawable.img_csc);
            }
        }


        return view;
    }

    private void InitView(View view) {

        home_list_item = (TextView) view.findViewById(R.id.home_list_item);
        list_item_detail = (TextView) view.findViewById(R.id.list_item_detail);
        imageview_home_list_item = (ImageView) view.findViewById(R.id.imageview_home_list_item);

        home_list_item2 = (TextView) view.findViewById(R.id.home_list_item2);
        list_item_detail2 = (TextView) view.findViewById(R.id.list_item_detail2);
        imageview_home_list_item2 = (ImageView) view.findViewById(R.id.imageview_home_list_item2);

        home_layout1 = (LinearLayout) view.findViewById(R.id.home_layout1);
        home_layout2 = (LinearLayout) view.findViewById(R.id.home_layout2);

        home_list_item_layout = (LinearLayout) view.findViewById(R.id.home_list_item_layout);
        //动态获取屏幕高度
        DisplayMetrics dm2 = mContext.getResources().getDisplayMetrics();
        ViewGroup.LayoutParams para;
        para = home_list_item_layout.getLayoutParams();
        para.height = dm2.heightPixels / 4;
        home_list_item_layout.setLayoutParams(para);//动态设置显示宽度
    }
}
