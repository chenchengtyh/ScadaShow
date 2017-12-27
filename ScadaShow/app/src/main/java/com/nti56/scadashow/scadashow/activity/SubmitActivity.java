package com.nti56.scadashow.scadashow.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;

public class SubmitActivity extends AppCompatActivity {

    private RadioGroup radioGroupsub;
    private Button typeinfoGroupIDsub;
    private Button typealarmGroupIDsub;
    private TextView title;
    private Button submits;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Button backs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        title = (TextView) findViewById(R.id.text_title);
        fm = getSupportFragmentManager();
        title.setText("故障处理");
        backs = (Button) findViewById(R.id.backs);
        backs.setVisibility(View.VISIBLE);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submits = (Button) findViewById(R.id.submits);
        submits.setVisibility(View.VISIBLE);
        submits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SubmitActivity.this,"您点击了提交",Toast.LENGTH_SHORT).show();
            }
        });

        ft = fm.beginTransaction();
        FaultFragment ff = new FaultFragment();
        Bundle hfb = new Bundle();
        hfb.putString("PageData", "FaultFragment");
        ff.setArguments(hfb);
        ft.replace(R.id.frame_container1, ff, "FaultFragment");
        ft.commit();

        radioGroupsub = (RadioGroup) findViewById(R.id.radioGroupIDsub);
        radioGroupsub.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.typeinfoGroupIDsub:
                        title.setText("故障处理");
                        ft = fm.beginTransaction();
                        FaultFragment ff = new FaultFragment();
                        Bundle hfb = new Bundle();
                        hfb.putString("PageData", "FaultFragment");
                        ff.setArguments(hfb);
                        ft.replace(R.id.frame_container1, ff, "FaultFragment");
                        ft.commit();
                        break;
                    case R.id.typealarmGroupIDsub:
                        title.setText("未知故障");
                        ft = fm.beginTransaction();
                        UnFaultFragment uff = new UnFaultFragment();
                        Bundle afb = new Bundle();
                        afb.putString("PageData", "UnFaultFragment");
                        uff.setArguments(afb);
                        ft.replace(R.id.frame_container1, uff, "UnFaultFragment");
                        ft.commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
