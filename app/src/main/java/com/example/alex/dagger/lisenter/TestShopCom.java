package com.example.alex.dagger.lisenter;

import com.example.alex.dagger.moudle.TestPenShopMoudle;
import com.example.alex.dagger.ui.MainActivity;

import dagger.Component;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
@Component(modules = TestPenShopMoudle.class)
public interface TestShopCom {
    void inject(MainActivity mainActivity);
}
