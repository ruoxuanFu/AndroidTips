package com.fdd.mydagger2test.componentdependence;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;

@Module
public class PotModule {

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface RosePot {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface LilyPot {
    }

    @RosePot
    @Provides
    Pot provideRosePot(@FlowerModule.RoseFlower Flower flower) {
        return new Pot(flower);
    }

    @LilyPot
    @Provides
    Pot provideLilyPot(@FlowerModule.LilyFlower Flower flower) {
        return new Pot(flower);
    }
}
