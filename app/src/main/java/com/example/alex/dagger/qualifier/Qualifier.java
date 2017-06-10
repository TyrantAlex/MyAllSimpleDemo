package com.example.alex.dagger.qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class Qualifier {

    @javax.inject.Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QualifierA{}

    @javax.inject.Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface QualifierB{}

}
