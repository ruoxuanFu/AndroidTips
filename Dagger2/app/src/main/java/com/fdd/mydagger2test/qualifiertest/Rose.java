package com.fdd.mydagger2test.qualifiertest;

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
