package com.nti56.scadashow.scadashow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nti56.scadashow.scadashow.R;

/**
 * Created by chencheng on 2017/8/31.
 */

public class VersionDetailAdapter extends BaseAdapter {

    private Context context;
    private String[] datas;

    private TextView version_number;
    private TextView version_time;

    public VersionDetailAdapter(Context context, String[] datas) {

        this.context = context;
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
        View view = LayoutInflater.from(context).inflate(
                R.layout.activity_version_detail_item, parent, false);

        version_number = (TextView) view.findViewById(R.id.version_number);
        version_time = (TextView) view.findViewById(R.id.version_time);

        version_number.setText("SCADA数据监控平台 版本:  " + datas[datas.length - 1 - position].split("\\|")[0]);
        version_time.setText("更新日期:  " + datas[datas.length - 1 - position].split("\\|")[1]);
        return view;
    }
}
