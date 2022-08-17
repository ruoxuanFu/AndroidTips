package com.fdd.hilttest;

import javax.inject.Inject;

public class AnnexBeanAdapter {

    private final AnnexBean bean;

    @Inject
    public AnnexBeanAdapter(AnnexBean bean) {
        this.bean = bean;
    }

    public AnnexBean getBean() {
        return bean;
    }
}
