package com.fdd.hilttest;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public class AnnexBeanModel {

    @Provides
    public AnnexBean provideAnnexBean() {
        AnnexBean bean = new AnnexBean();
        bean.setId("1008611");
        bean.setFileName("1008611+10010");
        return bean;
    }

}
