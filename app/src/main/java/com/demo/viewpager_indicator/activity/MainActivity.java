package com.demo.viewpager_indicator.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.demo.viewpager_indicator.fragment.MineFragment;
import com.demo.viewpager_indicator.fragment.PopularFragment;
import com.demo.viewpager_indicator.fragment.WholeFragment;
import com.demo.viewpager_indicator.view.ViewPagerIndicator;
import com.example.alex.dagger.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hongshen on 2017/8/7 0007.
 */

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.view_pager)
    ViewPager view_pager;

    @BindView(R.id.indicator)
    ViewPagerIndicator indicator;

    private List<Fragment> mTabContents;
    private FragmentPagerAdapter mAdapter;
    private List<String> mDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_viewpager);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add("全部");
        mDatas.add("热门");
        mDatas.add("我的");
        indicator.setTabTitles(mDatas);

        mTabContents = new ArrayList<>();
        mTabContents.add(new WholeFragment());
        mTabContents.add(new PopularFragment());
        mTabContents.add(new MineFragment());

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return mTabContents.get(i);
            }

            @Override
            public int getCount() {
                return mTabContents.size();
            }
        };

        view_pager.setAdapter(mAdapter);
        view_pager.setOffscreenPageLimit(3);
        indicator.setViewPager(view_pager, 0);
    }

}
