package com.dataBingding.event;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * 测试dataBinding响应onClick事件
 * @author : shen
 * @Date: 2018/5/20
 */

public class UserHandler {

    private Context context;

    public UserHandler(Context context) {
        this.context = context;
    }

    public void onClickName(View view) {
        Toast.makeText(context, "user onclicking...", Toast.LENGTH_SHORT).show();
    }
}
