package com.nti56.scadashow.scadashow.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.nti56.scadashow.scadashow.R;
import com.nti56.scadashow.scadashow.compent.PinchImageView;

import java.io.File;

/**
 * Created by chencheng on 2017/8/14.
 */
public class ImgView extends Activity {

    String filename;
    PinchImageView bImgView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgview);

        Bundle bundle = getIntent().getExtras();
        filename = bundle.getString("filename");
        bImgView = (PinchImageView) findViewById(R.id.big_img);

        File file = new File(filename);
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(filename);
            //将图片显示到ImageView中
            bImgView.setImageBitmap(bm);
            //bImgView.setImage(bm);
        }
    }
}
