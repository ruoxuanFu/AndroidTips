package com.fdd.mydagger2test.qualifiertest;

import dagger.Component;

@Component(modules = FlowerModule.class)
public interface FlowerComponent {
    void inject(QualifierTestActivity activity);
}
