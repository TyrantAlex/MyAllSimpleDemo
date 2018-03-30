package com.example.alex.dagger.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.alex.dagger.R;
//import com.example.alex.dagger.lisenter.DaggerTestShopCom;
import com.example.alex.dagger.moudle.TestShop;
import com.example.alex.dagger.qualifier.Qualifier;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
//    @Inject
//    TestShop mTestShop;

    @Qualifier.QualifierA @Inject TestShop mTestShopA;
    @Qualifier.QualifierB @Inject TestShop mTestShopB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DaggerTestShopCom.builder().build().inject(this);
        TextView tvShow = (TextView) findViewById(R.id.tv_show);
//        mTestShop.setPen("中华钢笔");
//        Log.d("sqs","现在的笔是" + mTestShop.getPen());
//        String write = mTestShop.write();
//        tvShow.setText(write);
//        Log.d("sqs","现在的字是" + write);
        mTestShopA.printPenName(mTestShopA.getPen());
        mTestShopB.printPenName(mTestShopB.getPen());
    }
}
