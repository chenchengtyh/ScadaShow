package com.nti56.scadashow.scadashow.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.adapter.UnFaultAdapter;
import com.nti56.scadashow.scadashow.utils.LogcatHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.R.attr.bitmap;
import static android.R.attr.path;

/**
 * Created by chencheng on 2017/8/8.
 */
public class UnFaultFragment extends Fragment {

    private Button pics;
    private GridView gridviews;
    private ArrayList paths = new ArrayList();

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    paths.add(msg.obj);
                    UnFaultAdapter unFaultAdapter = new UnFaultAdapter(getActivity(), paths);
                    gridviews.setAdapter(unFaultAdapter);
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unfault, container,
                false);
        gridviews = (GridView) view.findViewById(R.id.gridviews);
        pics = (Button) view.findViewById(R.id.pics);
        pics.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                            //申请权限，REQUEST_TAKE_PHOTO_PERMISSION是自定义的常量
                                            ActivityCompat.requestPermissions(getActivity(),
                                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                    1);
                                        } else {
                                            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 222);
                                            } else {
                                                //LogcatHelper.getInstance(getActivity()).start();
                                                //有权限，直接拍照
                                                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                                Uri imageUri = Uri.fromFile(new File(Environment
                                                        .getExternalStorageDirectory(), "/scadashow.jpg"));
                                                // 指定照片保存路径（SD卡），workupload.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                                startActivityForResult(cameraIntent, 1);
                                            }
                                        }
                                    }
                                }
        );
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 222);
                    } else {
                        //申请成功，可以拍照
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri imageUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "/scadashow.jpg"));
                        // 指定照片保存路径（SD卡），workupload.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(cameraIntent, 1);
                    }
                }
                break;
            case 222:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //申请成功，可以拍照
                    //LogcatHelper.getInstance(getActivity()).start();
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri imageUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "/scadashow.jpg"));
                    // 指定照片保存路径（SD卡），workupload.jpg为一个临时文件，每次拍照后这个图片都会被替换
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(cameraIntent, 1);
                }
                break;

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String sdState = Environment.getExternalStorageState();
            if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            new DateFormat();
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            //Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转换为图片格式
            //Bitmap bitmap = (Bitmap) bundle.get("data");

            String path = Environment.getExternalStorageDirectory() + "/scadashow.jpg";
            InputStream is = null;
            try {
                is = new FileInputStream(path);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inTempStorage = new byte[100 * 1024];
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            opts.inSampleSize = 4;
            Bitmap camorabitmap = BitmapFactory.decodeStream(is, null, opts);

            FileOutputStream b = null;

            String fileName = Environment.getExternalStorageDirectory() + "/scadashow/";

            try {
                File f = new File(fileName);
                f.mkdirs();
                f = new File(fileName, name);
                f.createNewFile();
                b = new FileOutputStream(fileName+name);
                camorabitmap.compress(Bitmap.CompressFormat.PNG, 100, b);// 把数据写入文件
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

            //显示图片
            Message msg = new Message();
            msg.what = 1;
            msg.obj = fileName+name;
            mHandler.sendMessage(msg);
        }
    }
}
