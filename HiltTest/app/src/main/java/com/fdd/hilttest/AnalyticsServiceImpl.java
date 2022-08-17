package com.fdd.hilttest;

import android.util.Log;

import javax.inject.Inject;

public class AnalyticsServiceImpl implements AnalyticsService {

    @Inject
    public AnalyticsServiceImpl() {
    }

    @Override
    public void analyticsMethods() {
        Log.d("fmsg", "analyticsMethods: analytics...");
    }
}