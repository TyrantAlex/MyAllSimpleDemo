package com.realm.config;

import com.realm.bean.CelebiPageBean;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author : hongshen
 * @Date: 2018/4/3 0003
 */

public class RealmManager {
    /**-------------------------------------------------------------------realm 增删改查------------------------------------------------------------------------------------**/
    public void addPage() {
        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        CelebiPageBean celebiPageBean = realm.createObject(CelebiPageBean.class); // Create a new object
        celebiPageBean.setPackageVersion("1.0.1");
        realm.commitTransaction();
    }

    public List<CelebiPageBean> queryPageBean() {
        Realm  mRealm=Realm.getDefaultInstance();

        RealmResults<CelebiPageBean> realmResults = mRealm.where(CelebiPageBean.class).findAll();

        return mRealm.copyFromRealm(realmResults);
    }

    public void deletePage() {
        Realm  mRealm=Realm.getDefaultInstance();

        final RealmResults<CelebiPageBean> celebiPageBeans=  mRealm.where(CelebiPageBean.class).findAll();

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

//                CelebiPageBean celebiPageBean=celebiPageBeans.get(5);
//                celebiPageBean.deleteFromRealm();

//                //删除第一个数据
//                celebiPageBeans.deleteFirstFromRealm();
//                //删除最后一个数据
//                celebiPageBeans.deleteLastFromRealm();
//                //删除位置为1的数据
//                celebiPageBeans.deleteFromRealm(1);
//                //删除所有数据
                celebiPageBeans.deleteAllFromRealm();
            }
        });
    }

    public void updatePage() {
        Realm  mRealm=Realm.getDefaultInstance();
        CelebiPageBean celebiPageBean = mRealm.where(CelebiPageBean.class).equalTo("packageVersion", "1.0.1").findFirst();
        mRealm.beginTransaction();
        celebiPageBean.setPackageVersion("1.0.2");
        mRealm.commitTransaction();
    }
}
