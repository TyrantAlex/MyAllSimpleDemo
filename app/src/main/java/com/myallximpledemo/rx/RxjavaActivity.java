package com.myallximpledemo.rx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.alex.dagger.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * Rxjava simple demo test1 独立的
 */
public class RxjavaActivity extends AppCompatActivity {
    private static final String TAG = "RxjavaActivity.class";

    private ImageView iv_test;
    private List<File> floaders = new ArrayList<File>();

    Observable observable;

    Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {
            Log.d(TAG,"RxJava ----> onCompleted!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG,"RxJava ----> onError!");
        }

        @Override
        public void onNext(String s) {
            Log.d(TAG,"RxJava onNext: " + s);
        }
    };

    private void rxJavaShow(){
/*        observable = Observable.create(new Observable.OnSubscribe<String>(){

            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.d(TAG,"RxJava 被观察者开始发布消息...");
                subscriber.onNext("hello rxjava");
                subscriber.onNext("haha");
                subscriber.onNext("Teeny");
                subscriber.onCompleted();
            }
        });*/

//        observable = Observable.just("hello rxjava just.","haha just","Teeny just");

/*        List<String> test_list = new ArrayList<String>();
        test_list.add("hello rxjava from");
        test_list.add("haha from");
        test_list.add("Teeny from");
        observable = Observable.from(test_list);*/

        //将字符串数组中的字符串依次打印出来
        String [] names = {"Teeny","Alex","Vencent"};
        observable = Observable.from(names);
        observable .subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG,"名字数组: " + s);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        rxJavaShow();
        observable.subscribe(observer);
    }
}
