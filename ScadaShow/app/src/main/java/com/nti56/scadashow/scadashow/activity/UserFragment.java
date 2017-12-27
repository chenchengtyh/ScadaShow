package com.nti56.scadashow.scadashow.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.interfaces.ScadaApplication;

/**
 * Created by chencheng on 2017/7/27.
 */
public class UserFragment extends Fragment {

    private String data;
    private Button ins_btns;
    private Button exit_login;
    private LinearLayout clearcache;
    private TextView cache;
    private LinearLayout version_go;
    ScadaApplication sapps;
    private TextView version_id;
    private final int CLEAN_SUC = 1001;
    private final int CLEAN_FAIL = 1002;
    private final int SET_CACHE = 1003;

    private TextView username;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        data = getArguments().getString("PageData");
        sapps = (ScadaApplication) getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container,
                false);
        ins_btns = (Button) view.findViewById(R.id.ins_btns);
        version_id = (TextView) view.findViewById(R.id.version_id);
        cache = (TextView) view.findViewById(R.id.cache);
        clearcache = (LinearLayout) view.findViewById(R.id.clearcache);
        version_go = (LinearLayout) view.findViewById(R.id.version_go);
        exit_login = (Button) view.findViewById(R.id.exit_login);
        ins_btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(getActivity(), InspectinspectActivity.class);
                startActivity(it);
            }
        });

        clearcache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCleanCache();
            }
        });

        version_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(getActivity(), VersionDetailActivity.class);
                startActivity(it);
            }
        });

        exit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setClass(getActivity(), LoginActivity.class);
                startActivity(it);
                getActivity().finish();
            }
        });

        version_id.setText(sapps.GetVersionDetail().split("&")[sapps.GetVersionDetail().split("&").length - 1].split("\\|")[0]);
        username = (TextView) view.findViewById(R.id.username);
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
        username.setText(mSharedPreferences.getString("name", ""));
        return view;
    }

    private void onClickCleanCache() {
        getConfirmDialog(getActivity(), "是否清空缓存?", new DialogInterface.OnClickListener
                () {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //clearAppCache();
                //tvCache.setText("0KB");
                SharedPreferences mSharedPreferences = getActivity().getSharedPreferences("ServiceUrl", Context.MODE_PRIVATE);
                mSharedPreferences.edit().clear().commit();
                mSharedPreferences = getActivity().getSharedPreferences("loginUser", Context.MODE_PRIVATE);
                mSharedPreferences.edit().clear().commit();
                mSharedPreferences = getActivity().getSharedPreferences("FreshAlarmData", Context.MODE_PRIVATE);
                mSharedPreferences.edit().clear().commit();
                Message msg = new Message();
                msg.what = CLEAN_SUC;
                handler.sendMessage(msg);
            }
        }).show();
    }

    public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = getDialog(context);
        builder.setIcon(R.drawable.icons_daniel);
        builder.setMessage(message);
        builder.setPositiveButton("确定", onClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }

    public static AlertDialog.Builder getDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder;
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CLEAN_FAIL:
                    Toast.makeText(getActivity(), "清除失败", Toast.LENGTH_SHORT).show();
                    break;
                case CLEAN_SUC:
                    Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };
}
