package com.feinno.live;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.feinno.srtclib_android.FeinnoMegLibSDK;


/**
 * Created by wangzhiguo on 17/10/12.
 */
public class BaseActivity extends AppCompatActivity {

    protected FeinnoMegLibSDK mFeinnoMegLibSDK;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getApplication().addActivity(this);
//        getSupportActionBar().hide();// 隐藏标题
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 设置全屏

        //获取上下文
        mContext = this;
        mFeinnoMegLibSDK = FeinnoMegLibSDK.getInstance();
    }

}
