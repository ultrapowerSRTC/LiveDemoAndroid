package com.feinno.live;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by wangzhiguo on 17/10/23.
 */
public class ExitRoomDialog extends Dialog {

    public Button mConfirmBT;
    public Button mDenyBT;
    private TextView resolution;

    public ExitRoomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.item_exit_room);
        mConfirmBT = findViewById(R.id.mainly_dialog_exit_confirm);
        mDenyBT = findViewById(R.id.mainly_dialog_exit_deny);
        resolution = findViewById(R.id.resolution);
    }


    public void setText(String d,String y,String n){
        if (mConfirmBT != null && mDenyBT != null && resolution != null){
            resolution.setText(d);
            mConfirmBT.setText(y);
            mDenyBT.setText(n);
        }
    }

}
