package com.myallsimpledemo.singleton.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.alex.dagger.R;
import com.myallsimpledemo.singleton.SingletonActivityVo;

public class Singleton3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singleton3);

        findViewById(R.id.btn_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Singleton3Activity.this, Singleton1Activity.class));
            }
        });

        SingletonActivityVo vo = SingletonActivityVo.getInstance();
        Log.d("sqs", "currentName = " + vo.getName()
                + ", currentPhone = " + vo.getPhone()
                + ", currentEmail = " + vo.getEmail());
        vo.setEmail("3@123.com");
        vo.setName("h3h3");
        vo.setPhone("188");
    }
}
