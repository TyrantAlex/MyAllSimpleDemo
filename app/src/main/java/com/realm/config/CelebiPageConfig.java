package com.realm.config;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.realm.bean.CelebiPageBean;
import com.realm.lisenter.OnFinishListener;
import com.realm.utils.FileUtils;
import com.realm.utils.ZipUtils;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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

    /**
     * 版本号存放文件名
     */
    private String ZIP_FILE_NAME_PACKAGE_VERSION = "package.json";

    //需要得到一个versionCode
    String packageVersionCode = "";

    private Context context;

    RealmDBManager realmDBManager;

    public CelebiPageConfig(Context context) {
        this.context = context;
        realmDBManager = new RealmDBManager();
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
            upZipPage(path, new OnFinishListener<String>() {
                @Override
                public void onSuccess(String s) {
                    packageVersionCode = s;
                    queryPageFromServer(packageVersionCode);
                }

                @Override
                public void onFailure(String message) {

                }
            });
        }
    }

    /**
     * 进入动态页面的逻辑
     */
    public void startPageInit(String pageId) {
        //数据库中是否存在当前页面id
        boolean exisPageIdForDB = realmDBManager.isExisPageIdForDB(pageId);
        if (exisPageIdForDB) {
            //从网络加载
            Log.d("sqs","从网络加载页面...");
        } else {
            //从文件中加载
            String pageStr = queryPageFromLocal(pageId);
            Log.d("sqs","从本地文件中加载页面..." + pageStr);
        }
    }

    /**
     * 从本地加载页面
     * @param pageId
     */
    private String queryPageFromLocal(String pageId) {
        File dir = context.getDir(path, Activity.MODE_PRIVATE);
        String[] list = dir.list();
        String completePath = null;
        for (int i = 0; i < list.length; i++) {
            //取得对应json文件路径
            if (pageId.equals(list[i])) {
                completePath = dir.getPath() + File.separator + list[i];
            }
        }
        String pageStr = null;
        try {
            pageStr = FileUtils.readFileToString(completePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageStr;
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
    private void upZipPage(final String path,final OnFinishListener<String> listener) {
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
                    public void onNext(Object object) {
                        String pageVersionCode = getPageVersionCode(path);
                        listener.onSuccess(pageVersionCode);
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
            //取得版本号文件路径
            if (ZIP_FILE_NAME_PACKAGE_VERSION.equals(list[i])) {
                completePath = dir.getPath() + File.separator + list[i];
            }
        }
        String pageStr = null;
        String versionCode = null;
        try {
            pageStr = FileUtils.readFileToString(completePath);
            Log.d("sqs", "page 字符串: " + pageStr.trim());
            if (!TextUtils.isEmpty(pageStr)) {
                JSONObject jsonObject = new JSONObject(pageStr);
                versionCode = jsonObject.getString("version");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 请求更新页面
     * @param packageVersion package版本号
     */
    private void queryPageFromServer(String packageVersion) {
        //TEMP
        CelebiPageBean celebiPageBean = new CelebiPageBean(); // Create a new object

        celebiPageBean.setPackageVersion("1.0.3");
        realmDBManager.addPageForRealm(celebiPageBean);
    }
}
