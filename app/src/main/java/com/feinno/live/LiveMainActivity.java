package com.feinno.live;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Title:LiveMainActivity
 * <p>
 * Description:直播推拉流页面
 * </p>
 * Author Han.C
 * Date 2020/2/22 12:44 PM
 */
public class LiveMainActivity extends AppCompatActivity {

    private LinearLayout contentUccid;
    private EditText uccidEdit;
    private Button live;
    private Button audience;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_main);
        initView();
    }

    private void initView() {
        contentUccid = (LinearLayout) findViewById(R.id.content_uccid);
        uccidEdit = (EditText) findViewById(R.id.uccid_edit);
        live = (Button) findViewById(R.id.live);
        audience = (Button) findViewById(R.id.audience);

        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //主播创建直播间开始推流
                String roomId = uccidEdit.getText().toString();
                if (TextUtils.isEmpty(roomId)){
                    ToastUtil.showShort(LiveMainActivity.this,"请输入房间号");
                    return;
                }
                Intent intent = new Intent(LiveMainActivity.this,ALiveCaptureActivity.class);
                intent.putExtra("roomId",roomId);
                startActivity(intent);

            }
        });

        audience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomId = uccidEdit.getText().toString();
                if (TextUtils.isEmpty(roomId)){
                    ToastUtil.showShort(LiveMainActivity.this,"请输入房间号");
                    return;
                }
                Intent intent = new Intent(LiveMainActivity.this,ALiveAudienceActivity.class);
                intent.putExtra("roomId",roomId);
                startActivity(intent);

            }
        });

    }


}