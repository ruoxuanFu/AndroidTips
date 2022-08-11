package com.fdd.mydagger2test.subcomponent;

import dagger.Component;

@Component(modules = FlowerModule.class)
public interface FlowerComponent {

    //@FlowerModule.RoseFlower
    //Flower getRoseFlower();
    //
    //@FlowerModule.LilyFlower
    //Flower getLilyFlower();

    PotComponent plus(PotModule potModule);
}
