package com.nti56.scadashow.scadashow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;

/**
 * Created by chencheng on 2017/8/11.
 */

public class TypeDetailsAdapter extends BaseAdapter{

    private Context context;
    private String Info;
    private String[] datas;
    private TextView info_type;
    private TextView info_item;

    public TypeDetailsAdapter(Context context,String Info){
        this.context = context;
        this.Info = Info;
        datas = Info.split("&");
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
                R.layout.fragment_type_details_item, parent, false);
        info_type = (TextView) view.findViewById(R.id.info_type);
        info_item = (TextView) view.findViewById(R.id.info_item);
        info_type.setText(datas[position].split("\\|")[0]);
        info_item.setText(datas[position].split("\\|")[1]);
        return view;
    }
}
