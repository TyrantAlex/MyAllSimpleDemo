package com.demo.viewpager_indicator.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alex.dagger.R;

/**
 * ListView 嵌套ViewPager
 * Created by hongshen on 2017/8/14 0014.
 */

public class ViewPagerListViewAdapter extends BaseAdapter{

    private Context context;

    private LayoutInflater inflater;

//    private ViewPager

    private PagerAdapter mAdapter = new ViewPagerAdapter();

    int[] imgRes = {R.mipmap.cats,R.mipmap.leaves,R.mipmap.c,R.mipmap.d,R.mipmap.e,R.mipmap.f,R.mipmap.g,R.mipmap.h,R.mipmap.i};

    public ViewPagerListViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 16;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_viewpager_listview, null);
        TextView tv_base_setting= (TextView) view.findViewById(R.id.tv_base_setting);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_list);
        if (position == 0) {
            view.setBackgroundColor(Color.parseColor("#000000"));
            tv_base_setting.setVisibility(View.GONE);
            viewPager.setVisibility(View.VISIBLE);
            //设置缓存的页面数量
            viewPager.setOffscreenPageLimit(2);
            viewPager.setPageMargin(5);
            viewPager.setAdapter(mAdapter);
        } else {
            tv_base_setting.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.GONE);
        }
        return view;
    }

    class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imgRes.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(context);
            view.setImageResource(imgRes[position]);
//            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public float getPageWidth(int position) {
            return 0.5f;
        }
    }
}
