package com.nti56.scadashow.scadashow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.adapter.HomeTypeDetalAdapter;
import com.nti56.scadashow.scadashow.interfaces.ScadaApplication;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.nti56.scadashow.scadashow.R.id.listview_fund;

/**
 * Created by chencheng on 2017/8/9.
 */
public class HomeTypeDetailActivity extends AppCompatActivity {

    private ListView listview_types;
    private TextView text_title;
    private ScadaApplication scadaApplication;
    private Button backs;
    String title;

    String[] datas_flk = {"入库输送机", "出库输送机", "码盘机", "拆盘机", "穿梭车", "堆垛机", "AGV"};
    String[] datas_cpk = {"成品入库分拣","机械手","入库输送机","出库输送机","码盘机","拆盘机","穿梭车","堆垛机"};
    String[] datas_pfk = {"入库输送机", "出库输送机", "码盘机", "拆盘机", "穿梭车", "堆垛机", "夹抱机", "升降机"};
    String[] datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_type_detail);

        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("DetailData_position");
        scadaApplication = (ScadaApplication) getApplication();

        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText(title + "资产");
        if (title.equals("成品综合库")) {
            datas = datas_cpk;
        } else if (title.equals("辅料平衡库")) {
            datas = datas_flk;
        } else {
            datas = datas_pfk;
        }
        backs = (Button) findViewById(R.id.backs);
        backs.setVisibility(View.VISIBLE);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview_types = (ListView) findViewById(R.id.listview_types);
        HomeTypeDetalAdapter homeTypeDetail = new HomeTypeDetalAdapter(this, datas);
        listview_types.setAdapter(homeTypeDetail);
        listview_types.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info = scadaApplication.getInfo(datas[position]);
                if(info.equals("null")){
                    Toast.makeText(HomeTypeDetailActivity.this,"暂无此设备数据",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent();
                    intent.setClass(HomeTypeDetailActivity.this,
                            TypeDetailActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("DetailData_TYPE", datas[position]);
                    mBundle.putString("DetailData_WAREHOUSE",title);
                    intent.putExtras(mBundle);
                    startActivity(intent);
                }

            }
        });
    }
}
