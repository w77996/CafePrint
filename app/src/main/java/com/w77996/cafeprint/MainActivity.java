package com.w77996.cafeprint;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.w77996.cafeprint.utils.MyMatrixImg;
import com.w77996.cafeprint.view.CircleRelativeLayout;
import com.w77996.cafeprint.view.CustomViews;

public class MainActivity extends Activity implements View.OnClickListener{

    Button mPhoto;
    //CustomViews mCustomViews;
    MyMatrixImg mMyMatrixImg;
    CircleRelativeLayout mview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mview = new CircleRelativeLayout(getApplicationContext());
        mMyMatrixImg = new MyMatrixImg(getApplicationContext());
        mview.addView(mMyMatrixImg);
        mPhoto = (Button)findViewById(R.id.btn_photo);
       // mCustomViews = (CustomViews) findViewById(R.id.cv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_photo:

                break;
        }
    }
}
