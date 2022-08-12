package com.fdd.mydagger2test.coffeemachinetest;

import dagger.Component;

@Component(modules = SimpleMakerModule.class)
public interface SimpleMakerComponent {
    void inject(CoffeeMachineActivity activity);
}
