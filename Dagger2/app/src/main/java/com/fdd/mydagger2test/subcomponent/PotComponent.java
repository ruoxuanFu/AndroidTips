package com.fdd.mydagger2test.subcomponent;

import dagger.Subcomponent;

@Subcomponent(modules = PotModule.class)
public interface PotComponent {
    PotFlowerComponent plus();
}
