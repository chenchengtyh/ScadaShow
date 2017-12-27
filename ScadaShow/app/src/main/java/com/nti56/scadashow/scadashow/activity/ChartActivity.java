package com.nti56.scadashow.scadashow.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.bean.AlarmsPoint;
import com.nti56.scadashow.scadashow.bean.AlarmsStatic;
import com.nti56.scadashow.scadashow.utils.LinkWebService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static android.R.attr.value;
import static com.nti56.scadashow.scadashow.R.id.order_pic;


/**
 * Created by chencheng on 2017/8/17.
 */
public class ChartActivity extends AppCompatActivity {

    LineChart chart;
    Button chart_bt1;
    private int mYear;
    private int mMonth;
    private int mDay;
    TextView current_date;
    ImageView date_left;
    ImageView date_right;

    private String warehouse;
    private String type;
    private String statisticstype;
    private LinkWebService linkWebService;
    private TextView title2;

    private LinkedList<AlarmsPoint> alarmpointLinkedList;
    public Gson gson = new Gson();


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    LineData data = getLineData(alarmpointLinkedList.size(), 100);
                    showChart(chart, data, Color.rgb(255, 255, 255));
                    chart.invalidate();
                    break;
                case 2:
                    break;
                default:
                    break;
            }

        }
    };

    private String ChangeType(String type) {

        String type_zh = "";

        if (type.indexOf("机器人") >= 0) {
            type_zh = "AGV";
        } else if (type.indexOf("堆垛机") >= 0) {
            type_zh = "SC";
        } else if (type.indexOf("输送机") >= 0) {
            type_zh = "STA";
        } else if (type.indexOf("成品入库分拣") >= 0) {
            type_zh = "CPINFJ";
        } else if (type.indexOf("升降机") >= 0) {
            type_zh = "SSJ";
        } else if (type.indexOf("机械手") >= 0) {
            type_zh = "ROBOT";
        } else if (type.indexOf("夹抱机") >= 0) {
            type_zh = "JBJ";
        } else if (type.indexOf("入库输送机") >= 0) {
            type_zh = "INSTA";
        } else if (type.indexOf("拆盘机") >= 0) {
            type_zh = "CPJ";
        } else if (type.indexOf("穿梭车") >= 0) {
            type_zh = "CSC";
        } else if (type.indexOf("码盘机") >= 0) {
            type_zh = "MPJ";
        } else if (type.indexOf("出库输送机") >= 0) {
            type_zh = "OUTSTA";
        }

        return type_zh;

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);

        title2 = (TextView) findViewById(R.id.title2);

        Bundle mBandler = getIntent().getExtras();
        warehouse = mBandler.getString("DetailData_WAREHOUSE");
        type = ChangeType(mBandler.getString("DetailData_TYPE"));
        statisticstype = mBandler.getString("Statistics_TYPE");
        linkWebService = new LinkWebService();

        title2.setText("( " + warehouse + "->" + mBandler.getString("DetailData_TYPE") + "->" + mBandler.getString("Select_TYPE") + " )折线图");

        current_date = (TextView) findViewById(R.id.current_date);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        current_date.setText(new StringBuilder()
                .append(mYear)
                .append("-")
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
                        : (mMonth + 1)).append("-")
                .append((mDay < 10) ? "0" + mDay : mDay));

        date_left = (ImageView) findViewById(R.id.date_left);
        date_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_left = Calendar.getInstance();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = df.parse(current_date.getText().toString());

                    c_left.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c_left.add(Calendar.DAY_OF_MONTH, -1);//-1今天的时间加一天
                mYear = c_left.get(Calendar.YEAR);
                mMonth = c_left.get(Calendar.MONTH);
                mDay = c_left.get(Calendar.DAY_OF_MONTH);

                current_date.setText(new StringBuilder()
                        .append(mYear)
                        .append("-")
                        .append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
                                : (mMonth + 1)).append("-")
                        .append((mDay < 10) ? "0" + mDay : mDay));

                GetDataFromService(mYear + "-" + ((mMonth + 1) < 10 ? "0" + (mMonth + 1)
                        : (mMonth + 1)) + "-" + mDay);

