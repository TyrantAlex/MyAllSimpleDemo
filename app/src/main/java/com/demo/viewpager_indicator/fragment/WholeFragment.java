package com.demo.viewpager_indicator.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.demo.viewpager_indicator.adapter.ViewPagerListViewAdapter;
import com.example.alex.dagger.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全部
 * Created by hongshen on 2017/8/8 0008.
 */

public class WholeFragment extends Fragment{

    private View view;

    @BindView(R.id.lv_view_pager)
    ListView lv_view_pager;

    private ViewPagerListViewAdapter viewPagerListViewAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_whole, container, false);
        ButterKnife.bind(this, view);
        if (lv_view_pager != null && getContext() != null) {
            viewPagerListViewAdapter = new ViewPagerListViewAdapter(getContext());
            lv_view_pager.setAdapter(viewPagerListViewAdapter);
        }
        return view;
    }

}
