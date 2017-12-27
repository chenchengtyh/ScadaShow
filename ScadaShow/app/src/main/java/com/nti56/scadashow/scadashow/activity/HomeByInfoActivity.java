package com.nti56.scadashow.scadashow.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.adapter.HomeByInfoAdapter;
import com.nti56.scadashow.scadashow.bean.Alarm;
import com.nti56.scadashow.scadashow.interfaces.ScadaApplication;

import java.util.LinkedList;

import uk.co.imallan.jellyrefresh.JellyRefreshLayout;
import uk.co.imallan.jellyrefresh.PullToRefreshLayout;


/**
 * Created by chencheng on 2017/7/31.
 */
public class HomeByInfoActivity extends AppCompatActivity{

    private String warehourse;
    private String data;
    private ListView listview_home;
    private JellyRefreshLayout mJellyLayout_home;
    private ScadaApplication sapps;
    private TextView textview2_home;
    private TextView title;
    private Button backs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_by_info);

        sapps = (ScadaApplication) getApplication();
        Bundle bundle = getIntent().getExtras();
        warehourse = bundle.getString("warehourse");

        title = (TextView) findViewById(R.id.text_title);
        title.setText(warehourse);
        backs = (Button) findViewById(R.id.backs);
        backs.setVisibility(View.VISIBLE);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textview2_home = (TextView) findViewById(R.id.textview2_home);
        listview_home = (ListView)findViewById(R.id.listview_home);

        LinkedList<Alarm> alarm =  FiliterData(sapps.getData(),warehourse);//筛选
        if(alarm.size() != 0) {
            HomeByInfoAdapter homeByInfoAdapter = new HomeByInfoAdapter(this, alarm);
            listview_home.setAdapter(homeByInfoAdapter);
        }else{
            textview2_home.setVisibility(View.VISIBLE);
            Toast.makeText(HomeByInfoActivity.this,"该区域无报警信息",Toast.LENGTH_SHORT).show();
        }

        mJellyLayout_home = (JellyRefreshLayout) findViewById(R.id.jelly_refresh_home);
        mJellyLayout_home.setPullToRefreshListener(new PullToRefreshLayout.PullToRefreshListener() {
            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                //Toast.makeText(getActivity(), "1", Toast.LENGTH_SHORT).show();
                pullToRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mJellyLayout_home.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        View loadingView = LayoutInflater.from(this).inflate(R.layout.view_loading, null);
        mJellyLayout_home.setLoadingView(loadingView);
    }

    private LinkedList<Alarm> FiliterData(LinkedList<Alarm> alarm, String warehourse) {

            LinkedList<Alarm> al = new LinkedList<>();

            for (int i = 0; i < alarm.size(); i++) {
                if (alarm.get(i).GetWAREHOUSENAME().equals(warehourse)) {
                    al.addLast(alarm.get(i));
                }
            }
            return al;
    }
}
