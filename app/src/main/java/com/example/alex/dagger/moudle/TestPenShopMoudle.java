package com.example.alex.dagger.moudle;

import com.example.alex.dagger.qualifier.Qualifier;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/23 0023.
 */
@Module
public class TestPenShopMoudle {

    public TestPenShopMoudle() {
    }

//    @Provides
//    public TestShop provideTestPen(){
//        return new TestShop();
//    }

    @Qualifier.QualifierA
    @Provides
    public TestShop provideTestPenA(){
        return new TestShop("PenA");
    }

    @Qualifier.QualifierB
    @Provides
    public TestShop provideTestPenB(){
        return new TestShop("PenB");
    }
}
