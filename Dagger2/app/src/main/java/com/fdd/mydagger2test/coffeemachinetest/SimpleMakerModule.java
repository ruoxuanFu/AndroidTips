package com.fdd.mydagger2test.coffeemachinetest;

import dagger.Module;
import dagger.Provides;

@Module
public class SimpleMakerModule {

    @Provides
    Cooker provideCooker() {
        return new Cooker("James", "Espresso");
    }

    @Provides
    CoffeeMaker provideSimpleMaker(){
        return new SimpleMaker(provideCooker());
    }
}
