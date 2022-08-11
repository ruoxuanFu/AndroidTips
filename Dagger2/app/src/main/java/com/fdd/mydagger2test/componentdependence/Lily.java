package com.fdd.mydagger2test.componentdependence;

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
