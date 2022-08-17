package com.fdd.hilttest;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ActivityContext;

@Module
@InstallIn(ActivityComponent.class)
public class CustomDialogModel {

    @ConfirmDialog
    @Provides
    public static AlertDialog.Builder provideConfirmDialog(@ActivityContext Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirm");
        builder.setMessage("Tip is a confirm");
        return builder;
    }

    @ErrorDialog
    @Provides
    public static AlertDialog.Builder provideErrorDialog(@ActivityContext Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error Dialog");
        builder.setMessage("Tip is a Error");
        return builder;
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ErrorDialog {
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ConfirmDialog {
    }
}
