package com.myallsimpledemo.singleton.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.alex.dagger.R;
import com.myallsimpledemo.singleton.SingletonActivityVo;

public class Singleton1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton1);

        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Singleton1Activity.this, Singleton2Activity.class));
            }
        });

        findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SingletonActivityVo vo = SingletonActivityVo.getInstance();
                vo.destroy();
                SingletonActivityVo vo1 = SingletonActivityVo.getInstance();
                Log.d("sqs", "currentName = " + vo1.getName()
                        + ", currentPhone = " + vo1.getPhone()
                        + ", currentEmail = " + vo1.getEmail());
            }
        });

        SingletonActivityVo vo = SingletonActivityVo.getInstance();
        Log.d("sqs", "currentName = " + vo.getName()
                + ", currentPhone = " + vo.getPhone()
                + ", currentEmail = " + vo.getEmail());
        vo.setEmail("1@123.com");
        vo.setName("haha");
        vo.setPhone("186");
    }
}
