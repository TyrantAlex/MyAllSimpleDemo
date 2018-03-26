package com.base;

import android.app.Application;

import io.realm.Realm;

/**
 * @author : hongshen
 * @Date: 2018/3/26 0026
 */

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();

//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm").build();
//        Realm.setDefaultConfiguration(config);
    }
}
