package com.realm.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.alex.dagger.R;
import com.realm.bean.CelebiPageBean;
import com.realm.config.CelebiPageConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RealmFirstActivity extends AppCompatActivity {

    @BindView(R.id.btn_add)
    Button btn_add;

    @BindView(R.id.btn_query)
    Button btn_query;

    @BindView(R.id.btn_delete)
    Button btn_delete;

    @BindView(R.id.btn_update)
    Button btn_update;

    @BindView(R.id.btn_up_zip)
    Button btn_up_zip;

    CelebiPageConfig celebiPageConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_first);
        ButterKnife.bind(this);
        initEvent();
        celebiPageConfig = new CelebiPageConfig(this);
    }

    private void initEvent() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                celebiPageConfig.addPage();
                Log.d("realm", "添加");
            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CelebiPageBean> celebiPageBeans = celebiPageConfig.queryPageBean();
                Log.d("realm", "查询");
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                celebiPageConfig.deletePage();
                Log.d("realm", "删除");
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                celebiPageConfig.updatePage();
                Log.d("realm", "修改");
            }
        });

        btn_up_zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                celebiPageConfig.startAppInit();
            }
        });
    }

}
