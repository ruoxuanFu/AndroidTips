package com.fdd.hilttest;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;

@Module
@InstallIn(ActivityComponent.class)
public class AlertDialogModel {

    @Provides
    public static AlertDialog.Builder provideTipDialog(@ActivityContext Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Tips");
        builder.setMessage("Tip is a tip");
        return builder;
    }
}
