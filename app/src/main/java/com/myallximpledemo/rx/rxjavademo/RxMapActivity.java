package com.myallximpledemo.rx.rxjavademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.alex.dagger.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

public class RxMapActivity extends AppCompatActivity {

    private static final String TAG = "RxMapActivity.class";

    private String inputString = "Hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_map);
        rxMapTest();
    }

    private void rxMapTest(){
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(inputString);
            }
        }).map(new Func1<String, Integer>() {

            @Override
            public Integer call(String s) {
                return s.length();
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Rxjava onCompleted...");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Rxjava onError...");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Rxjava onNext: Input string's length = " + integer);
            }
        });
    }

    private void rxMapTest2() {

        Observable.OnSubscribe<String> onSubscribe = new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(inputString);
            }
        };

        Func1<String, Integer> func1 = new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return s.length();
            }
        };

        Subscriber<Integer> subscriber1 = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Rxjava onCompleted...");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Rxjava onError...");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Rxjava onNext: Input string's length = " + integer);
            }
        };

        Observable<String> observable1 = Observable.create(onSubscribe);

        Observable<Integer> observable2 = observable1.map(func1);

        observable2.subscribe(subscriber1);

        // flatmap
        observable1.flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String s) {
                return null;
            }
        });
    }
}


