package com.fdd.mydagger2test.componentdependence;

import dagger.Component;

@Component(modules = PotModule.class, dependencies = FlowerComponent.class)
public interface PotComponent {
    @PotModule.RosePot
    Pot getRosePot();

    @PotModule.LilyPot
    Pot getLilyPot();
}
