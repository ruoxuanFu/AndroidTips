package com.fdd.mydagger2test.subcomponent;

import dagger.Subcomponent;

@Subcomponent
public interface PotFlowerComponent {
    void inject(SubComponentActivity activity);
}
