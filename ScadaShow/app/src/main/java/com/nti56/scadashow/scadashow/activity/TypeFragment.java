package com.nti56.scadashow.scadashow.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.adapter.TypeDetailsAdapter;
import com.nti56.scadashow.scadashow.adapter.UnFaultAdapter;
import com.nti56.scadashow.scadashow.interfaces.ScadaApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.nti56.scadashow.scadashow.R.id.gridviews;
import static com.nti56.scadashow.scadashow.R.id.img_pic;

/**
 * Created by chencheng on 2017/8/9.
 */
public class TypeFragment extends Fragment {

    ListView type_detail_listview;
    ScadaApplication scadaApplication;
    private String datas;
    private ImageView type_bg;
    private ImageView type_bg_nopic;
    private String type;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle1 = getArguments();
        type = bundle1.getString("PageData");
        scadaApplication = (ScadaApplication) getActivity().getApplication();
        datas = scadaApplication.getInfo(type);
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 1:
                    File file = new File((String) msg.obj);
                    if (file.exists()) {
                        Bitmap bm = BitmapFactory.decodeFile((String) msg.obj);
                        type_bg.setVisibility(View.VISIBLE);
                        type_bg_nopic.setVisibility(View.GONE);
                        //将图片显示到ImageView中
                        type_bg.setImageBitmap(bm);

                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_details, container,
                false);

        type_detail_listview = (ListView) view.findViewById(R.id.type_detail_listview);
        TypeDetailsAdapter typeDetailsAdapter = new TypeDetailsAdapter(getActivity(), datas);
        type_detail_listview.setAdapter(typeDetailsAdapter);
        type_detail_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getActivity(), datas.split("&")[position].split("\\|")[0] + ":" + datas.split("&")[position].split("\\|")[1], Toast.LENGTH_SHORT).show();

            }
        });
        type_bg = (ImageView) view.findViewById(R.id.type_bg);
        type_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
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
                                .getExternalStorageDirectory(), "/scadatype.jpg"));
                        // 指定照片保存路径（SD卡），workupload.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(cameraIntent, 1);
                    }
                }

*/
            }
        });

        //动态获取屏幕高度
        WindowManager manager = getActivity().getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;
        LinearLayout.LayoutParams para;
        para = (LinearLayout.LayoutParams) type_bg.getLayoutParams();
        para.height = height/3;
        type_bg.setLayoutParams(para);//动态设置显示宽度

        type_bg_nopic = (ImageView) view.findViewById(R.id.type_bg_nopic);
        type_bg_nopic.setOnClickListener(new View.OnClickListener() {
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
                                .getExternalStorageDirectory(), "/scadatype.jpg"));
                        // 指定照片保存路径（SD卡），workupload.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(cameraIntent, 1);
                    }
                }


            }
        });
//        if(type.equals("入库分拣系统")){
//            type_bg.setVisibility(View.VISIBLE);
//            type_bg_nopic.setVisibility(View.GONE);
//            type_bg.setBackgroundResource(R.drawable.rc_rkfjxt);
//        }else if(type.equals("出库分拣系统")){
//            type_bg.setVisibility(View.VISIBLE);
//            type_bg_nopic.setVisibility(View.GONE);
//            type_bg.setBackgroundResource(R.drawable.rc_ckfjxt);
//        }else{
//            type_bg.setVisibility(View.GONE);
//            type_bg_nopic.setVisibility(View.VISIBLE);
//        }

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
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + "_type.jpg";
            //Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转换为图片格式
            //Bitmap bitmap = (Bitmap) bundle.get("data");

            String path = Environment.getExternalStorageDirectory() + "/scadatype.jpg";
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
                b = new FileOutputStream(fileName + name);
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
            msg.obj = fileName + name;
            mHandler.sendMessage(msg);
        }
    }
}
