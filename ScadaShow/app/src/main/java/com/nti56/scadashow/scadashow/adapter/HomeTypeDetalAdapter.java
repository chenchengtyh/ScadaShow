package com.nti56.scadashow.scadashow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;

/**
 * Created by chencheng on 2017/8/9.
 */

public class HomeTypeDetalAdapter extends BaseAdapter {

    private Context mContext;
    private String[] datas;
    private TextView null_text;
    private TextView type_detail;
    private ImageView img_home_type_detail_item;

    public HomeTypeDetalAdapter(Context mContext, String[] datas) {
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
                R.layout.home_type_detail_item, parent, false);
        Init(view);
        //Toast.makeText(mContext,"datas[position]="+datas[position].substring(0,3),Toast.LENGTH_SHORT).show();

        if (datas[position].equals("堆垛机")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.img_ddj);
        } else if ((datas[position].equals("AGV")) || (datas[position].indexOf("机器人") != -1)) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.img_agv);
        } else if (datas[position].equals("穿梭车")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.img_csc);
        } else if (datas[position].equals("输送机")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.img_ssj);
        } else if (datas[position].equals("机械手")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.img_robot);
        } else if (datas[position].equals("入库输送机")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_gsssj);
        } else if (datas[position].equals("链式输送机")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_ltssj);
        } else if (datas[position].equals("辊式顶升移栽机")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_gsdsyzj);
        } else if (datas[position].equals("拆盘机")||datas[position].equals("码盘机")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_cmpj);
        } else if (datas[position].equals("旋转台")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_xzt);
        } else if (datas[position].equals("托盘自动落地设备")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_tpzdsdsb);
        } else if (datas[position].equals("夹抱机")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_ybjbj);
        } else if (datas[position].equals("升降机")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_cztsj);
        } else if (datas[position].equals("出库分拣系统")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_ckfjxt);
        } else if (datas[position].equals("成品入库分拣")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_rkfjxt);
        } else if (datas[position].equals("出库输送机")) {
            img_home_type_detail_item.setBackgroundResource(R.drawable.rc_ltssj);
        } else {
            img_home_type_detail_item.setBackgroundResource(R.drawable.img_nopic);
        }

        null_text.setVisibility(View.VISIBLE);
        type_detail.setText(datas[position]);

        return view;
    }

    private void Init(View view) {

        null_text = (TextView) view.findViewById(R.id.null_text);
        type_detail = (TextView) view.findViewById(R.id.type_detail);
        img_home_type_detail_item = (ImageView) view.findViewById(R.id.img_home_type_detail_item);
    }
}
