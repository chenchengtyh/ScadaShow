package com.nti56.scadashow.scadashow.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.bean.OrderInfo;
import com.nti56.scadashow.scadashow.utils.LinkWebService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.nti56.scadashow.scadashow.R.id.type_bg;

/**
 * Created by chencheng on 2017/8/15.
 */

public class OrderDetailActivity extends AppCompatActivity {

    private TextView title;
    private Button backs;
    private Button submits;
    private Button assign_men;
    private ArrayList<String> choice_name;
    private OrderInfo orderInfo;

    private TextView orderstatus;
    private TextView warehourse_type;
    private TextView creattime2;
    private TextView creattime;
    private TextView audittime;
    private TextView endtime;
    private TextView warehourseid;
    private TextView equipmentcode;
    private TextView equipmenttype;
    private TextView ordernumber;
    //private TextView fixpartid;
    private TextView orderdescption;
    private TextView order_type;
    private TextView submitperson;
    private TextView auditperson;
    private TextView executeperson;
    private ImageView order_pic;

    private LinkWebService linkWebService;
    String choice_order_name = "";
    private int checkAuthority = 0;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this)
                            .setTitle("SCADA数据监控平台")
                            .setIcon(R.drawable.icons_daniel)
                            .setCancelable(false)
                            .setMessage("提交成功")
                            .setPositiveButton("确定", null);
                    builder.show();
                    break;
                case 2:
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(OrderDetailActivity.this)
                            .setTitle("SCADA数据监控平台")
                            .setIcon(R.drawable.icons_daniel)
                            .setCancelable(false)
                            .setMessage("提交失败，请检查原因")
                            .setPositiveButton("确定", null);
                    builder2.show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        title = (TextView) findViewById(R.id.text_title);
        backs = (Button) findViewById(R.id.backs);
        submits = (Button) findViewById(R.id.submits);
        assign_men = (Button) findViewById(R.id.assign_men);

        orderstatus = (TextView) findViewById(R.id.orderstatus);
        warehourse_type = (TextView) findViewById(R.id.warehourse_type);
        creattime = (TextView) findViewById(R.id.creattime);
        creattime2 = (TextView) findViewById(R.id.creattime2);
        audittime = (TextView) findViewById(R.id.audittime);
        endtime = (TextView) findViewById(R.id.endtime);
        warehourseid = (TextView) findViewById(R.id.warehourseid);
        equipmentcode = (TextView) findViewById(R.id.equipmentcode);
        equipmenttype = (TextView) findViewById(R.id.equipmenttype);
        ordernumber = (TextView) findViewById(R.id.ordernumber);
        //fixpartid = (TextView) findViewById(fixpartid);
        orderdescption = (TextView) findViewById(R.id.orderdescption);
        order_type = (TextView) findViewById(R.id.order_type);
        submitperson = (TextView) findViewById(R.id.submitperson);
        auditperson = (TextView) findViewById(R.id.auditperson);
        executeperson = (TextView) findViewById(R.id.executeperson);
        order_pic = (ImageView) findViewById(R.id.order_pic);

        //动态获取屏幕高度
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;
        LinearLayout.LayoutParams para;
        para = (LinearLayout.LayoutParams) order_pic.getLayoutParams();
        para.height = height / 3;
        order_pic.setLayoutParams(para);//动态设置显示宽度

        Bundle bundle = getIntent().getExtras();
        orderInfo = (OrderInfo) bundle.getSerializable("OrderDetail");
        if (!orderInfo.GetORDERSTATUS().equals("新建")) {
            assign_men.setVisibility(View.GONE);
            submits.setVisibility(View.GONE);
        } else {
            assign_men.setVisibility(View.VISIBLE);
            submits.setVisibility(View.VISIBLE);
            checkAuthority();
        }
        orderstatus.setText(orderInfo.GetORDERSTATUS());
        warehourse_type.setText(orderInfo.GetWAREHOUSEID() + "-->" + Changes(orderInfo.GetEQUIPMENTTYPE()));
        creattime.setText("创建时间:    " + orderInfo.GetCREATEDATE());
        creattime2.setText(orderInfo.GetCREATEDATE());
        audittime.setText("审核时间:    " + orderInfo.GetAUDITTIME());
        endtime.setText("完成时间    " + orderInfo.GetENDTIME());
        warehourseid.setText("仓库ID:    " + orderInfo.GetWAREHOUSEID());
        equipmentcode.setText("设备编号:    " + orderInfo.GetEQUIPMENTCODE());
        equipmenttype.setText("设备类型:    " + orderInfo.GetEQUIPMENTTYPE());
        ordernumber.setText("维保工单号:    " + orderInfo.GetORDERNUMBER());
        //fixpartid.setText("维保部件ID:    " + orderInfo.GetFIXPARTID());
        orderdescption.setText(orderInfo.GetODERDESCRIPTION().replace("     ", ""));
        order_type.setText("工单类型:    " + orderInfo.GetORDERTYPE());
        submitperson.setText("上传人 :    " + orderInfo.GetCREATEUSER());
        auditperson.setText("审核人 :    " + orderInfo.GetAUDITPERSON());
        executeperson.setText("执行人 :    " + orderInfo.GetEXECUTEPERSON());

        choice_name = new ArrayList<>();
        title.setText("工单详情");
        backs.setVisibility(View.VISIBLE);
        backs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                //String user_name = sp.getString("user_name", "");//如果取不到值就取后面的""
                if (1 == checkAuthority) {
                    if (choice_order_name.equals("")) {
                        //Toast.makeText(OrderDetailActivity.this, "您未选择执行人", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this)
                                .setTitle("SCADA数据监控平台")
                                .setIcon(R.drawable.icons_daniel)
                                .setCancelable(false)
                                .setMessage("您未选择执行人,您可以选择忽略此工单，或者从新选择执行人")
                                .setPositiveButton("忽略工单", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        choice_order_name = ",";
                                        SubmitOrder();
                                    }
                                })
                                .setNegativeButton("去选择执行人", null);
                        builder.show();

                    } else {
                        SubmitOrder();
                    }

                } else {
                    Toast.makeText(OrderDetailActivity.this, "您不是管理员，无法指派人员", Toast.LENGTH_SHORT).show();
                }

            }
        });
        assign_men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                //String user_name = sp.getString("user_name", "");//如果取不到值就取后面的""
                if (1 != checkAuthority) {
                    Toast.makeText(OrderDetailActivity.this, "您不是管理员，无法指派人员", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
                builder.setIcon(R.drawable.icons_daniel);
                builder.setTitle("选择指派人员");
                final String[] names = new String[orderInfo.GetALLPERSON().split(",").length];
                for (int i = 0; i < orderInfo.GetALLPERSON().split(",").length; i++) {
                    names[i] = orderInfo.GetALLPERSON().split(",")[i];
                }

                boolean[] initUserChoice = SelectInit(names, choice_name);
                //    设置一个单项选择下拉框
                /**
                 * 第一个参数指定我们要显示的一组下拉多选框的数据集合
                 * 第二个参数代表哪几个选项被选择，如果是null，则表示一个都不选择，如果希望指定哪一个多选选项框被选择，
                 * 需要传递一个boolean[]数组进去，其长度要和第一个参数的长度相同，例如 {true, false, false, true};
                 * 第三个参数给每一个多选项绑定一个监听器
                 */
                builder.setMultiChoiceItems(names, initUserChoice, new DialogInterface.OnMultiChoiceClickListener() {
                    //StringBuffer sb = new StringBuffer(100);
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            choice_name.add(names[which]);
                        } else {
                            choice_name.remove(names[which]);
                        }
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        choice_order_name = "";
                        for (int i = 0; i < choice_name.size(); i++) {
                            choice_order_name += choice_name.get(i) + ",";
                        }
                        //Toast.makeText(OrderDetailActivity.this, "人员为：" + sb.toString(), Toast.LENGTH_SHORT).show();
                        if (!choice_order_name.equals("")) {
                            executeperson.setText("执行人: " + choice_order_name.substring(0, choice_order_name.length() - 1));
                            executeperson.setTextColor(OrderDetailActivity.this.getResources().getColor(R.color.reds));
                        } else {
                            executeperson.setText("执行人: ");
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    private void checkAuthority() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                linkWebService = new LinkWebService();
                SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                // 要访问的网址
                String wsdl = mSharedPreferences.getString("service_url", "");

                // webservice 的功能名称
                String method = "checkAuthority";

                SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                String user_name = sp.getString("user_name", "");

                if (wsdl.equals("") || user_name.equals("")) {
                    return;
                }

                // 设置传入参数
                Map<String, Object> params = new HashMap<>();
                String jsons = "[{\"userName\":" + user_name + ",\"authorityName\":下达}]";
                params.put("qrCode", jsons);
                try {
                    String obj = linkWebService.LinkSoapWebservice(wsdl, method, params);
                    //Log.d("jochen","AlarmDetailActivity---------"+obj);
                    if (obj.equals("1")) {
                        checkAuthority = 1;
                    } else {
                        checkAuthority = 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void SubmitOrder() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                linkWebService = new LinkWebService();
                SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                // 要访问的网址
                String wsdl = mSharedPreferences.getString("service_url", "");

                // webservice 的功能名称
                String method = "submitOrder";

                SharedPreferences sp = getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                String user_name = sp.getString("user_name", "");

                if (wsdl.equals("") || user_name.equals("")) {
                    return;
                }

                // 设置传入参数
                Map<String, Object> params = new HashMap<>();
                String jsons = "[{\"ordernumber\":" + orderInfo.GetORDERNUMBER() + ",\"auditperson\":" + user_name + ",\"executeperson\":" + choice_order_name.substring(0, choice_order_name.length() - 1) + "}]";
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

    private boolean[] SelectInit(String[] names, ArrayList<String> choice_name) {

        boolean[] selectInit = new boolean[names.length];
        if (0 == choice_name.size()) {
            return null;
        } else {

            for (int i = 0; i < names.length; i++) {
                selectInit[i] = false;
                for (int j = 0; j < choice_name.size(); j++) {
                    if (names[i].equals(choice_name.get(j))) {
                        selectInit[i] = true;
                    }
                }

            }
            return selectInit;
        }

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
        } else if(s.equals("CPINFJ")){
            type = "成品入库分拣";
        } else if(s.equals("SJJ")){
            type = "升降机";
        } else if(s.equals("OUTSTA")){
            type = "出库输送机";
        } else if(s.equals("INSTA")){
            type = "入库输送机";
        } else {
            type = "拆盘机";
        }

        return type;

    }
}
