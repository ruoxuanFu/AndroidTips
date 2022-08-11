package com.fdd.mydagger2test.componentdependence;

import dagger.Component;

@Component(dependencies = PotComponent.class)
public interface PotFlowerComponent {
    void inject(ComponentDependenceTestActivity activity);
}
