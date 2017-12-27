package com.nti56.scadashow.scadashow.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.adapter.HomeFragmenAdapter;
import com.nti56.scadashow.scadashow.bean.Alarm;

import static com.nti56.scadashow.scadashow.R.id.listview;


/**
 * Created by chencheng on 2017/7/27.
 */

public class HomeFragment extends Fragment {

    private String data;

    private ListView listview_fund;
    private String[] datas = {"成品综合库", "辅料平衡库", "原料配方库"};


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        data = getArguments().getString("PageData");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container,
                false);

        listview_fund = (ListView) view.findViewById(R.id.listview_fund);
        HomeFragmenAdapter homeFragmenAdapter = new HomeFragmenAdapter(getActivity(), datas);
        listview_fund.setAdapter(homeFragmenAdapter);
        listview_fund.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent();
                intent.setClass(getActivity(),
                        HomeTypeDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putString("DetailData_position", datas[position]);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        });
        return view;

    }
}
