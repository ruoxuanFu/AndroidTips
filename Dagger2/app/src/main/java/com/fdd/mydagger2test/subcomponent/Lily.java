package com.fdd.mydagger2test.subcomponent;

import javax.inject.Inject;

public class Lily implements Flower {

    @Inject
    public Lily() {
    }

    @Override
    public String whisper() {
        return "pure";
    }
}
