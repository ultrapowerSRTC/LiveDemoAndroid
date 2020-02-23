package com.feinno.live;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.feinno.androidbase.utils.log.LogFeinno;
import com.feinno.srtclib_android.FeinnoMegLibSDK;
import com.feinno.srtclib_android.SrtcCallBackListener;
import com.feinno.srtclib_android.bean.DanmukuBean;
import com.feinno.srtclib_android.bean.SRTCLiveCommonParams;
import com.feinno.srtclib_android.constant.CommonConstant;
import com.feinno.srtclib_android.util.RtmpUrlBean;

import java.nio.Buffer;

/**
 * Title:ALiveCaptureActivity
 * <p>
 * Description:直播主播端UI
 * </p>
 * Author Han.C
 * Date 2019/8/8 16:45
 */
public class ALiveCaptureActivity extends BaseActivity implements SrtcCallBackListener {
    private static final String TAG = ALiveCaptureActivity.class.getSimpleName();
    private ConstraintLayout pushContent;

    private String channelName;
    private ImageView mainBtnSwitchCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (CommonConstant.isLandscape) {
            //横屏
            if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }
        setContentView(R.layout.activity_alive_capture);
        //屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        pushContent = (ConstraintLayout) findViewById(R.id.pushContent);
        mainBtnSwitchCamera = (ImageView) findViewById(R.id.main_btn_switch_camera);
        channelName = getIntent().getStringExtra("roomId");
        initData();

    }

    private ExitRoomDialog mExitRoomDialog;

    private void initData() {
        mExitRoomDialog = new ExitRoomDialog(mContext, R.style.NoBackGroundDialog);
        mExitRoomDialog.setCanceledOnTouchOutside(false);
        mExitRoomDialog.setText("你确定要退出直播吗?", "确定", "取消");
        mExitRoomDialog.mConfirmBT.setOnClickListener(v -> {
            FeinnoMegLibSDK.getInstance().stopPusher();
            mFeinnoMegLibSDK.stopPusherPreview();
            mExitRoomDialog.dismiss();
            finish();
        });
        mExitRoomDialog.mDenyBT.setOnClickListener(v -> mExitRoomDialog.dismiss());


        SurfaceView surfaceView = FeinnoMegLibSDK.getInstance().createRendererView(this);

        mFeinnoMegLibSDK.setupLivePusherPreView(0, surfaceView, getRequestedOrientation());
        pushContent.addView(surfaceView);
        mFeinnoMegLibSDK.startPusherPreview();

        SRTCLiveCommonParams params = new SRTCLiveCommonParams();
        //登陆AppKey
        params.setAppKey(CommonConstant.AppKey);
        params.setChannelName(channelName);
        //是否横屏
        params.setIslandScape(false);
        //设置清晰度：1标清2高清3超清
        params.setDefinition("1");
        params.setDesc("这是描述");
        //设置开播的直播类型ID，实际业务中有单独的接口获取所有开播类型
        params.setLive1("1");
        params.setLive2("1");
        params.setName("房间名称");
        params.setUccId(CommonConstant.Uccid);
        params.setPassword("");
        params.setNickName(CommonConstant.Uccid);
        params.setAuth(false);//关闭防盗链
        mFeinnoMegLibSDK.createLiveRoom(params);


        mainBtnSwitchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeinnoMegLibSDK.getInstance().switchLivePusherCamera();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FeinnoMegLibSDK.getInstance().setCallBack(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        FeinnoMegLibSDK.getInstance().pauseDanmaku();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        mExitRoomDialog.show();
    }


    @Override
    public void onLoginStatus(int i, String s) {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onJoinChannelSuccess(String s, long l, String s1) {

    }

    @Override
    public void onUserJoined(long l, int i) {

    }

    @Override
    public void onUserEnableVideo(long l, boolean b) {

    }

    @Override
    public void onLeaveChannel() {

    }

    @Override
    public void onFirstLocalVideoFrame(int i, int i1) {

    }

    @Override
    public void onFirstRemoteVideoFrame(long l, int i, int i1) {

    }

    @Override
    public void onFirstRemoteVideoDecoded(long l, int i, int i1) {

    }

    @Override
    public void onUserOffline(long l, int i) {

    }

    @Override
    public void onCameraReady() {

    }

    @Override
    public void onAudioVolumeIndication(long l, int i, int i1) {

    }

    @Override
    public void onRtcStats() {

    }

    @Override
    public void onAudioRouteChanged(int i) {

    }

    @Override
    public void onSetSEI(String s) {

    }

    @Override
    public void onVideoStopped() {

    }

    @Override
    public void onPlayChatAudioCompletion(String s) {

    }

    @Override
    public void onRequestChannelKey() {

    }

    @Override
    public void onUserRoleChanged(long l, int i) {

    }

    @Override
    public void onScreenRecordTime(int i) {

    }

    @Override
    public void onUserMuteAudio(long l, boolean b) {

    }

    @Override
    public void onStatusOfRtmpPublish(int i, String s) {
        //RTMP推流回调
        LogFeinno.e(TAG, "onStatusOfRtmpPublish errorType-> " + i);
        if (i == 0) {
            ToastUtil.showShort(ALiveCaptureActivity.this, "推流初始化失败");
        } else if (i == 1) {
            ToastUtil.showShort(ALiveCaptureActivity.this, "推流启动失败");
        } else if (i == 4) {
            ToastUtil.showShort(ALiveCaptureActivity.this, "推流发送失败");
        } else if (i == 5) {
            ToastUtil.showShort(ALiveCaptureActivity.this, "推流成功");
        } else {
            ToastUtil.showShort(ALiveCaptureActivity.this, "推流失败:" + i);
        }

    }

    @Override
    public void onStatusOfPull(int i, String s) {

    }

    @Override
    public void onAudienceNumber(long l) {

    }

    @Override
    public void onFeverNumber(long l) {

    }

    @Override
    public void onSpeakingMuted(long l, boolean b) {

    }

    @Override
    public void onLocalVideoFrameCapturedBytes(int i, int i1, Buffer buffer) {

    }

    @Override
    public void onRemoteVideoFrameDecodedOfUid(int i, int i1, Buffer buffer) {

    }

    @Override
    public void onRtmpNumber(int i) {

    }

    @Override
    public void onDanmukuContent(DanmukuBean danmukuBean) {

    }

    @Override
    public void onNetChanged(int i) {

    }

    @Override
    public String getAuthParams(RtmpUrlBean rtmpUrlBean) {
        return null;
    }

    @Override
    public void FMLogCallBack(String s) {

    }

    @Override
    public void connectMqttCallBack(int i, String s) {

    }

    @Override
    public void onOpeTime(String s) {

    }

}
