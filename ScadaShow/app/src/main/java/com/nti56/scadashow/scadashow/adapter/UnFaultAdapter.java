package com.nti56.scadashow.scadashow.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.activity.ImgView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by chencheng on 2017/8/14.
 */

public class UnFaultAdapter extends BaseAdapter{

    private Context context;
    private ArrayList paths;
    private ImageView img_pic;

    public UnFaultAdapter(Context context,ArrayList paths){
        this.context = context;
        this.paths = paths;

    }

    @Override
    public int getCount() {
        return paths.size();
    }

    @Override
    public Object getItem(int position) {
        return paths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.fragment_unfault_item, parent, false);
        img_pic = (ImageView) view.findViewById(R.id.img_pic);
        img_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("filename", (String) paths.get(position));
                intent.putExtras(bundle);
                intent.setClass(context,ImgView.class);
                context.startActivity(intent);
            }
        });
        File file = new File((String) paths.get(position));
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile((String) paths.get(position));
            //将图片显示到ImageView中
            img_pic.setImageBitmap(bm);
        }

        return view;
    }
}
