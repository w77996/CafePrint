package com.w77996.cafeprint;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.w77996.cafeprint.utils.GlideImageLoader;
import com.w77996.cafeprint.utils.MyMatrixImg;
import com.w77996.cafeprint.utils.Utils;
import com.w77996.cafeprint.view.CircleRelativeLayout;
import com.w77996.cafeprint.view.CustomViews;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener{

    Button mPhoto;
    //CustomViews mCustomViews;
    MyMatrixImg mMyMatrixImg;
    CircleRelativeLayout mview;
    int mWidth;
    private ImagePicker imagePicker;

    public static final int REQUEST_CODE_SELECT = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClick();
        initImageView();
    }

    private void initClick() {
        mPhoto.setOnClickListener(this);
    }

    private void initView() {
        mview = new CircleRelativeLayout(getApplicationContext());
        mview = (CircleRelativeLayout)findViewById(R.id.cl);
       // mview.set
       // mMyMatrixImg = new MyMatrixImg(getApplicationContext());
       // mview.addView(mMyMatrixImg);
        mPhoto = (Button)findViewById(R.id.btn_photo);
        mWidth =  Utils.getDisplayWidth(this);
        //mview.setLayoutParams(new ReL.LayoutParams(mWidth, mWidth));
       // mCustomViews = (CustomViews) findViewById(R.id.cv);
    }

    private void initImageView(){
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(mWidth/2);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(mWidth/2);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
       /* imagePicker.setOutPutX(mWidth);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(mWidth);//保存文件的高度。单位像素*/
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_photo:
                Intent intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.d("MainActivity",images.size()+" " +images.get(0).path+images.get(0).name);
               // Bitmap bm = BitmapFactory.decodeFile(images.get(0).path+images.get(0).name);
              //  mMyMatrixImg.setImageBitmap(bm);
               /* MyAdapter adapter = new MyAdapter(images);
                gridView.setAdapter(adapter);*/
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
