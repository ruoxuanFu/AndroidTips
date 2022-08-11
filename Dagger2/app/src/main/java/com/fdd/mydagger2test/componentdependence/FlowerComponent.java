package com.fdd.mydagger2test.componentdependence;

import dagger.Component;

@Component(modules = FlowerModule.class)
public interface FlowerComponent {

    @FlowerModule.RoseFlower
    Flower getRoseFlower();

    @FlowerModule.LilyFlower
    Flower getLilyFlower();
}
