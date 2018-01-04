package com.w77996.cafeprint;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.util.BitmapUtil;
import com.lzy.imagepicker.view.CropImageView;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.w77996.cafeprint.utils.CheckPermissionUtils;
import com.w77996.cafeprint.utils.GlideImageLoader;
import com.w77996.cafeprint.utils.MyMatrixImg;
import com.w77996.cafeprint.utils.PermissionsActivity;
import com.w77996.cafeprint.utils.PermissionsChecker;
import com.w77996.cafeprint.utils.Utils;
import com.w77996.cafeprint.view.CircleRelativeLayout;
import com.w77996.cafeprint.view.CustomViews;
import com.w77996.cafeprint.view.RoundRelativeLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button mPhoto;
    Button mPic;
    Button mInput;
    //CustomViews mCustomViews;
   // private CustomViews mCustomView;
    MyMatrixImg mMyMatrixImg;
   // CircleRelativeLayout mview;
    com.w77996.cafeprint.view.CropImageView mCropImageView;
    int mWidth;
    private ImagePicker imagePicker;
    private ImageView mImage;
    private Bitmap mBitmap;
    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    public static final int REQUEST_CODE_SELECT = 100;

    private static final int PERMISSIONS_REQUEST_CODE = 0; // 请求码

    private PermissionsChecker mPermissionsChecker; // 权限检测器

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ZXingLibrary.initDisplayOpinion(this);
        //初始化权限
        initPermission();
        initView();
        initClick();
        initImageView();
    }

    private void initClick() {
        mPhoto.setOnClickListener(this);
        mPic.setOnClickListener(this);
        mInput.setOnClickListener(this);
    }

    private void initView() {
       // mview = new CircleRelativeLayout(getApplicationContext());
       // mview = (CircleRelativeLayout)findViewById(R.id.cl);
       // mview.set
        mCropImageView = (com.w77996.cafeprint.view.CropImageView)findViewById(R.id.img);
        //mMyMatrixImg = new MyMatrixImg();
      //  mMyMatrixImg = (MyMatrixImg)findViewById(R.id.img) ;

        mImage = (ImageView)findViewById(R.id.img2);
        mInput = (Button)findViewById(R.id.btn_input);
       // RoundRelativeLayout r = (RoundRelativeLayout)findViewById(R.id.rl);
       // mview.addView(mMyMatrixImg);
        mPhoto = (Button)findViewById(R.id.btn_photo);
        mPic = (Button)findViewById(R.id.btn_select_pic);
        mWidth =  Utils.getDisplayWidth(this);
        //mview.setLayoutParams(new ReL.LayoutParams(mWidth, mWidth));
       // mCustomView = (CustomViews) findViewById(R.id.cv);
    }

    private void initImageView(){
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(mWidth);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(mWidth);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
       /* imagePicker.setOutPutX(mWidth);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(mWidth);//保存文件的高度。单位像素*/
       // mCropImageView.setFocusStyle(imagePicker.getStyle());
        mCropImageView.setFocusWidth(imagePicker.getFocusWidth());
        mCropImageView.setFocusHeight(imagePicker.getFocusHeight());
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_photo:
                Intent intent = new Intent(this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            case R.id.btn_select_pic:
                Intent intent2 = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent2, 100);
                break;
            case R.id.btn_input:
              /*  mMyMatrixImg.setDrawingCacheEnabled(true);
                Bitmap bit =  mMyMatrixImg.getDrawingCache();

                mMyMatrixImg.buildDrawingCache(true);
                mMyMatrixImg.buildDrawingCache();
                Bitmap bitmap = mMyMatrixImg.getDrawingCache();
                saveBitmapFile(bitmap);
                mMyMatrixImg.setDrawingCacheEnabled(false);*/
               /* mCropImageView.setDrawingCacheEnabled(true);
                Bitmap bit =  mCropImageView.getDrawingCache();

                mCropImageView.buildDrawingCache(true);
                mCropImageView.buildDrawingCache();
                Bitmap bitmap = mCropImageView.getDrawingCache();
                saveBitmapFile(bitmap);
                mCropImageView.setDrawingCacheEnabled(false);*/
               /* if(bit == null){

                }*/
                break;
        }
    }

    public void saveBitmapFile(Bitmap bitmap){

        File temp = new File("/sdcard/1delete/");//要保存文件先创建文件夹
        if (!temp.exists()) {
            temp.mkdir();
        }
        ////重复保存时，覆盖原同名图片
        File file=new File("/sdcard/1delete/1.jpg");//将要保存图片的路径和图片名称
        //    File file =  new File("/sdcard/1delete/1.png");/////延时较长
        try {
            BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(file));
           bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 初始化权限事件
     */
    private void initPermission() {
        mPermissionsChecker = new PermissionsChecker(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    //重写OptionsItemSelected(MenuItem item)来响应菜单项(MenuItem)的点击事件（根据id来区分是哪个item）
        @Override
       public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
               // automatically handle clicks on the Home/Up button, so long
               // as you specify a parent activity in AndroidManifest.xml.
                switch (item.getItemId()) {
                    case R.id.action_settings:
                        //Toast.makeText(getApplicationContext(),"tewafdasfa",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), CaptureActivity.class);
                        startActivityForResult(intent, REQUEST_CODE);
                        break;
                    case R.id.action_down:
                        //Toast.makeText(getApplicationContext(),"tewafdasfa",Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getApplication(), CaptureActivity.class);
                        startActivityForResult(intent2, 123);
                        break;
                    default:
                           break;
                   }
              return super.onOptionsItemSelected(item);
        }
    @Override protected void onResume() {
        super.onResume();

        // 缺少权限时, 进入权限配置页面
        if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
            startPermissionsActivity();
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, PERMISSIONS_REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Log.d("MainActivity",images.size()+" " +images.get(0).path+"name :" +images.get(0).name);
               /* BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(images.get(0).path, options);
                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                options.inSampleSize = Utils.calculateInSampleSize(options, displayMetrics.widthPixels, displayMetrics.heightPixels);
                options.inJustDecodeBounds = false;
                mBitmap = BitmapFactory.decodeFile(images.get(0).path, options);*/
                imagePicker.getImageLoader().displayImage(MainActivity.this, images.get(0).path, mImage, mWidth, mWidth);
               // mCropImageView.setImageBitmap(mCropImageView.rotate(mBitmap, BitmapUtil.getBitmapDegree(images.get(0).path)));
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }else if(requestCode == 123){
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCropImageView.setOnBitmapSaveCompleteListener(null);
        if (null != mBitmap && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

}
