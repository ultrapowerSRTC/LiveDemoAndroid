package com.feinno.live;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.feinno.androidbase.utils.log.LogFeinno;
import com.feinno.srtclib_android.FeinnoMegLibSDK;
import com.feinno.srtclib_android.SrtcCallBackListener;
import com.feinno.srtclib_android.bean.DanmukuBean;
import com.feinno.srtclib_android.bean.SRTCLiveParams;
import com.feinno.srtclib_android.constant.CommonConstant;
import com.feinno.srtclib_android.util.RtmpUrlBean;
import com.feinno.srtclib_android.widget.EVideoView;
import java.nio.Buffer;

/**
 * Title:ALiveAudienceActivity
 * <p>
 * Description:直播观众端UI
 * </p>
 * Author Han.C
 * Date 2019/8/9 9:35
 */
public class ALiveAudienceActivity extends BaseActivity {

    private static final String TAG = ALiveAudienceActivity.class.getSimpleName();
    private EVideoView videoView;
    private TextView hint;
    private String channelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alive_audience);
        channelName = getIntent().getStringExtra("roomId");
        initView();
        //屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    private void initView() {
        videoView = (EVideoView) findViewById(R.id.video_view);
        hint = (TextView) findViewById(R.id.hint);

        FeinnoMegLibSDK.getInstance().initPlayerWithDefaultConfig(videoView);
        SRTCLiveParams bean = new SRTCLiveParams();
        bean.setChannelName(channelName);
        bean.setPassword("");
        bean.setUccId(CommonConstant.Uccid);
        bean.setUserName("测试观众" + CommonConstant.Uccid);

        FeinnoMegLibSDK.getInstance().enterLiveRoom(bean);


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        FeinnoMegLibSDK.getInstance().pausePlayer();
        FeinnoMegLibSDK.getInstance().pauseDanmaku();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FeinnoMegLibSDK.getInstance().stopPlayer();

    }

    @Override
    protected void onResume() {
        super.onResume();
        FeinnoMegLibSDK.getInstance().resumeDanmaku();
        FeinnoMegLibSDK.getInstance().resumePlayer();
        FeinnoMegLibSDK.getInstance().setCallBack(new SrtcCallBackListener() {
            @Override
            public void onLoginStatus(int code, String msg) {

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
                //int MEDIA_INFO_VIDEO_INTERRUPT= -10000;//数据连接中断，一般是视频源有问题或者数据格式不支持，比如音频不是AAC之类的
                //拉流回调
                LogFeinno.e(TAG, "type -> " + type + " msg -> " + msg);
                switch (type) {
                    case 0://开始拉流
                        ToastUtil.showShort(ALiveAudienceActivity.this, "拉取视频成功");
                        hint.setVisibility(View.GONE);
//                        FeinnoMegLibSDK.getInstance().setEnableSpeakerphone(true);
//                        showBottomPopWindow();
                        break;
                    case 1://缓冲
                        ToastUtil.showShort(ALiveAudienceActivity.this, "缓冲中...");
                        hint.setText("缓冲中...");
                        hint.setVisibility(View.VISIBLE);
                        break;
                    case 2://暂停
                        ToastUtil.showShort(ALiveAudienceActivity.this, "主播暂停中...");
                        hint.setText("主播暂停中...");
                        hint.setVisibility(View.VISIBLE);
                        break;
                    case 3://恢复
                        ToastUtil.showShort(ALiveAudienceActivity.this, "视频恢复");
                        hint.setVisibility(View.GONE);
                        break;
                    case 4://主播结束
                        ToastUtil.showShort(ALiveAudienceActivity.this, "主播已退出");
                        hint.setText("主播已退出...");
                        hint.setVisibility(View.VISIBLE);
                        break;
                    case 5://主播不在
                        ToastUtil.showShort(ALiveAudienceActivity.this, "主播未开播");
                        hint.setText("主播未开播");
                        hint.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        ToastUtil.showShort(ALiveAudienceActivity.this, "主播恢复，加载中。。。");
                        LogFeinno.e(TAG, "主播恢复，加载中。。。");
                        FeinnoMegLibSDK.getInstance().refreshLivePlayer();
                        break;
                    case -10000:
                        ToastUtil.showShort(ALiveAudienceActivity.this, "视频数据源有误");
                        hint.setText("视频数据源有误...");
                        hint.setVisibility(View.VISIBLE);
                        break;
                    case -1:
                        ToastUtil.showShort(ALiveAudienceActivity.this, "拉流异常，SDK开始重试机制");
                        LogFeinno.e(TAG, "==============拉流异常开始重试（包含网络切换和网络断开）");
                        break;
                    case -2:
                        ToastUtil.showShort(ALiveAudienceActivity.this, "拉流重试失败，刷新或退出重进");
                        hint.setText("拉流失败.");
                        hint.setVisibility(View.VISIBLE);
                        LogFeinno.e(TAG, "==============非网络状态导致的重试失败");
                        break;
                    case -3:
                        ToastUtil.showShort(ALiveAudienceActivity.this, "拉流失败");
                        hint.setText("拉流失败.");
                        hint.setVisibility(View.VISIBLE);
                        LogFeinno.e(TAG, "==============无网络导致的重试失败");
                        break;
                    case -4:
                        ToastUtil.showShort(ALiveAudienceActivity.this, "拉流失败");
                        hint.setText("拉流失败.");
                        hint.setVisibility(View.VISIBLE);
                        LogFeinno.e(TAG, "==============SRTC内部导致的重试失败");
                        break;
                    default:
                        hint.setText(msg);
                        hint.setVisibility(View.VISIBLE);
                }
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
                LogFeinno.e(TAG, "number ->" + number);

            }

            @Override
            public void onDanmukuContent(DanmukuBean mDanmuku) {


            }

            @Override
            public void onNetChanged(int code) {
            }

            @Override
            public String getAuthParams(RtmpUrlBean bean) {
                //todo--防盗链所需配置，主播端若开启防盗链，观众拉流的时候会从该回调中获取bean.toString()
                //获取房间号(以下加密用到的房间号一定要从这个类中获取)
                String channelName = bean.getChannelName();
                String KEY = "01234567899876543210abcd";
                //时间戳最好采用网络时间，手机时间可能会有不准的情况，导致CDN方校验失败
                String txTime = Long.toHexString(Long.valueOf(String.valueOf(System.currentTimeMillis()).substring(0, 10)));
                String txSecret = CheckSumBuilder.getMD5String(KEY + channelName + txTime);
                //将参数设置到类中
                bean.addArgs("txSecret", txSecret);
                bean.addArgs("txTime", txTime);

                LogFeinno.e(TAG, "rtmpUrl == " + bean.toString());
                return bean.toString();

            }

            @Override
            public void FMLogCallBack(String log) {

            }

            @Override
            public void connectMqttCallBack(int code, String msg) {

            }

            @Override
            public void onOpeTime(String time) {
                LogFeinno.e(TAG, "onOpeTime ->" + time);
            }

        });
    }

    @Override
    public void onBackPressed() {
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            return;
        }
        this.finish();
    }

}
