package com.feinno.live;

import android.app.Activity;
import android.app.Application;
import android.support.multidex.MultiDex;

import com.feinno.srtclib_android.FeinnoMegLibSDK;

import java.util.LinkedList;
import java.util.List;

/**
 * Title:MyApplication
 * <p>
 * Description:初始化Application
 * </p>
 * Author Han.C
 * Date 2019/5/7 13:37
 */
public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication myApplication;

    public static MyApplication getApplication() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;
        MultiDex.install(this);
        FeinnoMegLibSDK.init(this);

    }

    public List<Activity> mList = new LinkedList<Activity>();

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //杀目前正在运行的进程
//            killMessageServise();
//            killMessageServise();
//            killProcess();
//            FeinnoMegLibSDK.getInstance().destroy(this);
//            System.exit(0);

        }
    }


}
