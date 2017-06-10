package com.alex.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alex.dagger.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 检验EventBus的发布订阅流程
 */
public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.tv_1)
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        btn1.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEventMainThread(String message){
        tv1.setText("第一页面已接收:" + message);
        Toast.makeText(this,"第一页面已接收:" + message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                Intent intent = new Intent(this,Main3Activity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