//                LineData data = getLineData(24, 200);
//                showChart(chart, data, Color.rgb(255, 255, 255));
//                chart.invalidate();

            }
        });
        date_right = (ImageView) findViewById(R.id.date_right);
        date_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c_right = Calendar.getInstance();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = df.parse(current_date.getText().toString());

                    c_right.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                c_right.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
                mYear = c_right.get(Calendar.YEAR);
                mMonth = c_right.get(Calendar.MONTH);
                mDay = c_right.get(Calendar.DAY_OF_MONTH);


                current_date.setText(new StringBuilder()
                        .append(mYear)
                        .append("-")
                        .append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
                                : (mMonth + 1)).append("-")
                        .append((mDay < 10) ? "0" + mDay : mDay));

                GetDataFromService(mYear + "-" + ((mMonth + 1) < 10 ? "0" + (mMonth + 1)
                        : (mMonth + 1)) + "-" + mDay);
//                LineData data = getLineData(24, 300);
//                showChart(chart, data, Color.rgb(255, 255, 255));
//                chart.invalidate();

            }
        });

        chart_bt1 = (Button) findViewById(R.id.chart_bt1);
        chart_bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(ChartActivity.this, benDateSetListener, mYear,
                        mMonth, mDay).show();
            }
        });

        Date dt = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");//获取当前日期
        GetDataFromService(sd.format(dt));

        chart = (LineChart) findViewById(R.id.chart);

        //动态获取屏幕高度
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;
        LinearLayout.LayoutParams para;
        para = (LinearLayout.LayoutParams) chart.getLayoutParams();
        para.height = height / 2;
        chart.setLayoutParams(para);//动态设置显示宽度

    }

    private void GetDataFromService(final String time) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                SharedPreferences mSharedPreferences = getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                // 要访问的网址
                String wsdl = mSharedPreferences.getString("service_url", "");
                // webservice 的功能名称
                String method = "getRuntime";
                // 设置传入参数


                Map<String, Object> params = new HashMap<>();

                if (warehouse.equals("") || type.equals("") || statisticstype.equals("")) {
                    return;
                }

                String jsons = "[{\"WAREHOUSEID\":" + warehouse + ",\"EQUIPMENTTYPE\":" + type + ",\"statisticstype\":" + statisticstype + ",\"t_date\":" + time + "}]";
                params.put("qrCode", jsons);
                try {
                    String obj = linkWebService.LinkSoapWebservice(wsdl, method, params);
                    if (!obj.equals("")) {
                        obj = obj.replace("{\"test\":", "");
                        obj = obj.substring(0, obj.length() - 1);
                        if (obj.equals("]")) {
                            return;
                        }
                        //obj = "[{\"TOTALFAULTTIME\":\"48\",\"T_DATE\":\"2017/11/1 0:00:00\"},{\"TOTALFAULTTIME\":\"24\",\"T_DATE\":\"2017/11/2 0:00:00\"},{\"TOTALFAULTTIME\":\"48\",\"T_DATE\":\"2017/11/3 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/4 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/5 0:00:00\"},{\"TOTALFAULTTIME\":\"8\",\"T_DATE\":\"2017/11/6 0:00:00\"},{\"TOTALFAULTTIME\":\"\",\"T_DATE\":\"2017/11/7 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/8 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/9 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/10 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/11 0:00:00\"},{\"TOTALFAULTTIME\":\"4\",\"T_DATE\":\"2017/11/12 0:00:00\"}]";
                        alarmpointLinkedList = new LinkedList<>();
                        Type type = new TypeToken<AlarmsPoint>() {
                        }.getType();
                        JSONArray arr = new JSONArray(obj);
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject temp = (JSONObject) arr.get(i); // 传入字符串
                            AlarmsPoint alarmspointInstance = gson
                                    .fromJson(temp.toString(), type);
                            if (!alarmpointLinkedList.contains(alarmspointInstance)) {
                                alarmpointLinkedList.addLast(alarmspointInstance);
                            }
                        }
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

    private void showChart(LineChart chart, LineData data, int color) {

        chart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面
        chart.setDrawBorders(false);//是否在折线图上添加边框
        chart.setDescription("月/日");//数据描述
        chart.setNoDataTextDescription("你需要为图表提供数据");
        chart.setDrawGridBackground(false);//是否显示表格颜色
        chart.setGridBackgroundColor(R.color.colorPrimaryDark);//表格颜色
        chart.setTouchEnabled(true);
        chart.setScaleEnabled(true);
        chart.setDragEnabled(true);

        chart.setPinchZoom(false);
        chart.setBackgroundColor(color);
        chart.setData(data);
        Legend mLegend = chart.getLegend();//设置比例图标

        mLegend.setForm(Legend.LegendForm.CIRCLE);
        mLegend.setFormSize(10);
        mLegend.setTextColor(Color.BLACK);
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        chart.animateX(1000);//设置x轴执行动画
    }

    private LineData getLineData(int count, int range) {
        int colors[] = new int[count];
        ArrayList<String> xValues = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            xValues.add(alarmpointLinkedList.get(i).GetT_DATE().replace("0:00:00", "").replace("2017/", ""));//x轴的坐标数据
            colors[i] = Color.rgb(76, 174, 80);
        }

        ArrayList<Entry> yValues = new ArrayList<Entry>();//y轴的数据
        for (int i = 0; i < count; i++) {
            //float value = (float) (Math.random() * range) + 3;//随机给一个
            float value = Float.parseFloat(alarmpointLinkedList.get(i).GetTOTALFAULTTIME().equals("") ? "0" : alarmpointLinkedList.get(i).GetTOTALFAULTTIME());
            yValues.add(new Entry(value, i));
        }

        LineDataSet mLineDataSet = new LineDataSet(yValues, "监视数据( 万箱/日 )");

        mLineDataSet.setValueTextSize(10);
        mLineDataSet.setCircleColorHole(Color.rgb(76, 174, 80));
        mLineDataSet.setLineWidth(3);
        mLineDataSet.setCircleSize(5);
        mLineDataSet.setColor(Color.rgb(76, 174, 80));
        mLineDataSet.setCircleColors(colors);
        mLineDataSet.setHighLightColor(Color.rgb(76, 174, 80));

//		mLineDataSet.setDrawCircles(false);
//		mLineDataSet.setDrawCubic(true);
//		mLineDataSet.setCubicIntensity(0.6f);
//		mLineDataSet.setDrawFilled(true);
//		mLineDataSet.setFillColor(Color.rgb(0, 255, 255));

        ArrayList<LineDataSet> mLineDataSets = new ArrayList<>();
        mLineDataSets.add(mLineDataSet);

        LineData data = new LineData(xValues, mLineDataSets);
        return data;

    }

    private DatePickerDialog.OnDateSetListener benDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateTime();
        }

        private void updateTime() {
            // TODO Auto-generated method stub
            current_date.setText(new StringBuilder()
                    .append(mYear)
                    .append("-")
                    .append((mMonth + 1) < 10 ? "0" + (mMonth + 1)
                            : (mMonth + 1)).append("-")
                    .append((mDay < 10) ? "0" + mDay : mDay));

            GetDataFromService(mYear + "-" + ((mMonth + 1) < 10 ? "0" + (mMonth + 1)
                    : (mMonth + 1)) + "-" + mDay);
//            LineData data = getLineData(24, 100);
//            showChart(chart, data, Color.rgb(255, 255, 255));
//            chart.invalidate();
        }
    };
}
