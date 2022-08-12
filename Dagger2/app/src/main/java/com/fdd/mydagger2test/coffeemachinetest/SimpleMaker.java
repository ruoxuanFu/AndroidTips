package com.fdd.mydagger2test.coffeemachinetest;

import javax.inject.Inject;

public class SimpleMaker implements CoffeeMaker {

    Cooker cooker;

    @Inject
    public SimpleMaker(Cooker cooker) {
        this.cooker = cooker;
    }

    @Override
    public String makeCoffee() {
        return cooker.make();
    }
}
