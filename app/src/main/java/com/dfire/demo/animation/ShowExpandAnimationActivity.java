package com.dfire.demo.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alex.dagger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowExpandAnimationActivity extends AppCompatActivity {

    @BindView(R.id.tv_detail)
    TextView tv_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expand_animation);
        ButterKnife.bind(this);
        loadUiAnimation();
    }

    private void loadUiAnimation() {
        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueAnimator valueAnimator = ValueAnimator.ofInt(tv_detail.getLayoutParams().height, ViewGroup.LayoutParams.MATCH_PARENT);

                valueAnimator.setDuration(2000);

                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {

                        int currentValue = (Integer) animator.getAnimatedValue();

                        System.out.println(currentValue);

                        tv_detail.getLayoutParams().height = currentValue;

                        tv_detail.requestLayout();

                    }
                });

                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        tv_detail.setMaxLines(Integer.MAX_VALUE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                valueAnimator.start();
            }
        });
    }
}
