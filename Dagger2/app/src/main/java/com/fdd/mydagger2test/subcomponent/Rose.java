package com.fdd.mydagger2test.subcomponent;

import javax.inject.Inject;

public class Rose implements Flower {

    @Inject
    public Rose() {
    }

    @Override
    public String whisper() {
        return "love";
    }
}
