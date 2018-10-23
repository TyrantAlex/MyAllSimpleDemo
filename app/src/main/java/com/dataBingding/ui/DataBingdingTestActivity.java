package com.dataBingding.ui;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import com.dataBingding.event.UserHandler;
import com.example.alex.dagger.R;
import com.example.alex.dagger.databinding.ActivityDataBindingTestBinding;

/**
 * dataBinding 测试 activity
 * @author : shen
 * @Date: 2018/5/20
 */

public class DataBingdingTestActivity extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_binding_test);
//        ActivityDataBindingTestBinding binding = ActivityDataBindingTestBinding.inflate(getLayoutInflater());
        ActivityDataBindingTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_test);
        binding.setHandler(new UserHandler(this));
    }
}
