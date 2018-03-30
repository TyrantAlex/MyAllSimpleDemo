package com.realm.config;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.realm.bean.CelebiPage;
import com.realm.bean.CelebiPageBean;
import com.realm.utils.FileUtils;
import com.realm.utils.ZipUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author : hongshen
 * @Date: 2018/3/29 0029
 */

public class CelebiPageConfig {

    private boolean isFirstPage = true;

    private boolean isNeed2UpZip = true;

    private String path = "CelebiFilePath";

    private String ZIP_FILE_NAME = "DynamicPage.zip";

    private Context context;

    public CelebiPageConfig(Context context) {
        this.context = context;
    }

    /**
     * 启动时逻辑
     */
    public void startAppInit() {
        //如果是第一次启动
        if (isFirstPage) {
            //清除数据库文件
            deleteAllPage();
            //清除本地json文件
            deleteLocalFile(path);
        }
        //是否需要解压zip包
        if (isNeed2UpZip) {
            upZipPage(path);
        }

        //需要得到一个versionCode
        String packageVersionCode = "";
        queryPageFromServer(packageVersionCode);
    }

    /**
     * 进入动态页面的逻辑
     */
    public void startPageInit(String pageId) {
        //数据库中是否存在当前页面id
        boolean exisPageIdForDB = isExisPageIdForDB(pageId);
        if (exisPageIdForDB) {
            //从网络加载

        } else {
            //从文件中加载

        }
    }

    /**
     * 清除所有页面相关数据
     */
    private void deleteAllPage() {
        Realm mRealm=Realm.getDefaultInstance();
        final RealmResults<CelebiPageBean> celebiPageBeans=  mRealm.where(CelebiPageBean.class).findAll();
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //删除所有数据
                celebiPageBeans.deleteAllFromRealm();
            }
        });
    }

    /**
     * 清除本地文件
     * @param path 路径
     */
    private void deleteLocalFile(String path) {

    }

    /**
     * 解压zip包创建本地文件夹
     * @param path 路径
     */
    private void upZipPage(final String path) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                ZipUtils.UnZipFile(context, path, ZIP_FILE_NAME);
                subscriber.onNext("");
                subscriber.onCompleted();
            }
        })
        .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
        .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
        .subscribe(new Observer<Object>() {
                    @Override
                    public void onNext(Object drawable) {
                        getPageVersionCode(path);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    private String getPageVersionCode(String path) {
        File dir = context.getDir(path, Activity.MODE_PRIVATE);
        String[] list = dir.list();
        String completePath = null;
        for (int i = 0; i < list.length; i++) {
            completePath = dir.getPath() + File.separator + list[i];
        }
        String pageStr = null;
        try {
            pageStr = FileUtils.readFileToString(completePath);
            Log.d("sqs", "page 字符串: " + pageStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageStr;
    }

    /**
     * 请求更新页面
     * @param packageVersion package版本号
     */
    private void queryPageFromServer(String packageVersion) {
        //TEMP
        CelebiPageBean celebiPageBean = new CelebiPageBean(); // Create a new object
        celebiPageBean.setPackageVersion("1.0.3");
        addPageForRealm(celebiPageBean);
    }

    /**
     * 存储页面
     * @param celebiPageBean
     */
    private void addPageForRealm(CelebiPageBean celebiPageBean) {
        Realm realm=Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(celebiPageBean);
        realm.commitTransaction();
    }

    /**
     * 查询pageid是否存在于数据库中
     * @param pageId
     * @return
     */
    private boolean isExisPageIdForDB(String pageId){
        Realm  mRealm=Realm.getDefaultInstance();
        CelebiPage celebiPage = mRealm.where(CelebiPage.class).equalTo("android", pageId).findFirst();
        return celebiPage == null ? false : true;
    }
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

                CelebiPageBean celebiPageBean=celebiPageBeans.get(5);
                celebiPageBean.deleteFromRealm();
//                //删除第一个数据
//                celebiPageBeans.deleteFirstFromRealm();
//                //删除最后一个数据
//                celebiPageBeans.deleteLastFromRealm();
//                //删除位置为1的数据
//                celebiPageBeans.deleteFromRealm(1);
//                //删除所有数据
//                celebiPageBeans.deleteAllFromRealm();
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
