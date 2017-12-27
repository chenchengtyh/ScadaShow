package com.nti56.scadashow.scadashow.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.bean.Alarm;
import com.nti56.scadashow.scadashow.bean.AlarmPerson;
import com.nti56.scadashow.scadashow.utils.LinkWebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class AlarmDetailActivity extends AppCompatActivity {

    Alarm alarm;
    private TextView text_title;
    private Button submit;
    private Button backs;
    private TextView warehourse_type;
    private TextView stationno;
    private TextView errorcode;
    private TextView errorstring;
    private TextView errortime;
    private TextView taskno;
    private TextView fromsta;
    private TextView tosta;
    private TextView method;
    private TextView error_time;
    private TextView alarm_user;
    private TextView errorstringeng;
    private Button alarm_ignore;
    private LinkWebService linkWebService;
    private boolean already_ignore = false;
    private String content;
    private LinkedList<AlarmPerson> alarmPersonLinkedList;
    public Gson gson = new Gson();
    private String wsdl;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    Toast.makeText(AlarmDetailActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    already_ignore = true;
                    finish();
                    break;
                case 2:
                    Toast.makeText(AlarmDetailActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlarmDetailActivity.this)
                            .setTitle("SCADA数据监控平台")
                            .setIcon(R.drawable.icons_daniel)
                            .setCancelable(false)
                            .setMessage("您刚刚已提交成功，无法再次提交")
                            .setPositiveButton("确定", null);
                    builder.show();
                    break;
                case 4:
                    if (CheckSubmitPerson()) {
                        final EditText inputSubmit = new EditText(AlarmDetailActivity.this);
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(AlarmDetailActivity.this);
                        builder2.setTitle("请填写故障处理方法").setIcon(R.drawable.icons_daniel).setView(inputSubmit)
                                .setNegativeButton("取消", null);
                        builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                AlarmSubmit(inputSubmit.getText().toString().trim());
                            }
                        });
                        builder2.show();
                    }
                    break;
                case 5:
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(AlarmDetailActivity.this)
                            .setTitle("SCADA数据监控平台")
                            .setIcon(R.drawable.icons_daniel)
                            .setCancelable(false)
                            .setMessage("提交成功,您已经是第一处理人，请及时处理警报")
                            .setPositiveButton("确定", null);
                    builder3.show();
                    break;
                default:
                    break;

            }
        }
    };

    private boolean CheckSubmitPerson() {

        SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        final String user_name = sp.getString("user_name", "");
        final String person = alarmPersonLinkedList.get(0).GetPerson();
        if (user_name.equals("")) {
            return false;
        } else {
            if (alarmPersonLinkedList.get(0).GetSTATUS().equals("3")) {

                if (user_name.equals(person)) {
                    return true;
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlarmDetailActivity.this)
                            .setTitle("SCADA数据监控平台")
                            .setIcon(R.drawable.icons_daniel)
                            .setCancelable(false)
                            .setMessage("提醒: ( " + person + " )员工在处理中")
                            .setPositiveButton("确定", null);
                    builder.show();
                    return false;
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlarmDetailActivity.this)
                        .setTitle("SCADA数据监控平台")
                        .setIcon(R.drawable.icons_daniel)
                        .setCancelable(false)
                        .setMessage("故障目前无处理人，是否成为第一个处理人?")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int arg1) {
                                        // TODO Auto-generated method stub
                                        SubmitFirst(user_name);
                                    }

                                }).setNegativeButton("取消", null);
                builder.show();
                return false;
            }
        }

    }

    private void SubmitFirst(final String user_name) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                // webservice 的功能名称
                String method = "SubmitPerson";
                // 设置传入参数
                Map<String, Object> params = new HashMap<>();
                String jsons = "[{\"ID\":" + alarm.GetID() + ",\"person\":" + user_name + ",\"status\":3}]";
                params.put("qrCode", jsons);

                try {
                    String obj = linkWebService.LinkSoapWebservice(wsdl, method, params);
                    if (!obj.equals("")) {
                        if (obj.equals("888")) {
                            Message msg = new Message();
                            msg.what = 5;
                            mHandler.sendMessage(msg);
                        } else {
                            Message msg = new Message();
                            msg.what = 2;
                            mHandler.sendMessage(msg);
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_detail);

        Bundle bundle = getIntent().getExtras();
        alarm = (Alarm) bundle.getSerializable("DetailData");

        linkWebService = new LinkWebService();
        SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
        // 要访问的网址
        wsdl = mSharedPreferences.getString("service_url", "");

        warehourse_type = (TextView) findViewById(R.id.warehourse_type);
        errorcode = (TextView) findViewById(R.id.errorcode);
        stationno = (TextView) findViewById(R.id.stationno);
        errorstring = (TextView) findViewById(R.id.errorstring);
        errortime = (TextView) findViewById(R.id.errortime);
        taskno = (TextView) findViewById(R.id.taskno);
        fromsta = (TextView) findViewById(R.id.fromsta);
        tosta = (TextView) findViewById(R.id.tosta);
        method = (TextView) findViewById(R.id.method);
        error_time = (TextView) findViewById(R.id.error_time);
        alarm_user = (TextView) findViewById(R.id.alarm_user);
        alarm_ignore = (Button) findViewById(R.id.alarm_ignore);
        errorstringeng = (TextView) findViewById(R.id.errorstringeng);

        warehourse_type.setText(alarm.GetWAREHOUSENAME() + "-->" + Changes(alarm.GetTYPE()));
        errorcode.setText(alarm.GetERRORCODE());
        stationno.setText(alarm.GetSTATIONNO());
        errorstring.setText(alarm.GetERRORSTRING());
        error_time.setText(alarm.GetERRORTIME());
        errortime.setText(alarm.GetERRORTIME());
        taskno.setText(alarm.GetTASKNO());
        fromsta.setText(alarm.GetFROMSTA());
        tosta.setText(alarm.GetTOSTA());
        method.setText(alarm.GetMETHOD());
        alarm_user.setText(alarm.GetUSERNAME());
        errorstringeng.setText(alarm.GetSTRINGENG());

        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("报警详情");
        backs = (Button) findViewById(R.id.backs);
        backs.setVisibility(View.VISIBLE);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetPerson(alarm.GetID());

            }
        });

        alarm_ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (already_ignore) {
                    Message msg = new Message();
                    msg.what = 3;
                    mHandler.sendMessage(msg);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AlarmDetailActivity.this)
                            .setTitle("SCADA数据监控平台")
                            .setIcon(R.drawable.icons_daniel)
                            .setCancelable(false)
                            .setMessage("确定忽略警报吗？")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int arg1) {
                                            // TODO Auto-generated method stub
                                            AlarmSubmit(null);
                                        }

                                    }).setNegativeButton("取消", null);
                    builder.show();
                }
            }
        });


    }

    private void GetPerson(final String ID) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                // webservice 的功能名称
                String method = "GetAlarm";
                // 设置传入参数
                Map<String, Object> params = new HashMap<>();
                String jsons = "[{\"mac\":,\"appflag\":,\"ID\":" + ID + "}]";
                params.put("qrCode", jsons);

                try {
                    String obj = linkWebService.LinkSoapWebservice(wsdl, method, params);
                    if (!obj.equals("")) {
                        obj = obj.replace("{\"test\":", "");
                        obj = obj.substring(0, obj.length() - 1);
                        alarmPersonLinkedList = new LinkedList<>();
                        java.lang.reflect.Type type = new TypeToken<AlarmPerson>() {
                        }.getType();
                        JSONArray arr = new JSONArray(obj);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject temp = (JSONObject) arr.get(i); // 传入字符串
                            AlarmPerson alarmInstance = gson
                                    .fromJson(temp.toString(), type);
                            if (!alarmPersonLinkedList.contains(alarmInstance)) {
                                alarmPersonLinkedList.addLast(alarmInstance);
                            }
                        }
                        Message msg = new Message();
                        msg.what = 4;
                        mHandler.sendMessage(msg);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void AlarmSubmit(final String content) {

        this.content = content;

        new Thread(new Runnable() {
            @Override
            public void run() {

                // webservice 的功能名称
                String method = "Submit";

                SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                String user_name = sp.getString("user_name", "");

                if (wsdl.equals("") || user_name.equals("")) {
                    return;
                }

                // 设置传入参数
                Map<String, Object> params = new HashMap<>();
                String jsons = "[{\"ID\":" + alarm.GetID() + ",\"equipmentID\":123,\"content\":" + content + ",\"user\":" + user_name + ",\"code\":123}]";
                params.put("qrCode", jsons);
                try {
                    String obj = linkWebService.LinkSoapWebservice(wsdl, method, params);
                    //Log.d("jochen","AlarmDetailActivity---------"+obj);
                    if (obj.equals("888")) {
                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 2;
                        mHandler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private String Changes(String s) {

        String type;

        if (s.equals("SC")) {
            type = "堆垛机";
        } else if (s.equals("AGV")) {
            type = "AGV";
        } else if (s.equals("STA")) {
            type = "输送机";
        } else if (s.equals("CSC")) {
            type = "穿梭车";
        } else if (s.equals("JBJ")) {
            type = "夹抱机";
        } else if (s.equals("ROBOT")) {
            type = "机械手";
        } else if (s.equals("MPJ")) {
            type = "码盘机";
        } else if (s.equals("CPINFJ")) {
            type = "成品入库分拣";
        } else if (s.equals("SJJ")) {
            type = "升降机";
        } else if (s.equals("OUTSTA")) {
            type = "出库输送机";
        } else if (s.equals("INSTA")) {
            type = "入库输送机";
        } else {
            type = "拆盘机";
        }

        return type;

    }
}
