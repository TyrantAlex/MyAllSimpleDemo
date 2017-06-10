package com.myallsimpledemo.glide;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.alex.dagger.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 使用Glide下载网络图片
 */
public class ShowPictureActivity extends AppCompatActivity {

    public static final String TAG = "ShowPictureActivity";

    private Handler handler = new Handler();

    @BindView(R.id.iv_glide_show1)
    ImageView ivGlideShow1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        Glide.with(ShowPictureActivity.this)
                .load("http://i.imgur.com/idojSYm.png")
                .animate(android.R.anim.slide_in_left)
                .into(new GlideDrawableImageViewTarget(ivGlideShow1){
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                Log.d(TAG,"下载第一张图片完成开始下载第二章图片...");
                Toast.makeText(ShowPictureActivity.this, "下载第一张图片完成开始下载第二章图片", Toast.LENGTH_SHORT).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                      EventBus.getDefault().post(1);
                    }
                },5000);
            }
        });
    }

    @Subscribe
    public void onEventBusShowPic(Integer flag){
        if(flag == 0){
            Log.d(TAG,"接收到发布flag = 0");
        }else if(flag == 1){
            Log.d(TAG,"接收到发布flag = 1,开始下载图片");
            //Glide下载显示图片
            Glide.with(this)
                    .load("http://img2.3lian.com/2014/f6/173/d/51.jpg")
                    .transform(new GlideCircleTransform(ShowPictureActivity.this))
                    .animate(R.drawable.slide_left_in)
                    .into(ivGlideShow1);
//            Glide.with(this).load("http://i.imgur.com/idojSYm.png").into(ivGlideShow1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
