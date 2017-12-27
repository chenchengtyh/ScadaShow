package com.nti56.scadashow.scadashow.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nti56.scadashow.scadashow.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

/**
 * Created by chencheng on 2017/8/9.
 */
public class TypeDetailActivity extends AppCompatActivity {

    private TextView title;
    private RadioGroup radioGroup;
    private Button typeinfoGroupID;
    private Button typealarmGroupID;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private String type;
    private String warehouse;
    private Button backs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_details);

        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("DetailData_TYPE");
        warehouse = bundle.getString("DetailData_WAREHOUSE");
        title = (TextView) findViewById(R.id.text_title);
        title.setText(type);
        backs = (Button) findViewById(R.id.backs);
        backs.setVisibility(View.VISIBLE);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fm = getSupportFragmentManager();

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupID);
        typeinfoGroupID = (Button) findViewById(R.id.typeinfoGroupID);

        ft = fm.beginTransaction();
        TypeFragment tf = new TypeFragment();
        Bundle hfb = new Bundle();
        hfb.putString("PageData", type);
        tf.setArguments(hfb);
        ft.replace(R.id.frame_container2, tf, "TypeFragment");
        ft.commit();


        typealarmGroupID = (Button) findViewById(R.id.typealarmGroupID);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                //String result = radioButton.getText().toString();
                switch (checkedId) {
                    case R.id.typeinfoGroupID:
                        ft = fm.beginTransaction();
                        TypeFragment tf = new TypeFragment();
                        Bundle hfb = new Bundle();
                        hfb.putString("PageData", type);
                        tf.setArguments(hfb);
                        ft.replace(R.id.frame_container2, tf, "TypeFragment");
                        ft.commit();
                        break;
                    case R.id.typealarmGroupID:
                        ft = fm.beginTransaction();
                        AlarmssFragment asf = new AlarmssFragment();
                        Bundle afb = new Bundle();
                        afb.putString("DetailData_TYPE", type);
                        afb.putString("DetailData_WAREHOUSE", warehouse);
                        afb.putString("PageData", "AlarmssFragment");
                        asf.setArguments(afb);
                        ft.replace(R.id.frame_container2, asf, "AlarmssFragment");
                        ft.commit();
                        break;
                    default:
                        break;
                }
            }
        });

    }
}
