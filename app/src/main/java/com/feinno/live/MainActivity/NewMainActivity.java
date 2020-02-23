package com.feinno.live.MainActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.feinno.androidbase.utils.log.LogFeinno;
import com.feinno.srtclib_android.FeinnoMegLibSDK;
import com.feinno.srtclib_android.SrtcCallBackListener;
import com.feinno.srtclib_android.bean.DanmukuBean;
import com.feinno.srtclib_android.util.RtmpUrlBean;
import com.feinno.live.BaseActivity;
import com.feinno.live.CheckSumBuilder;
import com.feinno.live.LiveMainActivity;
import com.feinno.live.MyApplication;
import com.feinno.live.R;
import com.feinno.live.ToastUtil;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.nio.Buffer;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Demo首页
 * 采用今日头条的整体适配方案，暂时没有发现什么问题
 */
//@IgnoreScreenAdapter
//@ScreenAdapterDesignWidthInDp(400)
public class NewMainActivity extends BaseActivity implements SrtcCallBackListener {
    private static final String TAG = NewMainActivity.class.getSimpleName();


    @BindView(R.id.uccid_edit)
    EditText uccidEdit;

    @BindView(R.id.loginSDK)
    Button loginSDK;

    /**
     * 加载中窗体
     */
    private ProgressDialog mDialog;
    /**
     * 按击返回键次数
     */
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_main);
        ButterKnife.bind(this);
        initView();

        checkPermission(this);
    }

    public void checkPermission(Activity activity) {
        XXPermissions.with(activity).permission(Permission.CAMERA, Permission.RECORD_AUDIO, Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_PHONE_STATE)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        Log.e("===", "必须授权成功");
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        Log.e("===", "悬浮窗权限拒绝授权");
                    }
                });

    }


    /**
     * 初始化缓存数据
     */
    private void initView() {

        uccidEdit.setText("");
        mDialog = new ProgressDialog(this);
        mDialog.setTitle("");
        mDialog.setMessage("登录中...");

        loginSDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uccId = uccidEdit.getText().toString();
                if (TextUtils.isEmpty(uccId)) {
                    ToastUtil.showShort(NewMainActivity.this, "请输入uccId");
                    return;
                }
                mDialog.show();
                FeinnoMegLibSDK.getInstance().setServiceIp("");
                String curtime = String.valueOf(System.currentTimeMillis());
                //todo--实际业务处理中，不用每次加入房间都登陆，登陆成功一次即可。
                FeinnoMegLibSDK.getInstance().loginSDK("",
                        uccId, curtime,
                        CheckSumBuilder.getCheckSum("", uccId, curtime));
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        FeinnoMegLibSDK.getInstance().setCallBack(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
    }

    @Override
    public void onLoginStatus(int code, String msg) {
        mDialog.dismiss();
        if (code == 0) {
            LogFeinno.e(TAG, "登录成功");
            startActivity(new Intent(NewMainActivity.this, LiveMainActivity.class));
        } else {
            ToastUtil.showShort(NewMainActivity.this, "登录失败 code=" + code + "    msg=" + msg);
        }
    }

    @Override
    public void onError(int err) {

    }

    @Override
    public void onJoinChannelSuccess(String channel, long uid, String callId) {

    }

    @Override
    public void onUserJoined(long nUserId, int identity) {

    }

    @Override
    public void onUserEnableVideo(long uid, boolean muted) {

    }

    @Override
    public void onLeaveChannel() {

    }

    @Override
    public void onFirstLocalVideoFrame(int width, int height) {

    }

    @Override
    public void onFirstRemoteVideoFrame(long uid, int width, int height) {

    }

    @Override
    public void onFirstRemoteVideoDecoded(long uid, int width, int height) {

    }

    @Override
    public void onUserOffline(long nUserId, int reason) {

    }

    @Override
    public void onCameraReady() {

    }

    @Override
    public void onAudioVolumeIndication(long nUserID, int audioLevel, int audioLevelFullRange) {

    }

    @Override
    public void onRtcStats() {

    }

    @Override
    public void onAudioRouteChanged(int routing) {

    }

    @Override
    public void onSetSEI(String sei) {

    }

    @Override
    public void onVideoStopped() {

    }

    @Override
    public void onPlayChatAudioCompletion(String filePath) {

    }

    @Override
    public void onRequestChannelKey() {

    }

    @Override
    public void onUserRoleChanged(long userID, int userRole) {

    }

    @Override
    public void onScreenRecordTime(int mRecordTime) {

    }

    @Override
    public void onUserMuteAudio(long uid, boolean muted) {

    }

    @Override
    public void onStatusOfRtmpPublish(int errorType, String srtcPushUrl) {

    }

    @Override
    public void onStatusOfPull(int type, String msg) {

    }

    @Override
    public void onAudienceNumber(long number) {

    }

    @Override
    public void onFeverNumber(long number) {

    }

    @Override
    public void onSpeakingMuted(long uid, boolean muted) {

    }

    @Override
    public void onLocalVideoFrameCapturedBytes(int width, int height, Buffer src) {

    }

    @Override
    public void onRemoteVideoFrameDecodedOfUid(int width, int height, Buffer src) {

    }

    @Override
    public void onRtmpNumber(int number) {

    }

    @Override
    public void onDanmukuContent(DanmukuBean mDanmuku) {

    }

    @Override
    public void onNetChanged(int code) {

    }

    @Override
    public String getAuthParams(RtmpUrlBean bean) {
        return null;
    }

    @Override
    public void FMLogCallBack(String log) {

    }

    @Override
    public void connectMqttCallBack(int code, String msg) {
        if (code == 2000) {
            ToastUtil.showShort(NewMainActivity.this, "长链接建立成功");
        } else {
            ToastUtil.showShort(NewMainActivity.this, "长链接建立失败 code -> " + code + " msg -> " + msg);
        }
    }

    @Override
    public void onOpeTime(String time) {

    }


    @Override
    public void onBackPressed() {
        switch (count++) {
            case 0:
                Toast.makeText(NewMainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        count = 0;
                    }
                }, 1500);
                break;
            case 1:
                MyApplication.getApplication().exit();
            default:
                break;
        }

    }


}
