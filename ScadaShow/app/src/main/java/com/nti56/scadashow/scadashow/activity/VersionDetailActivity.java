package com.nti56.scadashow.scadashow.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.adapter.VersionDetailAdapter;
import com.nti56.scadashow.scadashow.interfaces.ScadaApplication;

import static com.nti56.scadashow.scadashow.R.id.text_title;

/**
 * Created by chencheng on 2017/8/31.
 */

public class VersionDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView version_listview;
    private TextView text_title;
    private TextView basks;
    private TextView version_number_title;
    private TextView version_details;
    private ScadaApplication sapps;
    private String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_detail);

        sapps = (ScadaApplication) this.getApplication();
        data = sapps.GetVersionDetail();
        text_title = (TextView) findViewById(R.id.text_title);
        basks = (TextView) findViewById(R.id.backs);
        version_number_title = (TextView) findViewById(R.id.version_number_title);
        version_details = (TextView) findViewById(R.id.version_details);
        version_listview = (ListView) findViewById(R.id.version_listview);

        text_title.setText("版本信息");
        version_number_title.setText("SCADA数据监控平台 版本:  " + data.split("&")[data.split("&").length - 1].split("\\|")[0]);
        String details = data.split("&")[data.split("&").length - 1].split("\\|")[2];
        details = details.replace(" ", "\n");
        version_details.setText("更新内容:\n" + details);

        VersionDetailAdapter versionDetailAdapter = new VersionDetailAdapter(this, data.split("&"));
        version_listview.setAdapter(versionDetailAdapter);
        version_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                version_number_title.setText("SCADA数据监控平台 版本:  " + data.split("&")[data.split("&").length - 1 - position].split("\\|")[0]);
                String details = data.split("&")[data.split("&").length - 1 - position].split("\\|")[2];
                details = details.replace(" ", "\n");
                version_details.setText("更新内容:\n" + details);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backs:
                finish();
                break;
            default:
                break;
        }
    }
}
