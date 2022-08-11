package com.fdd.mydagger2test.mpvtest;

import dagger.Component;

@Component(modules = MvpTestModule.class)
public interface MvpTestComponent {

    void inject(MvpTestActivity activity);
}
