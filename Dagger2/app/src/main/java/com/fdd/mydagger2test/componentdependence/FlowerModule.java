package com.fdd.mydagger2test.componentdependence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;

@Module
public class FlowerModule {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RoseFlower {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LilyFlower {
    }

    @RoseFlower
    @Provides
    Flower provideRose() {
        return new Rose();
    }

    @LilyFlower
    @Provides
    Flower provideLily() {
        return new Lily();
    }
}